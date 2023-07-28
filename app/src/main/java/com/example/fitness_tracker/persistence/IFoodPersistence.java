package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.domain_specific_objects.Weight;

import java.util.List;

public interface IFoodPersistence {

    List<Food> getFoodSequential();

    List<Food> getFoodRandom(final Food currentFood);

    Food insertFood(final Food currentFood);

    Food updateFood(final Food currentFood);

    void deleteFood(final Food currentFood);

    int foodSize();
}
