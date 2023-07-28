package com.example.fitness_tracker.domain_specific_objects;


import java.util.Objects;

/**
 * The UserWeight
 */
public class UserWeight  {

    /**
     * the current user
     */
    private final User user;

    /**
     * the current weight
     */
    private Weight weight;

    /**
     * the date (YYYY/MM/DD)
     */
    private final String date;

    /**
     * the time (24 hour clock HH:MM)
     */
    private final String time;

    //private WeightLog weight_log; DEPRECATED

    /**
     * CONSTRUCTOR
     * @param user the current user
     * @param weight the weight
     * @param date the date
     * @param time the time
     */
    public UserWeight(User user, Weight weight, String date, String time) {
        this.user = user;
        this.weight = weight;
        this.date = date;
        this.time = time;
        //this.weight_log = new WeightLog(weight,date);
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
        UserWeight that = (UserWeight) o;
        return user.equals(that.user) && weight.equals(that.weight);
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public User getUser() {
        return user;
    }

    public Weight getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, weight, date, time);
    }

    /**
     * standard toString
     */
    @Override
    public String toString() {
        return "UserWeight{" +
                "user=" + user +
                ", weight=" + weight +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }


}


