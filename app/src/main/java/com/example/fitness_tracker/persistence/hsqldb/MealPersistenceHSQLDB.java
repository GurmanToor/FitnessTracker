package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.persistence.IMealPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MealPersistenceHSQLDB implements IMealPersistence {

    private final String dbPath;

    public MealPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Meal fromResultSet(final ResultSet rs) throws SQLException {
        final int mealID = rs.getInt("mealid");
        final String name = rs.getString("mealname");

        return new Meal(mealID, name);
    }

    @Override
    public List<Meal> getMealSequential() {
        final List<Meal> meals = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM meal");
            while (rs.next()) {
                final Meal meal = fromResultSet(rs);
                meals.add(meal);
            }
            rs.close();
            st.close();

            return meals;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Meal> getMealRandom(Meal currentMeal) {
        final List<Meal> meals = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM meal WHERE mealid = ?");
            st.setInt(1, currentMeal.getId());

            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                final Meal weight = fromResultSet(rs);
                meals.add(weight);
            }

            rs.close();
            st.close();

            return meals;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Meal insertMeal(Meal currentMeal) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO meal VALUES(?, ?)");
            st.setInt(1, currentMeal.getId());
            st.setString(2, currentMeal.getName());
            st.executeUpdate();
            return currentMeal;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Meal updateMeal(Meal currentMeal) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE meal SET mealname = ? WHERE mealid = ?");
            st.setString(1, currentMeal.getName());
            st.setInt(2, currentMeal.getId());
            st.executeUpdate();
            return currentMeal;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteMeal(Meal currentMeal) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM foodmeal WHERE mealid = ?");
            sc.setInt(1, currentMeal.getId());
            sc.executeUpdate();
            final PreparedStatement um = c.prepareStatement("DELETE FROM usermeal WHERE mealid = ?");
            sc.setInt(1, currentMeal.getId());
            sc.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM meal WHERE mealid = ?");
            st.setInt(1, currentMeal.getId());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int mealSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM meal");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
