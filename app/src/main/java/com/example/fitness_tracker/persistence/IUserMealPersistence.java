package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.UserMeal;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;

import java.util.List;

public interface IUserMealPersistence {

    List<UserMeal> getUserMeal(final String username);

    List<UserMeal> getMealUser(final int mealID);

    UserMeal insertUserMeal(UserMeal currentUserMeal);

    UserMeal updateUserMeal(UserMeal currentUserMeal);

    void deleteUserMeal(UserMeal currentUserMeal);

    int userMealSize();
}
