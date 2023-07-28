package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.persistence.IFoodPersistence;

import java.util.HashMap;
import java.util.List;

public class AccessFoodFake implements IFoodPersistence {

    HashMap<Integer, Food> table;

    public AccessFoodFake() {
        table = new HashMap<>();
        Food food1 = new Food(1,"Apple",80);
        Food food2 = new Food(2,"Peanut Butter",90);
        Food food3 = new Food(3,"Almond Milk",60);
        table.put(1,food1);
        table.put(2,food2);
        table.put(3,food3);
    }

    @Override
    public List<Food> getFoodSequential() {
        return null;
    }

    @Override
    public List<Food> getFoodRandom(Food currentFood) {
        return null;
    }

    public Food getFood(int foodID) {
        return table.get(foodID);
    }

    @Override
    public Food insertFood(Food currentFood) {
        Food output = null;
        if (table.put(currentFood.getId(),currentFood) == null)
            output = currentFood;
        return output;
    }

    @Override
    public Food updateFood(Food currentFood) {
        Food output = null;
        if (table.containsKey(currentFood.getId())) {
            table.put(currentFood.getId(), currentFood);
            output = currentFood;
        }
        return output;
    }

    @Override
    public void deleteFood(Food currentFood) {
        table.remove(currentFood.getId());
    }

    public int foodSize() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }


}
