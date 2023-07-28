package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.persistence.IFoodPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FoodPersistenceHSQLDB implements IFoodPersistence {

    private final String dbPath;

    public FoodPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Food fromResultSet(final ResultSet rs) throws SQLException {
        final int foodID = rs.getInt("foodid");
        final String name = rs.getString("foodname");
        final int calories = rs.getInt("calories");

        return new Food(foodID,name,calories);
    }

    @Override
    public List<Food> getFoodSequential() {
        final List<Food> foods = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM food");
            while (rs.next()) {
                final Food food = fromResultSet(rs);
                foods.add(food);
            }
            rs.close();
            st.close();

            return foods;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Food> getFoodRandom(Food currentFood) {
        final List<Food> foods = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM food WHERE foodid = ?");
            st.setInt(1, currentFood.getId());

            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                final Food food = fromResultSet(rs);
                foods.add(food);
            }

            rs.close();
            st.close();

            return foods;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Food insertFood(Food currentFood) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO food VALUES(?, ?, ?)");
            st.setInt(1, currentFood.getId());
            st.setString(2, currentFood.getName());
            st.setInt(3,currentFood.getCalories());
            st.executeUpdate();
            return currentFood;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Food updateFood(Food currentFood) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE food SET foodname = ?, calories = ? WHERE foodid = ?");
            st.setString(1, currentFood.getName());
            st.setInt(2, currentFood.getCalories());
            st.setInt(3, currentFood.getId());
            st.executeUpdate();
            return currentFood;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    //make sure to delete Weight from all many-many tables as well
    public void deleteFood(Food currentFood) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM foodmeal WHERE foodid = ?");
            sc.setInt(1, currentFood.getId());
            sc.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM food WHERE foodid = ?");
            st.setInt(1, currentFood.getId());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int foodSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM food");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
