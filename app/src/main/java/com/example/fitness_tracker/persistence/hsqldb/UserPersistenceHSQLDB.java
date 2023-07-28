package com.example.fitness_tracker.persistence.hsqldb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.fitness_tracker.persistence.IUserPersistence;
import com.example.fitness_tracker.domain_specific_objects.User;

public class UserPersistenceHSQLDB implements IUserPersistence {
    private final String dbPath;

    public UserPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        System.out.println(dbPath);
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private User fromResultSet(final ResultSet rs) throws SQLException {
        final String userName = rs.getString("username");
        final String userPassword = rs.getString("password");
        final String userFirstName = rs.getString("firstname");
        final String userLastName = rs.getString("lastname");
        final BigDecimal userGoalWeight = rs.getBigDecimal("goalweight");
        final String userEmail = rs.getString("email");

        return new User(userName, userPassword, userFirstName,userLastName,userGoalWeight,userEmail);
    }

    @Override
    public List<User> getUserSequential() {
        final List<User> users = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM user");
            while (rs.next()) {
                final User user = fromResultSet(rs);
                users.add(user);
            }
            rs.close();
            st.close();

            return users;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<User> getUserRandom(User currentUser) {
        final List<User> users = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM user WHERE username = ?");
            st.setString(1, currentUser.getUsername());

            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                final User user = fromResultSet(rs);
                users.add(user);
            }

            rs.close();
            st.close();

            return users;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public User insertUser(User currentUser) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?, ?, ?)");
            st.setString(1, currentUser.getUsername());
            st.setString(2, currentUser.getPassword());
            st.setString(3, currentUser.getFirstName());
            st.setString(4, currentUser.getLastName());
            st.setBigDecimal(5, currentUser.getGoalWeight());
            st.setString(6, currentUser.getEmail());
            st.executeUpdate();
            return currentUser;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public User updateUser(User currentUser) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE user SET password = ?, firstname = ?, lastname = ?, goalweight = ? WHERE username = ?");
            st.setString(1, currentUser.getPassword());
            st.setString(2, currentUser.getFirstName());
            st.setString(3, currentUser.getLastName());
            st.setBigDecimal(4, currentUser.getGoalWeight());
            st.setString(5, currentUser.getUsername());
            st.executeUpdate();
            return currentUser;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteUser(User currentUser) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM usermeal WHERE username = ?");
            sc.setString(1, currentUser.getUsername());
            sc.executeUpdate();
            final PreparedStatement uwo = c.prepareStatement("DELETE FROM userworkout WHERE username = ?");
            uwo.setString(1, currentUser.getUsername());
            uwo.executeUpdate();
            final PreparedStatement uw = c.prepareStatement("DELETE FROM userweight WHERE username = ?");
            uw.setString(1, currentUser.getUsername());
            uw.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM user WHERE username = ?");
            st.setString(1, currentUser.getUsername());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int userSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM user");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
