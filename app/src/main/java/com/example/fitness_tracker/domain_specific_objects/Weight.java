package com.example.fitness_tracker.domain_specific_objects;


import java.math.BigDecimal;
import java.util.Objects;

/**
 * Weight
 *
 * The weight object for workouts
 */
public class Weight  {
    /**
     * weightID specifier
     */
    private final int weightID;

    /**
     * weight in lbs
     */
    private final BigDecimal weight; //in lbs

    /**
     * CONSTRUCTOR
     *
     * @param weightID the id
     * @otherparams default vals
     */
    public Weight(final int weightID) {
        this.weightID = weightID;
        weight = null;
    }

    /**
     * CONSTRUCTOR
     *
     * @param weightID the id
     * @param weight the weight
     */
    public Weight(final int weightID, final BigDecimal weight) {
        this.weightID = weightID;
        this.weight = weight;
    }

    /**
     * equals
     *
     * checks if the given object is equal to the current workout object
     * @param o the object
     * @return if it is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight = (Weight) o;
        return weightID == weight.weightID;
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public int getWeightID() {
        return weightID;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weightID);
    }




}

