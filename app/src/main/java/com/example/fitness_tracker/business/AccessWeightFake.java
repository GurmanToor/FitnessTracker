package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.IWeightPersistence;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


/**
 * AccessWeight.java
 *
 * PURPOSE: A fake implementation for a table that contains User objects
 */
public class AccessWeightFake implements IWeightPersistence {

    /**
     * the table
     */
    private final HashMap<Integer, Weight> table;

    /**
     * CONSTRUCTOR
     */
    public AccessWeightFake() {
        table = new HashMap<Integer,Weight>();
        Weight weight1 = new Weight(1,new BigDecimal("150.0"));
        Weight weight2 = new Weight(2,new BigDecimal("180.5"));
        Weight weight3 = new Weight(3,new BigDecimal("140.2"));
        table.put(1,weight1);
        table.put(2,weight2);
        table.put(3,weight3);
    }

    public Weight getWeightByID(int id) {
        return table.get(id); //returns null if Weight with id does not exist
    }

    @Override
    public List<Weight> getWeightSequential() {
        return null;
    }

    @Override
    public List<Weight> getWeightRandom(Weight currentWeight) {
        return null;
    }

    /********************************************************************************************
     *                               MODIFIERS!
     * ********************************************************************************************/
    public Weight insertWeight(Weight u) {
        Weight output = null;
        if (table.put(u.getWeightID(),u) == null)
            output = u;
        return output;
    }

    @Override
    public void deleteWeight(Weight u) {
        Weight output = null;
        if (table.remove(u.getWeightID()) != null)
            output = u;
    }

    public Weight updateWeight(Weight u) {
        Weight output = null;
        if (table.containsKey(u.getWeightID())) {
            table.put(u.getWeightID(), u);
            output = u;
        }
        return output;
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public List<Weight> getAllWeights() {
        return (List<Weight>) table.values();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public int weightSize() {
        return table.size();
    }
}
