package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.IUserWeightPersistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * AccessUserWeight.java
 *
 * PURPOSE: A fake implementation for a table that contains UserWeight objects
 */
public class AccessUserWeightFake implements IUserWeightPersistence {

    /**
     * the table
     */
    private final ArrayList<UserWeight> table;

    /**
     * CONSTRUCTOR
     */
    public AccessUserWeightFake() {
        table = new ArrayList<>();
        User user1 = new User("gurman123","password123","Gurman","Toor",new BigDecimal("141.5"),"toorg1@myumanitoba.ca");
        User user2 = new User("janesmith123","password123","Jane","Smith",new BigDecimal("123.8"),"janesmith@yahoo.ca");
        User user3 = new User("mikejames123","password123","Mike","James",new BigDecimal("210.2"),"mikejames@gmail.com");
        Weight weight1 = new Weight(1,new BigDecimal("150.0"));
        Weight weight2 = new Weight(2,new BigDecimal("180.5"));
        Weight weight3 = new Weight(3,new BigDecimal("140.2"));
        table.add(new UserWeight(user1,weight1,"2021/12/25","10:25"));
        table.add(new UserWeight(user2,weight2,"2021/12/28","18:23"));
        table.add(new UserWeight(user3,weight3,"2022/01/02","08:23"));
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public List<UserWeight> getUserWeight(String username) {
        ArrayList<UserWeight> output = new ArrayList<>();
        for (UserWeight i: table) {
            if (i.getUser().getUsername().equals(username))
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output; //returns a List of all UserWeight objects in the table that satisfy the given username
        // and returns null if there are no objects with that username in the table
    }

    public List<UserWeight> getWeightUser(int id) {
        ArrayList<UserWeight> output = new ArrayList<>();
        for (UserWeight i: table) {
            if (i.getWeight().getWeightID() == id)
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output; //returns a List of all UserWeight objects in the table that satisfy the given weightID
        // and returns null if there are no objects with that weightID in the table
    }

    public List<UserWeight> getAll() {
        ArrayList<UserWeight> output = new ArrayList<>();
        output.addAll(table);
        return output; //returns all UserWeight objects in the table
    }

    public int userWeightSize() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    /********************************************************************************************
     *                               MODIFIERS!
     * ********************************************************************************************/
    @Override
    public UserWeight insertUserWeight(UserWeight w) {
        UserWeight output = null;
        if (!table.contains(w)) {
            table.add(w);
            output = w; //Element was successfully added
        }
        return output;
    }

    @Override
    public void deleteUserWeight(UserWeight w) {
        int output = -1;
        User u = w.getUser();
        Weight we = w.getWeight();
        UserWeight uw;
        for (int i = 0; i < table.size(); i++) {
            uw = table.get(i);
            if (uw.getUser().equals(u) && uw.getWeight().equals(we) && uw.getDate().equals(w.getDate()) && uw.getTime().equals(w.getTime())) {
                table.remove(i);
                output = 1;
            }
        }
    }

    @Override
    public UserWeight updateUserWeight(UserWeight w) {
        UserWeight output = null;
        if (table.contains(w)) {
            table.set(table.indexOf(w),w);
            output = w;
        }
        return output;
    }
}
