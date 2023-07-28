package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IWorkoutPersistence;

import java.util.HashMap;
import java.util.List;

/**
 * AccessWorkout.java
 *
 * PURPOSE: A fake implementation for a table that contains User objects
 */
public class AccessWorkoutFake implements IWorkoutPersistence {

    /**
     * the table
     */
    private HashMap<Integer, Workout> table;

    /**
     * CONSTRUCTOR
     */
    public AccessWorkoutFake() {
        table = new HashMap<Integer,Workout>();
        Workout workout1 = new Workout(1,"Push Day","00:34:27");
        Workout workout2 = new Workout(2,"Pull Day","00:45:35");
        Workout workout3 = new Workout(3,"Leg Day","00:57:32");
        table.put(1,workout1);
        table.put(2,workout2);
        table.put(3,workout3);
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public Workout getWorkoutByID(int id) {
        return (Workout)table.get(id); //returns null if Workout with id does not exist
    }

    public List<Workout> getAllWorkouts() {
        return (List<Workout>) table.values();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public int workoutSize() {
        return table.size();
    }

    @Override
    public List<Workout> getWorkoutSequential() {
        return null;
    }

    @Override
    public List<Workout> getWorkoutRandom(Workout currentWorkout) {
        return null;
    }

    /********************************************************************************************
     *                               MODIFIERS!
     * ********************************************************************************************/
    public Workout insertWorkout(Workout u) {
        Workout output = null;
        if (table.put(u.getWorkoutID(),u) == null)
            output = u;
        return output;
    }

    public void deleteWorkout(Workout u) {
        int output;
        if (table.remove(u.getWorkoutID()) == null)
            output = -1;
        else {
            output = 1;
        }
    }

    public Workout updateWorkout(Workout u) {
        Workout output = null;
        if (table.containsKey(u.getWorkoutID())) {
            table.put(u.getWorkoutID(), u);
            output = u;
        }
        return output;
    }

}
