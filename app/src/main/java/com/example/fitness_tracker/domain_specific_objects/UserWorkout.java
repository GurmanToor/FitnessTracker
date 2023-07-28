package com.example.fitness_tracker.domain_specific_objects;

import java.util.Objects;

/**
 * The UserWorkout
 */
public class UserWorkout {

    /**
     * the current user
     */
    private final User user;

    /**
     * the given workout
     */
    private final Workout workout;

    /**
     * the current time
     */
    private final String time;

    /**
     * the current time
     */
    private final String date;

    /**
     * CONSTRUCTOR
     * @param user the user
     * @param workout the workout
     * @param time the time
     */
    public UserWorkout(User user, Workout workout, String date, String time) {
        this.user = user;
        this.workout = workout;
        this.date = date;
        this.time = time;
    }
    /**
     * equals
     *
     * checks if the given object is equal to the current workout object
     * @param o the object
     * @return if it is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWorkout that = (UserWorkout) o;
        return user.equals(that.user) && workout.equals(that.workout);
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public User getUser() {
        return user;
    }

    public Workout getWorkout() {
        return workout;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, workout, time, date);
    }

    /**
     * standard toString
     */
    @Override
    public String toString() {
        return "UserWorkout{" +
                "user=" + user +
                ", workout=" + workout +
                ", date=" + date +
                ", time='" + time + '\'' +
                '}';
    }
}
