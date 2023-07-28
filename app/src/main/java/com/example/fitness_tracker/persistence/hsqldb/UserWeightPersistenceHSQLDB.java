package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.IUserWeightPersistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserWeightPersistenceHSQLDB implements IUserWeightPersistence {

    private final String dbPath;

    public UserWeightPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private UserWeight fromResultSet(final ResultSet rs) throws SQLException {
        final String rsUsername = rs.getString("username");
        final String userPassword = rs.getString("password");
        final String userFirstName = rs.getString("firstname");
        final String userLastName = rs.getString("lastname");
        final BigDecimal userGoalWeight = rs.getBigDecimal("goalweight");
        final String email = rs.getString("email");
        final int weightID = rs.getInt("weightid");
        final String date = rs.getString("date");
        final String time = rs.getString("time");
        final BigDecimal weight = rs.getBigDecimal("weightvalue");

        final User user = new User(rsUsername,userPassword,userFirstName,userLastName,userGoalWeight,email);
        final Weight weightObject = new Weight(weightID, weight);
        return new UserWeight(user, weightObject, date,time);
    }

    @Override
    public List<UserWeight> getUserWeight(String username) {
        final List<UserWeight> userWeights = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM weight,userweight,user WHERE weight.weightid=userweight.weightid AND user.username = ? AND userweight.username = ?");
            st.setString(1, username);
            st.setString(2, username);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final UserWeight record = fromResultSet(rs);
                userWeights.add(record);
            }

            rs.close();
            st.close();

            return userWeights;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<UserWeight> getWeightUser(int weightID) {
        final List<UserWeight> userWeights = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM user,userweight,weight WHERE user.username=userweight.username AND weight.weightid = ? AND userweight.weightid = ?");
            st.setInt(1, weightID);
            st.setInt(1, weightID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final UserWeight record = fromResultSet(rs);
                userWeights.add(record);
            }

            rs.close();
            st.close();

            return userWeights;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public UserWeight insertUserWeight(UserWeight currentUserWeight) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO userweight VALUES(?, ?, ?, ?)");
            st.setString(1, currentUserWeight.getUser().getUsername());
            st.setInt(2, currentUserWeight.getWeight().getWeightID());
            st.setString(3, currentUserWeight.getDate());
            st.setString(4, currentUserWeight.getTime());
            st.executeUpdate();
            return currentUserWeight;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public UserWeight updateUserWeight(UserWeight currentUserWeight) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE userweight SET date = ?, time = ? WHERE username = ? AND weightid = ?");
            st.setString(1, currentUserWeight.getDate());
            st.setString(2, currentUserWeight.getTime());
            st.setString(3, currentUserWeight.getUser().getUsername());
            st.setInt(4, currentUserWeight.getWeight().getWeightID());
            st.executeUpdate();
            return currentUserWeight;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteUserWeight(UserWeight currentUserWeight) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM userweight WHERE username = ? AND weightid = ?");
            sc.setString(1, currentUserWeight.getUser().getUsername());
            sc.setInt(2,currentUserWeight.getWeight().getWeightID());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int userWeightSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM userweight");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
