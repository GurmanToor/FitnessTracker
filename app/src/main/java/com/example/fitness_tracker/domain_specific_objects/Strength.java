package com.example.fitness_tracker.domain_specific_objects;

/**
 * The Strength object for workout
 */
public class Strength extends Exercise{

    /**
     * the body part
     */
    private String bodyPart;

    /**
     * the name of the workout
     */
    private String workoutName;

    /**
     * reps
     */
    private int reps;

    /**
     * sets
     */
    private int sets;

    /**
     * CONSTRUCTOR
     * @param exerciseID id
     * @param name
     * @param bodyPart the body part being worked out
     * @param newType type of excersize
     * @param repNumber reps
     * @param setNumber sets
     */
    public Strength(int exerciseID, String name, String bodyPart, String newType,int repNumber, int setNumber, String newLength){
        super(exerciseID, name,bodyPart,newType,newLength);
        reps = repNumber;
        sets = setNumber;
    }

    /**
     * CONSTRUCTOR
     * @param workoutID the specifier
     * @otherparams default vals
     */
    public Strength(int workoutID) {
        super(workoutID);
        reps = 0;
        sets = 0;
    }

    public int getReps(){
        return reps;
    }

    public int getSets(){
        return sets;
    }
}
