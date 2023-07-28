package com.example.fitness_tracker.domain_specific_objects;

import java.util.Objects;

public class UserMeal {

    private User user;
    private Meal meal;
    private String date;
    private String time;

    public UserMeal(User user, Meal meal, String date, String time) {
        this.user = user;
        this.meal = meal;
        this.date = date;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public Meal getMeal() {
        return meal;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMeal userMeal = (UserMeal) o;
        return Objects.equals(user, userMeal.user) && Objects.equals(meal, userMeal.meal) && Objects.equals(date, userMeal.date) && Objects.equals(time, userMeal.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, meal, date, time);
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "user=" + user +
                ", meal=" + meal +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
