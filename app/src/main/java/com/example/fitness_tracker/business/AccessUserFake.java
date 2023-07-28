package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.persistence.IUserPersistence;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * AccessUser.java
 *
 * PURPOSE: A fake implementation for a table that contains User objects
 */
public class AccessUserFake implements IUserPersistence {

    /**
     * The table
     */
    private HashMap<String, User> table;

    /**
     * CONSTRUCTOR
     */
    public AccessUserFake() {
        table = new HashMap<String,User>();
        User user1 = new User("gurman123","password123","Gurman","Toor",new BigDecimal("141.5"),"toorg1@myumanitoba.ca");
        User user2 = new User("janesmith123","password123","Jane","Smith",new BigDecimal("123.8"),"janesmith@yahoo.ca");
        User user3 = new User("mikejames123","password123","Mike","James",new BigDecimal("210.2"),"mikejames@gmail.com");
        table.put("gurman123",user1);
        table.put("janesmith123",user2);
        table.put("mikejames123",user3);
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public User getUserByID(String username) {
        return (User)table.get(username); //returns null if User with username does not exist
    }

    public List<User> getAllUsers() {
        return (List<User>) table.values();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public int userSize() {
        return table.size();
    }

    @Override
    public List<User> getUserSequential() {
        return null;
    }

    @Override
    public List<User> getUserRandom(User currentUser) {
        return null;
    }

    /********************************************************************************************
     *                               MODIFIERS!
     * ********************************************************************************************/
    public User insertUser(User u) {
        User output = null;
        if (table.put(u.getUsername(),u) == null)
            output = u;
        return output;
    }

    public void deleteUser(User u) {
        int output;
        if (table.remove(u.getUsername()) == null)
            output = -1;
        else {
            output = 1;
        }
    }

    public User updateUser(User u) {
        User output = null;
        if (table.containsKey(u.getUsername())) {
            table.put(u.getUsername(), u);
            output = u;
        }
        return output;
    }

}
