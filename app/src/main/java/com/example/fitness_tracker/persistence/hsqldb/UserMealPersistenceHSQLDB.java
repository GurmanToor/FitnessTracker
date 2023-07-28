package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.domain_specific_objects.UserMeal;
import com.example.fitness_tracker.persistence.IUserMealPersistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserMealPersistenceHSQLDB implements IUserMealPersistence {

    private final String dbPath;

    public UserMealPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private UserMeal fromResultSet(final ResultSet rs) throws SQLException {
        final String rsUsername = rs.getString("username");
        final String password = rs.getString("password");
        final String firstName = rs.getString("firstname");
        final String lastName = rs.getString("lastname");
        final BigDecimal goalWeight = rs.getBigDecimal("goalweight");
        final String email = rs.getString("email");
        final int mealID = rs.getInt("mealid");
        final String mealName = rs.getString("mealname");
        final String date = rs.getString("date");
        final String time = rs.getString("time");

        final User user = new User(rsUsername,password,firstName,lastName,goalWeight,email);
        final Meal meal = new Meal(mealID, mealName);
        return new UserMeal(user, meal, date, time);
    }

    @Override
    public List<UserMeal> getUserMeal(String username) {
        final List<UserMeal> userMeals = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM meal,usermeal,user WHERE meal.mealid=usermeal.mealid AND user.username = ? AND usermeal.username = ?");
            st.setString(1, username);
            st.setString(2, username);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final UserMeal record = fromResultSet(rs);
                userMeals.add(record);
            }

            rs.close();
            st.close();

            return userMeals;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<UserMeal> getMealUser(int mealID) {
        final List<UserMeal> userMeals = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM user,usermeal,meal WHERE user.username=usermeal.username AND meal.mealid = ? AND usermeal.mealid = ?");
            st.setInt(1, mealID);
            st.setInt(2, mealID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final UserMeal record = fromResultSet(rs);
                userMeals.add(record);
            }

            rs.close();
            st.close();

            return userMeals;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public UserMeal insertUserMeal(UserMeal currentUserMeal) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO usermeal VALUES(?, ?, ?, ?)");
            st.setString(1, currentUserMeal.getUser().getUsername());
            st.setInt(2, currentUserMeal.getMeal().getId());
            st.setString(3, currentUserMeal.getDate());
            st.setString(4, currentUserMeal.getTime());
            st.executeUpdate();
            return currentUserMeal;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public UserMeal updateUserMeal(UserMeal currentUserMeal) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE usermeal SET date = ?, time = ? WHERE username = ? AND mealid = ?");
            st.setString(1, currentUserMeal.getDate());
            st.setString(2, currentUserMeal.getTime());
            st.setString(3, currentUserMeal.getUser().getUsername());
            st.setInt(4, currentUserMeal.getMeal().getId());
            st.executeUpdate();
            return currentUserMeal;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteUserMeal(UserMeal currentUserMeal) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM usermeal WHERE username = ? AND mealid = ?");
            sc.setString(1, currentUserMeal.getUser().getUsername());
            sc.setInt(2,currentUserMeal.getMeal().getId());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int userMealSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM usermeal");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
