package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.Exercise;

import java.util.List;

public interface IExercisePersistence {
    List<Exercise> getExerciseSequential();

    List<Exercise> getExerciseRandom(final Exercise currentExercise);

    Exercise insertExercise(final Exercise currentExercise);

    Exercise updateExercise(final Exercise currentExercise);

    void deleteExercise(final Exercise currentExercise);

    int exerciseSize();
}
