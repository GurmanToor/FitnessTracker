package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IExerciseWorkoutPersistence;

import java.util.List;

public class AccessExerciseWorkout {

    private IExerciseWorkoutPersistence dataAccess;
    private List<ExerciseWorkout> elements;

    private ExerciseWorkout exerciseWorkout;
    private int currentEW;

    private int currentWE;

    public AccessExerciseWorkout()
    {
        dataAccess = Services.getExerciseWorkoutPersistence();
        elements = null;
        currentEW = 0;
        currentWE = 0;
    }

    public AccessExerciseWorkout(final IExerciseWorkoutPersistence ewPersistence) {
        this();
        this.dataAccess = ewPersistence;
    }

    public List<ExerciseWorkout> getExerciseWorkout(int exerciseID) {
        return dataAccess.getExerciseWorkout(exerciseID);
    }

    public List<ExerciseWorkout> getWorkoutExercise(int workoutID) {
        return dataAccess.getWorkoutExercise(workoutID);
    }

//    public ExerciseWorkout getExerciseWorkout(int exerciseID)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getExerciseWorkout(exerciseID);
//            currentEW = 0;
//        }
//        if (currentEW < elements.size())
//        {
//            exerciseWorkout = elements.get(currentEW);
//            currentEW++;
//        }
//        else
//        {
//            elements = null;
//            exerciseWorkout = null;
//            currentEW = 0;
//        }
//        return exerciseWorkout;
//    }
//
//    public ExerciseWorkout getWorkoutExercise(int workoutID)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getWorkoutExercise(workoutID);
//            currentWE = 0;
//        }
//        if (currentWE < elements.size())
//        {
//            exerciseWorkout = elements.get(currentWE);
//            currentWE++;
//        }
//        else
//        {
//            elements = null;
//            exerciseWorkout = null;
//            currentWE = 0;
//        }
//        return exerciseWorkout;
//    }

    public ExerciseWorkout insertExerciseWorkout(ExerciseWorkout currentExerciseWorkout)
    {
        return dataAccess.insertExerciseWorkout(currentExerciseWorkout);
    }

    public void deleteExerciseWorkout(ExerciseWorkout currentExerciseWorkout)
    {
        dataAccess.deleteExerciseWorkout(currentExerciseWorkout);
    }

    public int exerciseWorkoutSize() {
        return dataAccess.exerciseWorkoutSize();
    }

}
