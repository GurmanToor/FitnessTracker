package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IExerciseWorkoutPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExerciseWorkoutPersistenceHSQLDB implements IExerciseWorkoutPersistence {
    private final String dbPath;

    public ExerciseWorkoutPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private ExerciseWorkout fromResultSet(final ResultSet rs) throws SQLException {
        final int exerciseID = rs.getInt("exerciseid");
        final int workoutID = rs.getInt("workoutid");
        final String workoutName = rs.getString("workoutname");
        final String exerciseName = rs.getString("exercisename");
        final String workoutLength = rs.getString("workoutlength");
        final String bodyPart = rs.getString("bodypart");
        final String exerciseType = rs.getString("exercisetype");
        final int distance = rs.getInt("distance");
        final int reps = rs.getInt("reps");
        final int sets = rs.getInt("sets");
        final String distUnits = rs.getString("distunits");
        final String exerciseLength = rs.getString("exerciselength");

        Exercise output = null;
        if (exerciseType.trim().equalsIgnoreCase("strength")) {
            output = new Strength(exerciseID,exerciseName,bodyPart,exerciseType,reps,sets,exerciseLength);
        }
        if (exerciseType.trim().equalsIgnoreCase("cardio")) {
            output = new Cardio(exerciseID,exerciseName,bodyPart,exerciseType,distance,distUnits,exerciseLength);
        }
        final Workout workoutObject = new Workout(workoutID,workoutName,workoutLength);
        return new ExerciseWorkout(output, workoutObject);
    }

    @Override
    public List<ExerciseWorkout> getExerciseWorkout(int exerciseID) {
        final List<ExerciseWorkout> exerciseWorkouts = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM exercise,workout,exerciseworkout WHERE workout.workoutid=exerciseworkout.workoutid AND exercise.exerciseid = ? AND exerciseworkout.exerciseid = ?");
            st.setInt(1, exerciseID);
            st.setInt(2, exerciseID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final ExerciseWorkout record = fromResultSet(rs);
                exerciseWorkouts.add(record);
            }

            rs.close();
            st.close();

            return exerciseWorkouts;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ExerciseWorkout> getWorkoutExercise(int workoutID) {
        final List<ExerciseWorkout> exerciseWorkouts = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM exercise,exerciseworkout,workout WHERE exercise.exerciseid=exerciseworkout.exerciseid AND workout.workoutid = ? AND exerciseworkout.workoutid = ?");
            st.setInt(1, workoutID);
            st.setInt(2, workoutID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final ExerciseWorkout record = fromResultSet(rs);
                exerciseWorkouts.add(record);
            }

            rs.close();
            st.close();

            return exerciseWorkouts;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public ExerciseWorkout insertExerciseWorkout(ExerciseWorkout currentExerciseWorkout) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO exerciseworkout VALUES(?, ?)");
            st.setInt(1, currentExerciseWorkout.getExercise().getExerciseID());
            st.setInt(2, currentExerciseWorkout.getWorkout().getWorkoutID());
            st.executeUpdate();
            return currentExerciseWorkout;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteExerciseWorkout(ExerciseWorkout currentExerciseWorkout) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM exerciseworkout WHERE exerciseid = ? AND workoutid = ?");
            sc.setInt(1, currentExerciseWorkout.getExercise().getExerciseID());
            sc.setInt(2,currentExerciseWorkout.getWorkout().getWorkoutID());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int exerciseWorkoutSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM exerciseworkout");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
