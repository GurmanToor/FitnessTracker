package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.persistence.IUserPersistence;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class AccessUser
{
    private IUserPersistence userPersistence;
    private List<User> users;
    private User user;
    private int currentUser;

    public AccessUser()
    {
        userPersistence = Services.getUserPersistence();
        users = null;
        user = null;
        currentUser = 0;
    }

    public AccessUser(final IUserPersistence userPersistence) {
        this();
        this.userPersistence = userPersistence;
    }

    public List<User> getUsers()
    {
        users = userPersistence.getUserSequential();
        return Collections.unmodifiableList(users);
    }

    public User getSequential()
    {
        if (users == null)
        {
            users = userPersistence.getUserSequential();
            currentUser = 0;
        }
        if (currentUser < users.size())
        {
            user = users.get(currentUser);
            currentUser++;
        }
        else
        {
            users = null;
            user = null;
            currentUser = 0;
        }
        return user;
    }

    public User getRandom(String username)
    {
        user = null;
        if (username.trim().equals(""))
        {
            //System.out.println("*** Invalid student number");
        }
        else
        {
            users = userPersistence.getUserRandom(new User(username));
            if (users.size()==1)
            {
                user = (User) users.get(0);
            }
        }
        return user;
    }

    public User insertUser(User currentUser)
    {
        return userPersistence.insertUser(currentUser);
    }

    public User updateUser(User currentUser)
    {
        return userPersistence.updateUser(currentUser);
    }

    public void deleteUser(User currentUser)
    {
        userPersistence.deleteUser(currentUser);
    }

    public int userSize() {
        return userPersistence.userSize();
    }
}
