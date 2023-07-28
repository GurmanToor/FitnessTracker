package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.domain_specific_objects.Weight;

import java.util.List;

public interface IMealPersistence {

    List<Meal> getMealSequential();

    List<Meal> getMealRandom(final Meal currentMeal);

    Meal insertMeal(final Meal currentMeal);

    Meal updateMeal(final Meal currentMeal);

    void deleteMeal(final Meal currentMeal);

    int mealSize();
}
