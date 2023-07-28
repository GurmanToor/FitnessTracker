package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserMeal;
import com.example.fitness_tracker.persistence.IUserMealPersistence;

import java.math.BigDecimal;
import java.util.List;

public class AccessUserMeal {

    private IUserMealPersistence dataAccess;
    private List<UserMeal> elements;

    private UserMeal userMeal;
    private int currentUM;

    private int currentMU;

    public AccessUserMeal()
    {
        dataAccess = Services.getUserMealPersistence();
        elements = null;
        currentUM = 0;
        currentMU = 0;
    }

    public AccessUserMeal(final IUserMealPersistence umPersistence) {
        this();
        this.dataAccess = umPersistence;
    }

    public List<UserMeal> getUserMeal(String username) {
        return dataAccess.getUserMeal(username);
    }

    public List<UserMeal> getMealUser(int mealID) {
        return dataAccess.getMealUser(mealID);
    }

//    public UserMeal getUserMeal(String username)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getUserMeal(username);
//            currentUM = 0;
//        }
//        if (currentUM < elements.size())
//        {
//            userMeal = elements.get(currentUM);
//            currentUM++;
//        }
//        else
//        {
//            elements = null;
//            userMeal = null;
//            currentUM = 0;
//        }
//        return userMeal;
//    }
//
//    public UserMeal getMealUser(int mealID)
//    {
//        if (elements == null)
//        {
//            elements = dataAccess.getMealUser(mealID);
//            currentMU = 0;
//        }
//        if (currentMU < elements.size())
//        {
//            userMeal = elements.get(currentMU);
//            currentMU++;
//        }
//        else
//        {
//            elements = null;
//            userMeal = null;
//            currentMU = 0;
//        }
//        return userMeal;
//    }

    public UserMeal insertUserMeal(UserMeal currentUserMeal)
    {
        return dataAccess.insertUserMeal(currentUserMeal);
    }

    public UserMeal updateUserMeal(UserMeal currentUserMeal)
    {
        return dataAccess.updateUserMeal(currentUserMeal);
    }

    public void deleteUserMeal(UserMeal currentUserMeal)
    {
        dataAccess.deleteUserMeal(currentUserMeal);
    }

    public int userMealSize() {
        return dataAccess.userMealSize();
    }
}
