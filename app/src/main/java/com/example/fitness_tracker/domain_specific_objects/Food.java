package com.example.fitness_tracker.domain_specific_objects;

import java.math.BigDecimal;
import java.util.Objects;

public class Food {

    private int id;
    private String name;
    private int calories;

    public Food(int id, String name, int calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }

    public Food(int id) {
        this.id = id;
        name = null;
        calories = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return id == food.id && Objects.equals(name, food.name) && Objects.equals(calories, food.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, calories);
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                '}';
    }
}
