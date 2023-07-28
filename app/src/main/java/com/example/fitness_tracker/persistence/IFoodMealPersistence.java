package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.FoodMeal;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;

import java.util.List;

public interface IFoodMealPersistence {

    List<FoodMeal> getFoodMeal(final int foodID);

    List<FoodMeal> getMealFood(final int mealID);

    FoodMeal insertFoodMeal(FoodMeal currentFoodMeal);

    void deleteFoodMeal(FoodMeal currentFoodMeal);

    //FoodMeal updateFoodMeal(final FoodMeal currentFoodMeal);

    int foodMealSize();

}
