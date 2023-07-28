package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.domain_specific_objects.FoodMeal;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.persistence.IFoodMealPersistence;

import java.util.List;

public class AccessFoodMeal {

    private IFoodMealPersistence dataAccess;
    private List<FoodMeal> elements;

    private FoodMeal foodMeal;
    private int currentFM;
    private int currentMF;

    public AccessFoodMeal()
    {
        dataAccess = Services.getFoodMealPersistence();
        elements = null;
        currentFM = 0;
        currentMF = 0;
    }

    public AccessFoodMeal(final IFoodMealPersistence fmPersistence) {
        this();
        this.dataAccess = fmPersistence;
    }

    public List<FoodMeal> getFoodMeal(int foodID) {
        return dataAccess.getFoodMeal(foodID);
    }

    public List<FoodMeal> getMealFood(int mealID) {
        return dataAccess.getMealFood(mealID);
    }

//    public FoodMeal getFoodMeal(int foodID)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getFoodMeal(foodID);
//            currentFM = 0;
//        }
//        if (currentFM < elements.size())
//        {
//            foodMeal = elements.get(currentFM);
//            currentFM++;
//        }
//        else
//        {
//            elements = null;
//            foodMeal = null;
//            currentFM = 0;
//        }
//        return foodMeal;
//    }
//
//    public FoodMeal getMealFood(int mealID)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getFoodMeal(mealID);
//            currentMF = 0;
//        }
//        if (currentMF < elements.size())
//        {
//            foodMeal = elements.get(currentMF);
//            currentMF++;
//        }
//        else
//        {
//            elements = null;
//            foodMeal = null;
//            currentMF = 0;
//        }
//        return foodMeal;
//    }

    public FoodMeal insertFoodMeal(FoodMeal currentFoodMeal)
    {
        return dataAccess.insertFoodMeal(currentFoodMeal);
    }

    public void deleteFoodMeal(FoodMeal currentFoodMeal)
    {
        dataAccess.deleteFoodMeal(currentFoodMeal);
    }

    public int foodMealSize() {
        return dataAccess.foodMealSize();
    }
}
