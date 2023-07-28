package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IExerciseWorkoutPersistence;

import java.util.ArrayList;
import java.util.List;

/**
 * AccessExerciseWorkout.java
 *
 * PURPOSE: A fake implementation for a table that contains ExerciseWorkout objects
 */
public class AccessExerciseWorkoutFake implements IExerciseWorkoutPersistence {

    /**
     * the table
     */
    private final ArrayList<ExerciseWorkout> table;

    /**
     * CONSTRUCTOR
     */
    public AccessExerciseWorkoutFake() {
        table = new ArrayList<>();
        Exercise ex1 = new Strength(1,"Bicep Curls","Bicep","Strength",10,3,"00:08:42");
        Exercise ex2 = new Cardio(2,"Basketball Game","Heart","Cardio",30,"km","00:58:44");
        Exercise ex3 = new Strength(3,"Barbell Bench Press","Chest","Strength",5,5,"00:13:25");
        Workout workout1 = new Workout(1,"Push Day","00:34:27");
        Workout workout2 = new Workout(2,"Pull Day","00:45:35");
        Workout workout3 = new Workout(3,"Leg Day","00:57:32");
        table.add(new ExerciseWorkout(ex1,workout1));
        table.add(new ExerciseWorkout(ex2,workout1));
        table.add(new ExerciseWorkout(ex3,workout2));
        table.add(new ExerciseWorkout(ex1,workout2));
        table.add(new ExerciseWorkout(ex1,workout3));
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    @Override
    public List<ExerciseWorkout> getExerciseWorkout(int id) {
        ArrayList<ExerciseWorkout> output = new ArrayList<>();
        for (ExerciseWorkout i : table) {
            if (i.getExercise().getExerciseID() == id)
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output; //returns a List of all ExerciseWorkout objects that satisfy the given ExerciseID
    }

    @Override
    public List<ExerciseWorkout> getWorkoutExercise(int id) {
        ArrayList<ExerciseWorkout> output = new ArrayList<>();
        for (ExerciseWorkout i : table) {
            if (i.getWorkout().getWorkoutID() == id)
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output; //returns a List of all ExerciseWorkout objects in the table that satisfy the given WorkoutID
    }

    public List<ExerciseWorkout> getAll() {
        ArrayList<ExerciseWorkout> output = new ArrayList<>();
        output.addAll(table);
        return output; //returns all ExerciseWorkout elements in the table
    }

    public int exerciseWorkoutSize() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    /********************************************************************************************
     *                               MODIFIERS!
     * ********************************************************************************************/

    @Override
    public ExerciseWorkout insertExerciseWorkout(ExerciseWorkout w) {
        ExerciseWorkout output = null;
        if (!table.contains(w)) {
            table.add(w);
            output = w; //Element was successfully added
        }
        return output;
    }

    public ExerciseWorkout updateExerciseWorkout(ExerciseWorkout EW) {
        ExerciseWorkout output = null;
        if (table.contains(EW)) {
            table.set(table.indexOf(EW),EW);
            output = EW;
        }
        return output;
    }

    public void deleteExerciseWorkout(ExerciseWorkout w){
        int output;
        if (table.remove(w))
                output = 1;
    }


}

