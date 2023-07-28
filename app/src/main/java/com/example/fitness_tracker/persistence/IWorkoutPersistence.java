package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.Workout;

import java.util.List;

public interface IWorkoutPersistence {
    List<Workout> getWorkoutSequential();

    List<Workout> getWorkoutRandom(final Workout currentWorkout);

    Workout insertWorkout(final Workout currentWorkout);

    Workout updateWorkout(final Workout currentWorkout);

    void deleteWorkout(final Workout currentWorkout);

    int workoutSize();
}
