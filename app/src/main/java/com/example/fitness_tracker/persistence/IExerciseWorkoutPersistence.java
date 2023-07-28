package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;

import java.util.List;

public interface IExerciseWorkoutPersistence {
    List<ExerciseWorkout> getExerciseWorkout(final int exerciseID);

    List<ExerciseWorkout> getWorkoutExercise(final int workoutID);

    ExerciseWorkout insertExerciseWorkout(ExerciseWorkout currentExerciseWorkout);

    void deleteExerciseWorkout(ExerciseWorkout currentExerciseWorkout);

    //ExerciseWorkout updateExerciseWorkout(final ExerciseWorkout currentExerciseWorkout);

    int exerciseWorkoutSize();
}
