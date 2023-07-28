package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.persistence.IExercisePersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExercisePersistenceHSQLDB implements IExercisePersistence {
    private final String dbPath;

    public ExercisePersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Exercise fromResultSet(final ResultSet rs) throws SQLException {
        Exercise output = null;
        final int exerciseID = rs.getInt("exerciseid");
        final String name = rs.getString("exercisename");
        final String bodyPart = rs.getString("bodypart");
        final String type = rs.getString("exercisetype");
        final int distance = rs.getInt("distance");
        final int sets = rs.getInt("sets");
        final int reps = rs.getInt("reps");
        final String length = rs.getString("exerciselength");
        final String units = rs.getString("distunits");
        if (type.toLowerCase().equals("strength")) {
            output = new Strength(exerciseID,name,bodyPart,type,sets,reps,length);
        }
        else if (type.toLowerCase().equals("cardio")) {
            output = new Cardio(exerciseID,name,bodyPart,type,distance,units,length);
        }
        else {
            // do nothing
        }

        return output;
    }

    @Override
    public List<Exercise> getExerciseSequential() {
        final List<Exercise> exercises = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM exercise");
            while (rs.next()) {
                final Exercise exercise = fromResultSet(rs);
                exercises.add(exercise);
            }
            rs.close();
            st.close();

            return exercises;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Exercise> getExerciseRandom(Exercise currentExercise) {
        final List<Exercise> exercises = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM exercise WHERE exerciseid = ?");
            st.setInt(1, currentExercise.getExerciseID());
            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                final Exercise exercise = fromResultSet(rs);
                exercises.add(exercise);
            }

            rs.close();
            st.close();

            return exercises;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Exercise insertExercise(Exercise currentExercise) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO exercise VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, currentExercise.getExerciseID());
            st.setString(2, currentExercise.getName());
            st.setString(3, currentExercise.getBodyPart());
            st.setString(4, currentExercise.getType());
            st.setString(9, currentExercise.getLength());
            if (currentExercise.getType().equalsIgnoreCase("strength")) {
                st.setInt(5, 0);
                Strength currentStrength = (Strength)currentExercise;
                st.setInt(6, currentStrength.getReps());
                st.setInt(7, currentStrength.getSets());
                st.setString(8,"NULL");
            }
            if (currentExercise.getType().equalsIgnoreCase("cardio")) {
                Cardio currentCardio = (Cardio)currentExercise;
                st.setInt(5, currentCardio.getDistance());
                st.setInt(6, 0);
                st.setInt(7, 0);
                st.setString(8,currentCardio.getDistanceUnits());
            }

            st.executeUpdate();
            return currentExercise;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Exercise updateExercise(Exercise currentExercise) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE exercise SET exercisename = ?, bodypart = ?, exercisetype = ?, distance = ?, reps = ?, sets = ?, exerciselength = ?, distunits = ? WHERE exerciseid = ?");
            st.setString(1, currentExercise.getName());
            st.setString(2, currentExercise.getBodyPart());
            st.setString(3, currentExercise.getType());
            st.setString(7, currentExercise.getLength());
            if (currentExercise.getType().equalsIgnoreCase("strength")) {
                Strength currentStrength = (Strength)currentExercise;
                st.setInt(4,0);
                st.setInt(5, currentStrength.getReps());
                st.setInt(6, currentStrength.getSets());
                st.setString(8,"NULL");
            }
            if (currentExercise.getType().equalsIgnoreCase("cardio")) {
                Cardio currentCardio = (Cardio)currentExercise;
                st.setInt(4,currentCardio.getDistance());
                st.setInt(5, 0);
                st.setInt(6, 0);
                st.setString(8,currentCardio.getDistanceUnits());
            }
            st.setInt(9, currentExercise.getExerciseID());
            st.executeUpdate();
            return currentExercise;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    //make sure to delete Exercise from all many-many tables as well
    public void deleteExercise(Exercise currentExercise) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM exerciseworkout WHERE exerciseid = ?");
            sc.setInt(1, currentExercise.getExerciseID());
            sc.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM exercise WHERE exerciseid = ?");
            st.setInt(1, currentExercise.getExerciseID());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int exerciseSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM exercise");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
