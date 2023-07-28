package com.example.fitness_tracker.persistence.hsqldb;

import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.IWeightPersistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WeightPersistenceHSQLDB implements IWeightPersistence {
    private final String dbPath;

    public WeightPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Weight fromResultSet(final ResultSet rs) throws SQLException {
        final int weightID = rs.getInt("weightid");
        final BigDecimal weight = rs.getBigDecimal("weightvalue");

        return new Weight(weightID, weight);
    }

    @Override
    public List<Weight> getWeightSequential() {
        final List<Weight> weights = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM weight");
            while (rs.next()) {
                final Weight weight = fromResultSet(rs);
                weights.add(weight);
            }
            rs.close();
            st.close();

            return weights;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Weight> getWeightRandom(Weight currentWeight) {
        final List<Weight> weights = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM weight WHERE weightid = ?");
            st.setInt(1, currentWeight.getWeightID());

            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                final Weight weight = fromResultSet(rs);
                weights.add(weight);
            }

            rs.close();
            st.close();

            return weights;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Weight insertWeight(Weight currentWeight) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO weight VALUES(?, ?)");
            st.setInt(1, currentWeight.getWeightID());
            st.setBigDecimal(2, currentWeight.getWeight());
            st.executeUpdate();
            return currentWeight;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Weight updateWeight(Weight currentWeight) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE weight SET weightvalue = ? WHERE weightid = ?");
            st.setBigDecimal(1, currentWeight.getWeight());
            st.setInt(2, currentWeight.getWeightID());
            st.executeUpdate();
            return currentWeight;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteWeight(Weight currentWeight) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM userweight WHERE weightid = ?");
            sc.setInt(1, currentWeight.getWeightID());
            sc.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM weight WHERE weightid = ?");
            st.setInt(1, currentWeight.getWeightID());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int weightSize() {
        try (final Connection c = connection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM weight");
            // get the number of rows from the result set
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
