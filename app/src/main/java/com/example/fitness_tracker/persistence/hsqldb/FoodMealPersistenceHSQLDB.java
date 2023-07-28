package com.example.fitness_tracker.persistence.hsqldb;


import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.domain_specific_objects.FoodMeal;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.persistence.IFoodMealPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FoodMealPersistenceHSQLDB implements IFoodMealPersistence {
    private final String dbPath;

    public FoodMealPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private FoodMeal fromResultSet(final ResultSet rs) throws SQLException {
        final int foodID = rs.getInt("foodid");
        final int mealID = rs.getInt("mealid");
        final String mealName = rs.getString("mealname");
        final String foodName = rs.getString("foodname");
        final int calories = rs.getInt("calories");

        return new FoodMeal(new Food(foodID,foodName,calories), new Meal(mealID,mealName));
    }

    @Override
    public List<FoodMeal> getFoodMeal(int foodID) {
        final List<FoodMeal> foodMeals = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM meal,foodmeal,food WHERE meal.mealid=foodmeal.mealid AND food.foodid = ? AND foodmeal.foodid = ?");
            st.setInt(1, foodID);
            st.setInt(2, foodID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final FoodMeal record = fromResultSet(rs);
                foodMeals.add(record);
            }

            rs.close();
            st.close();

            return foodMeals;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<FoodMeal> getMealFood(int mealID) {
        final List<FoodMeal> foodMeals = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM food,foodmeal,meal WHERE food.foodid=foodmeal.foodid AND meal.mealid = ? AND foodmeal.mealid = ?");
            st.setInt(1, mealID);
            st.setInt(2, mealID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final FoodMeal record = fromResultSet(rs);
                foodMeals.add(record);
            }

            rs.close();
            st.close();

            return foodMeals;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public FoodMeal insertFoodMeal(FoodMeal currentFoodMeal) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO foodmeal VALUES(?, ?)");
            st.setInt(1, currentFoodMeal.getFood().getId());
            st.setInt(2, currentFoodMeal.getMeal().getId());
            st.executeUpdate();
            return currentFoodMeal;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteFoodMeal(FoodMeal currentFoodMeal) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM foodmeal WHERE foodid = ? AND mealid = ?");
            sc.setInt(1, currentFoodMeal.getFood().getId());
            sc.setInt(2,currentFoodMeal.getMeal().getId());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int foodMealSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM foodmeal");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
