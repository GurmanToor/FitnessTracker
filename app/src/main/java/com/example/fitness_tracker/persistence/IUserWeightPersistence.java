package com.example.fitness_tracker.persistence;

import java.util.List;

import com.example.fitness_tracker.domain_specific_objects.UserWeight;


public interface IUserWeightPersistence {

    List<UserWeight> getUserWeight(final String username);

    List<UserWeight> getWeightUser(final int weightID);

    UserWeight insertUserWeight(UserWeight currentUserWeight);

    UserWeight updateUserWeight(UserWeight currentUserWeight);

    void deleteUserWeight(UserWeight currentUserWeight);

    int userWeightSize();
}
