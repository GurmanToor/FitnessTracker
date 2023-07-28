package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.persistence.IFoodPersistence;

import java.util.Collections;
import java.util.List;

public class AccessFood {

    private IFoodPersistence foodPersistence;
    private List<Food> foods;
    private Food food;
    private int currentFood;

    public AccessFood()
    {
        foodPersistence = Services.getFoodPersistence();
        foods = null;
        food = null;
        currentFood = 0;
    }

    public AccessFood(final IFoodPersistence foodPersistence) {
        this();
        this.foodPersistence = foodPersistence;
    }

    public List<Food> getFoods()
    {
        foods = foodPersistence.getFoodSequential();
        return Collections.unmodifiableList(foods);
    }

    public Food getSequential()
    {
        if (foods == null)
        {
            foods = foodPersistence.getFoodSequential();
            currentFood = 0;
        }
        if (currentFood < foods.size())
        {
            food = foods.get(currentFood);
            currentFood++;
        }
        else
        {
            foods = null;
            food = null;
            currentFood = 0;
        }
        return food;
    }

    public Food getRandom(int foodID)
    {
        food = null;
        if (foodID < 0)
        {
            //System.out.println("*** Invalid foodID");
        }
        else
        {
            foods = foodPersistence.getFoodRandom(new Food(foodID));
            if (foods.size()==1)
            {
                food = (Food) foods.get(0);
            }
        }
        return food;
    }

    public Food insertFood(Food currentFood)
    {
        return foodPersistence.insertFood(currentFood);
    }

    public Food updateFood(Food currentFood)
    {
        return foodPersistence.updateFood(currentFood);
    }

    public void deleteFood(Food currentFood)
    {
        foodPersistence.deleteFood(currentFood);
    }

    public int foodSize() {
        return foodPersistence.foodSize();
    }
}
