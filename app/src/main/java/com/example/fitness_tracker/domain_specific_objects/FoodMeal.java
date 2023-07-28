package com.example.fitness_tracker.domain_specific_objects;

import java.util.Objects;

public class FoodMeal {

    private Food food;
    private Meal meal;

    public FoodMeal(Food food, Meal meal) {
        this.food = food;
        this.meal = meal;
    }

    public Food getFood() {
        return food;
    }

    public Meal getMeal() {
        return meal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodMeal foodMeal = (FoodMeal) o;
        return Objects.equals(food, foodMeal.food) && Objects.equals(meal, foodMeal.meal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(food, meal);
    }

    @Override
    public String toString() {
        return "FoodMeal{" +
                "food=" + food +
                ", meal=" + meal +
                '}';
    }
}
