package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.domain_specific_objects.FoodMeal;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.persistence.IFoodMealPersistence;

import java.util.ArrayList;
import java.util.List;

public class AccessFoodMealFake implements IFoodMealPersistence {

    ArrayList<FoodMeal> table;

    public AccessFoodMealFake() {
        table = new ArrayList<>();
        Food food1 = new Food(1,"Apple",80);
        Food food2 = new Food(2,"Peanut Butter",90);
        Food food3 = new Food(3,"Almond Milk",60);
        Meal meal1 = new Meal(1,"Breakfast");
        Meal meal2 = new Meal(2,"Lunch");
        Meal meal3 = new Meal(3,"Dinner");
        table.add(new FoodMeal(food1,meal1));
        table.add(new FoodMeal(food2,meal1));
        table.add(new FoodMeal(food3,meal2));
        table.add(new FoodMeal(food2,meal3));
    }

    @Override
    public List<FoodMeal> getFoodMeal(int foodID) {
        ArrayList<FoodMeal> output = new ArrayList<>();
        for (FoodMeal i : table) {
            if (i.getFood().getId() == foodID)
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output;
    }

    @Override
    public List<FoodMeal> getMealFood(int mealID) {
        ArrayList<FoodMeal> output = new ArrayList<>();
        for (FoodMeal i : table) {
            if (i.getMeal().getId() == mealID)
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output;
    }

    @Override
    public FoodMeal insertFoodMeal(FoodMeal currentFoodMeal) {
        FoodMeal output = null;
        if (!table.contains(currentFoodMeal)) {
            table.add(currentFoodMeal);
            output = currentFoodMeal; //Element was successfully added
        }
        return output;
    }

    @Override
    public void deleteFoodMeal(FoodMeal currentFoodMeal) {
        table.remove(currentFoodMeal);
    }

    public int foodMealSize() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }
}
