package com.example.fitness_tracker.domain_specific_objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Workout
 *
 * A workout object, for.. workouts
 */
public class Workout implements Serializable {

    /**
     * The name of the workout
     */
    private String name;

    /**
     * the time length of the workout (HH:MM:SS)
     */
    private String length;

    /**
     * workoutID specifier
     */
    private int workoutID;

    /**
     * CONSTRUCTOR
     * @param workoutLength the length
     * @param workoutName the name
     * @param workoutID the id
     */
    public Workout(int workoutID, String workoutName, String workoutLength){
        length = workoutLength;
        name = workoutName;
        this.workoutID = workoutID;
    }

    /**
     * CONSTRUCTOR
     * @param workoutID the id
     * @otherparams default vals
     */
    public Workout(int workoutID) {
        this.workoutID = workoutID;
        length = null;
        name = null;
    }

    /**
     * equals
     *
     * checks if the given object is equal to the current workout
     * @param o the workout
     * @return if it is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return workoutID == workout.workoutID;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", workoutID=" + workoutID +
                '}';
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutID);
    }

    public String getLength() {
        return length;
    }

    public int getWorkoutID() {
        return workoutID;
    }
}