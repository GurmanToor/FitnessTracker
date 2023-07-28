package com.example.fitness_tracker.domain_specific_objects;

import java.util.Objects;

/**
 * Exercise Workout for the given workout with the exercise object
 */
public class ExerciseWorkout {

    /**
     * the current exercise
     */
    private final Exercise exercise;

    /**
     * the current workout
     */
    private final Workout workout;

    /**
     * CONSTRUCTOR
     * @param exercise the current exercise
     * @param workout the current workout
     */
    public ExerciseWorkout(Exercise exercise, Workout workout) {
        this.exercise = exercise;
        this.workout = workout;
    }
    /**
     * equals
     *
     * checks if the given object is equal to the current workout object
     * @param o the object
     * @return if it is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseWorkout that = (ExerciseWorkout) o;
        return exercise.equals(that.exercise) && workout.equals(that.workout);
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public Exercise getExercise() {
        return exercise;
    }

    public Workout getWorkout() {
        return workout;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exercise, workout);
    }

    /**
     * standard toString
     */
    @Override
    public String toString() {
        return "ExerciseWorkout{" +
                "exercise=" + exercise +
                ", workout=" + workout +
                '}';
    }
}
