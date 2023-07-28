package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.persistence.IMealPersistence;

import java.util.Collections;
import java.util.List;

public class AccessMeal {

    private IMealPersistence mealPersistence;
    private List<Meal> meals;
    private Meal meal;
    private int currentMeal;

    public AccessMeal()
    {
        mealPersistence = Services.getMealPersistence();
        meals = null;
        meal = null;
        currentMeal = 0;
    }

    public AccessMeal(final IMealPersistence mealPersistence) {
        this();
        this.mealPersistence = mealPersistence;
    }

    public List<Meal> getMeals()
    {
        meals = mealPersistence.getMealSequential();
        return Collections.unmodifiableList(meals);
    }

    public Meal getSequential()
    {
        if (meals == null)
        {
            meals = mealPersistence.getMealSequential();
            currentMeal = 0;
        }
        if (currentMeal < meals.size())
        {
            meal = meals.get(currentMeal);
            currentMeal++;
        }
        else
        {
            meals = null;
            meal = null;
            currentMeal = 0;
        }
        return meal;
    }

    public Meal getRandom(int mealID)
    {
        meal = null;
        if (mealID < 0)
        {
            //System.out.println("*** Invalid foodID");
        }
        else
        {
            meals = mealPersistence.getMealRandom(new Meal(mealID));
            if (meals.size()==1)
            {
                meal = (Meal) meals.get(0);
            }
        }
        return meal;
    }

    public Meal insertMeal(Meal currentMeal)
    {
        return mealPersistence.insertMeal(currentMeal);
    }

    public Meal updateMeal(Meal currentMeal)
    {
        return mealPersistence.updateMeal(currentMeal);
    }

    public void deleteMeal(Meal currentMeal)
    {
        mealPersistence.deleteMeal(currentMeal);
    }

    public int mealSize() {
        return mealPersistence.mealSize();
    }
}
