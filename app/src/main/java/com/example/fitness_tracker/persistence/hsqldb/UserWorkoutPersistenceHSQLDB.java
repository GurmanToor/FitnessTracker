package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IUserWorkoutPersistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserWorkoutPersistenceHSQLDB implements IUserWorkoutPersistence {

    private final String dbPath;

    public UserWorkoutPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private UserWorkout fromResultSet(final ResultSet rs) throws SQLException {
        final String rsUsername = rs.getString("username");
        final String userPassword = rs.getString("password");
        final String userFirstName = rs.getString("firstname");
        final String userLastName = rs.getString("lastname");
        final BigDecimal userGoalWeight = rs.getBigDecimal("goalweight");
        final String email = rs.getString("email");
        final int workoutID = rs.getInt("workoutid");
        final String name = rs.getString("workoutname");
        final String time = rs.getString("time");
        final String date = rs.getString("date");
        final String length = rs.getString("workoutlength");

        final User user = new User(rsUsername,userPassword,userFirstName,userLastName,userGoalWeight,email);
        final Workout workoutObject = new Workout(workoutID,name, length);
        return new UserWorkout(user, workoutObject, date,time);
    }

    @Override
    public List<UserWorkout> getUserWorkout(String username) {
        final List<UserWorkout> userWorkouts = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM workout,userworkout,user WHERE workout.workoutid=userworkout.workoutid AND user.username = ? AND userworkout.username = ?");
            st.setString(1, username);
            st.setString(2, username);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final UserWorkout record = fromResultSet(rs);
                userWorkouts.add(record);
            }

            rs.close();
            st.close();

            return userWorkouts;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<UserWorkout> getWorkoutUser(int workoutID) {
        final List<UserWorkout> userWorkouts = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM user,userworkout,workout WHERE user.username=userworkout.username AND workout.workoutid = ? AND userworkout.workoutid = ?");
            st.setInt(1, workoutID);
            st.setInt(2, workoutID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final UserWorkout record = fromResultSet(rs);
                userWorkouts.add(record);
            }

            rs.close();
            st.close();

            return userWorkouts;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public UserWorkout insertUserWorkout(UserWorkout currentUserWorkout) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO userworkout VALUES(?, ?, ?, ?)");
            st.setString(1, currentUserWorkout.getUser().getUsername());
            st.setInt(2, currentUserWorkout.getWorkout().getWorkoutID());
            st.setString(3, currentUserWorkout.getDate());
            st.setString(4, currentUserWorkout.getTime());
            st.executeUpdate();
            return currentUserWorkout;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public UserWorkout updateUserWorkout(UserWorkout currentUserWorkout) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE userworkout SET date = ?, time = ? WHERE username = ? AND workoutid = ?");
            st.setString(1, currentUserWorkout.getDate());
            st.setString(2, currentUserWorkout.getTime());
            st.setString(3, currentUserWorkout.getUser().getUsername());
            st.setInt(4, currentUserWorkout.getWorkout().getWorkoutID());
            st.executeUpdate();
            return currentUserWorkout;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteUserWorkout(UserWorkout currentUserWorkout) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM userworkout WHERE username = ? AND workoutid = ?");
            sc.setString(1, currentUserWorkout.getUser().getUsername());
            sc.setInt(2,currentUserWorkout.getWorkout().getWorkoutID());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int userWorkoutSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM userworkout");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
