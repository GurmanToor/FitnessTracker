package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.persistence.IMealPersistence;

import java.util.HashMap;
import java.util.List;

public class AccessMealFake implements IMealPersistence {

    HashMap<Integer,Meal> table;

    public AccessMealFake() {
        table = new HashMap<>();
        Meal meal1 = new Meal(1,"Breakfast");
        Meal meal2 = new Meal(2,"Lunch");
        Meal meal3 = new Meal(3,"Dinner");
        table.put(1,meal1);
        table.put(2,meal2);
        table.put(3,meal3);

    }
    @Override
    public List<Meal> getMealSequential() {
        return null;
    }

    @Override
    public List<Meal> getMealRandom(Meal currentMeal) {
        return null;
    }

    public Meal getMeal(int mealID) {
        return table.get(mealID);
    }

    @Override
    public Meal insertMeal(Meal currentMeal) {
        Meal output = null;
        if (table.put(currentMeal.getId(),currentMeal) == null)
            output = currentMeal;
        return output;
    }

    @Override
    public Meal updateMeal(Meal currentMeal) {
        Meal output = null;
        if (table.containsKey(currentMeal.getId())) {
            table.put(currentMeal.getId(), currentMeal);
            output = currentMeal;
        }
        return output;
    }

    @Override
    public void deleteMeal(Meal currentMeal) {
        table.remove(currentMeal.getId());
    }

    public int mealSize() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }
}
