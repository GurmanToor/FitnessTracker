package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.IUserWeightPersistence;

import java.math.BigDecimal;
import java.util.List;

public class AccessUserWeight {

    private IUserWeightPersistence dataAccess;
    private List<UserWeight> elements;

    private UserWeight userWeight;
    private int currentUW;

    private int currentWU;

    public AccessUserWeight()
    {
        dataAccess = Services.getUserWeightPersistence();
        elements = null;
        currentUW = 0;
        currentWU = 0;
    }

    public AccessUserWeight(final IUserWeightPersistence uwPersistence) {
        this();
        this.dataAccess = uwPersistence;
    }

    public List<UserWeight> getUserWeight(String username) {
        return dataAccess.getUserWeight(username);
    }

    public List<UserWeight> getWeightUser(int weightID) {
        return dataAccess.getWeightUser(weightID);
    }

//    public UserWeight getUserWeight(String username)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getUserWeight(username);
//            currentUW = 0;
//        }
//        if (currentUW < elements.size())
//        {
//            userWeight = elements.get(currentUW);
//            currentUW++;
//        }
//        else
//        {
//            elements = null;
//            userWeight = null;
//            currentUW = 0;
//        }
//        return userWeight;
//    }
//
//    public UserWeight getWeightUser(int weightID)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getWeightUser(weightID);
//            currentWU = 0;
//        }
//        if (currentWU < elements.size())
//        {
//            userWeight = elements.get(currentWU);
//            currentWU++;
//        }
//        else
//        {
//            elements = null;
//            userWeight = null;
//            currentWU = 0;
//        }
//        return userWeight;
//    }

    public UserWeight insertUserWeight(UserWeight currentUserWeight)
    {
        return dataAccess.insertUserWeight(currentUserWeight);
    }

    public UserWeight updateUserWeight(UserWeight currentUserWeight)
    {
        return dataAccess.updateUserWeight(currentUserWeight);
    }

    public void deleteUserWeight(UserWeight currentUserWeight)
    {
        dataAccess.deleteUserWeight(currentUserWeight);
    }

    public int userWeightSize() {
        return dataAccess.userWeightSize();
    }
}
