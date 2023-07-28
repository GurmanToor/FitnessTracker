package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IUserWorkoutPersistence;

import java.math.BigDecimal;
import java.util.List;

public class AccessUserWorkout {

    private IUserWorkoutPersistence dataAccess;
    private List<UserWorkout> elements;

    private UserWorkout userWorkout;
    private int currentUW;

    private int currentWU;

    public AccessUserWorkout()
    {
        dataAccess = Services.getUserWorkoutPersistence();
        elements = null;
        currentUW = 0;
        currentWU = 0;
    }

    public AccessUserWorkout(final IUserWorkoutPersistence uwPersistence) {
        this();
        this.dataAccess = uwPersistence;
    }

    public List<UserWorkout> getUserWorkout(String username) {
        return dataAccess.getUserWorkout(username);
    }

    public List<UserWorkout> getWorkoutUser(int workoutID) {
        return dataAccess.getWorkoutUser(workoutID);
    }

//    public UserWorkout getUserWorkout(String username)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getUserWorkout(username);
//            currentUW = 0;
//        }
//        if (currentUW < elements.size())
//        {
//            userWorkout = elements.get(currentUW);
//            currentUW++;
//        }
//        else
//        {
//            elements = null;
//            userWorkout = null;
//            currentUW = 0;
//        }
//        return userWorkout;
//    }
//
//    public UserWorkout getWorkoutUser(int workoutID)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getWorkoutUser(workoutID);
//            currentWU = 0;
//        }
//        if (currentWU < elements.size())
//        {
//            userWorkout = elements.get(currentWU);
//            currentWU++;
//        }
//        else
//        {
//            elements = null;
//            userWorkout = null;
//            currentWU = 0;
//        }
//        return userWorkout;
//    }

    public UserWorkout insertUserWorkout(UserWorkout currentUserWorkout)
    {
        return dataAccess.insertUserWorkout(currentUserWorkout);
    }

    public UserWorkout updateUserWorkout(UserWorkout currentUserWorkout)
    {
        return dataAccess.updateUserWorkout(currentUserWorkout);
    }

    public void deleteUserWorkout(UserWorkout currentUserWorkout)
    {
        dataAccess.deleteUserWorkout(currentUserWorkout);
    }

    public int userWorkoutSize() {
        return dataAccess.userWorkoutSize();
    }

}
