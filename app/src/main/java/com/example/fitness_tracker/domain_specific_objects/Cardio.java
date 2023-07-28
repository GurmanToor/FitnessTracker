package com.example.fitness_tracker.domain_specific_objects;

/**
 * Cardio object for workout
 */
public class Cardio extends Exercise{

    /**
     * how far
     */
    private int distance;

    /**
     * workout type
     */
    private String workoutType;

    /**
     * distance
     */
    private String distanceUnits;

    /**
     * CONSTRUCTOR
     * @param newID id for the exercise
     * @param newName name for exercise
     * @param newBodyPart specify body part
     * @param newType type of exercise
     * @param dist distance ran
     */
    public Cardio(int newID, String newName, String newBodyPart, String newType, int dist, String distUnits,String length){
        super(newID,newName,newBodyPart,newType,length);
        distance = dist;
        distanceUnits = distUnits;
    }

    public Cardio(int newID) {
        super(newID,null,null,null,null);
        distance = 0;
        distanceUnits = null;
    }

    public int getDistance() {
        return distance;
    }
    public String getDistanceUnits(){
        return distanceUnits;
    }
}
