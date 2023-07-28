package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;

import java.util.List;

public interface IUserWorkoutPersistence {
    List<UserWorkout> getUserWorkout(final String username);

    List<UserWorkout> getWorkoutUser(final int workoutID);

    UserWorkout insertUserWorkout(UserWorkout currentUserWorkout);

    UserWorkout updateUserWorkout(UserWorkout currentUserWorkout);

    void deleteUserWorkout(UserWorkout currentUserWorkout);

    int userWorkoutSize();
}
