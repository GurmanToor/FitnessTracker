package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.persistence.IExercisePersistence;

import java.util.HashMap;
import java.util.List;

/**
 * AccessExercise.java
 *
 * PURPOSE: A fake implementation for a table that contains Exercise objects
 */
public class AccessExerciseFake implements IExercisePersistence {

    /**
     * the table
     */
    private HashMap<Integer, Exercise> table;

    /**
     * CONSTRUCTOR
     */
    public AccessExerciseFake() {
        table = new HashMap<Integer,Exercise>();
        Exercise ex1 = new Strength(1,"Bicep Curls","Bicep","Strength",10,3,"00:08:42");
        Exercise ex2 = new Cardio(2,"Basketball Game","Heart","Cardio",30,"km","00:58:44");
        Exercise ex3 = new Strength(3,"Barbell Bench Press","Chest","Strength",5,5,"00:13:25");
        table.put(1, ex1);
        table.put(2,ex2);
        table.put(3,ex3);
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public Exercise getExerciseByID(int id) {
        return table.get(id); //returns null if Weight with id does not exist
    }

    public List<Exercise> getAllExercises() {
        return (List<Exercise>) table.values();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public int exerciseSize() {
        return table.size();
    }

    @Override
    public List<Exercise> getExerciseSequential() {
        return null;
    }

    @Override
    public List<Exercise> getExerciseRandom(Exercise currentExercise) {
        return null;
    }

    /********************************************************************************************
     *                               MODIFIERS!
     * ********************************************************************************************/
    @Override
    public Exercise insertExercise(Exercise u) {
        Exercise output = null;
        if (table.put(u.getExerciseID(),u) == null)
            output = u;
        return output; //returns exercise added if insertion is successful and null otherwise
    }

    @Override
    public void deleteExercise(Exercise u) {
        Exercise output = null;
        if (table.remove(u.getExerciseID()) == null)
            output = u;
    }


    public Exercise updateExercise(Exercise u) {
        Exercise output = null;
        if (table.containsKey(u.getExerciseID())) {
            table.put(u.getExerciseID(), u);
            output = u;
        }
        return output;
    }
}

