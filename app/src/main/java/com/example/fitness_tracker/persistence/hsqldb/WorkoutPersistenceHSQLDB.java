package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IWorkoutPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPersistenceHSQLDB implements IWorkoutPersistence {
    private final String dbPath;

    public WorkoutPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Workout fromResultSet(final ResultSet rs) throws SQLException {
        final int workoutID = rs.getInt("workoutid");
        final String name = rs.getString("workoutname");
        final String length = rs.getString("workoutlength");

        return new Workout(workoutID, name,length);
    }

    @Override
    public List<Workout> getWorkoutSequential() {
        final List<Workout> workouts = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM workout");
            while (rs.next()) {
                final Workout workout = fromResultSet(rs);
                workouts.add(workout);
            }
            rs.close();
            st.close();

            return workouts;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Workout> getWorkoutRandom(Workout currentWorkout) {
        final List<Workout> workouts = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM workout WHERE workoutid = ?");
            st.setInt(1, currentWorkout.getWorkoutID());
            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                final Workout workout = fromResultSet(rs);
                workouts.add(workout);
            }

            rs.close();
            st.close();

            return workouts;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Workout insertWorkout(Workout currentWorkout) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO workout VALUES(?, ?, ?)");
            st.setInt(1, currentWorkout.getWorkoutID());
            st.setString(2, currentWorkout.getName());
            st.setString(3, currentWorkout.getLength());
            st.executeUpdate();
            return currentWorkout;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Workout updateWorkout(Workout currentWorkout) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE workout SET workoutname = ?, workoutlength = ? WHERE workoutid = ?");
            st.setString(1, currentWorkout.getName());
            st.setString(2, currentWorkout.getLength());
            st.setInt(3, currentWorkout.getWorkoutID());
            st.executeUpdate();
            return currentWorkout;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    //make sure to delete Workout from all many-many tables as well
    public void deleteWorkout(Workout currentWorkout) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM exerciseworkout WHERE workoutid = ?");
            sc.setInt(1, currentWorkout.getWorkoutID());
            sc.executeUpdate();
            final PreparedStatement uw = c.prepareStatement("DELETE FROM userworkout WHERE workoutid = ?");
            uw.setInt(1, currentWorkout.getWorkoutID());
            uw.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM workout WHERE workoutid = ?");
            st.setInt(1, currentWorkout.getWorkoutID());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int workoutSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM workout");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
