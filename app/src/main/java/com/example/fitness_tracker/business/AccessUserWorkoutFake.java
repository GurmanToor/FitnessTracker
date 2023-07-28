package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IUserWorkoutPersistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * AccessUserWorkout.java
 *
 * PURPOSE: A fake implementation for a table that contains UserWorkout objects
 */
public class AccessUserWorkoutFake implements IUserWorkoutPersistence {

    /**
     * the table
     */
    private final ArrayList<UserWorkout> table;

    /**
     * CONSTRUCTOR
     */
    public AccessUserWorkoutFake() {
        table = new ArrayList<>();
        User user1 = new User("gurman123","password123","Gurman","Toor",new BigDecimal("141.5"), "toorg1@myumanitoba.ca");
        User user2 = new User("janesmith123","password123","Jane","Smith",new BigDecimal("123.8"),"janesmith@yahoo.ca");
        User user3 = new User("mikejames123","password123","Mike","James",new BigDecimal("210.2"),"mikejames@gmail.com");
        Workout workout1 = new Workout(1,"2022/01/01","00:34:27");
        Workout workout2 = new Workout(2,"2022/01/02","00:45:35");
        Workout workout3 = new Workout(3,"2022/01/03","00:57:32");
        table.add(new UserWorkout(user1,workout1,"2022/01/01","18:00"));
        table.add(new UserWorkout(user2,workout2,"2022/01/02","19:00"));
        table.add(new UserWorkout(user3,workout3,"2022/01/03","20:00"));
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    @Override
    public List<UserWorkout> getUserWorkout(String username) {
        ArrayList<UserWorkout> output = new ArrayList<>();
        for (UserWorkout i: table) {
            if (i.getUser().getUsername().equals(username))
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output; //returns a List of all UserWorkout objects in the table that satisfy the given username
        // and returns null if there are no objects with that username in the table
    }

    @Override
    public List<UserWorkout> getWorkoutUser(int id) {
        ArrayList<UserWorkout> output = new ArrayList<>();
        for (UserWorkout i: table) {
            if (i.getWorkout().getWorkoutID() == id)
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output; //returns a List of all UserWeight objects in the table that satisfy the given workoutID
        // and returns null if there are no objects with that workoutID in the table
    }

    public List<UserWorkout> getAll() {
        ArrayList<UserWorkout> output = new ArrayList<>();
        output.addAll(table);
        return output; //returns all UserWorkout objects in the table
    }

    public int userWorkoutSize() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    /********************************************************************************************
     *                               MODIFIERS!
     * ********************************************************************************************/
    @Override
    public UserWorkout insertUserWorkout(UserWorkout w) {
        UserWorkout output = null;
        if (!table.contains(w)) {
            table.add(w);
            output = w; //Element was successfully added
        }
        return output;
    }

    @Override
    public void deleteUserWorkout(UserWorkout w) {
        int output;
        if (table.remove(w))
            output = 1;
        else {
            output = -1;
        }
    }

    @Override
    public UserWorkout updateUserWorkout(UserWorkout w) {
        UserWorkout output = null;
        if (table.contains(w)) {
            table.set(table.indexOf(w),w);
            output = w;
        }
        return output;
    }
}
