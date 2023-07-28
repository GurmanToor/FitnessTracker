package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserMeal;
import com.example.fitness_tracker.persistence.IUserMealPersistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccessUserMealFake implements IUserMealPersistence {

    ArrayList<UserMeal> table;

    public AccessUserMealFake() {
        table = new ArrayList<>();
        Meal meal1 = new Meal(1,"Breakfast");
        Meal meal2 = new Meal(2,"Lunch");
        Meal meal3 = new Meal(3,"Dinner");
        User user1 = new User("gurman123","password123","Gurman","Toor",new BigDecimal("141.5"), "toorg1@myumanitoba.ca");
        User user2 = new User("janesmith123","password123","Jane","Smith",new BigDecimal("123.8"),"janesmith@yahoo.ca");
        User user3 = new User("mikejames123","password123","Mike","James",new BigDecimal("210.2"),"mikejames@gmail.com");
        table.add(new UserMeal(user1,meal2,"2021/12/25","14:25"));
        table.add(new UserMeal(user2,meal1,"2022/02/12","12:13"));
        table.add(new UserMeal(user3,meal3,"2022/03/12","19:33"));
    }

    @Override
    public List<UserMeal> getUserMeal(String username) {
        ArrayList<UserMeal> output = new ArrayList<>();
        for (UserMeal i : table) {
            if (i.getUser().getUsername().equals(username))
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output;
    }

    @Override
    public List<UserMeal> getMealUser(int mealID) {
        ArrayList<UserMeal> output = new ArrayList<>();
        for (UserMeal i : table) {
            if (i.getMeal().getId() == mealID)
                output.add(i);
        }
        if (output.size() == 0)
            output = null;
        return output;
    }

    @Override
    public UserMeal insertUserMeal(UserMeal currentUserMeal) {
        UserMeal output = null;
        if (!table.contains(currentUserMeal)) {
            table.add(currentUserMeal);
            output = currentUserMeal; //Element was successfully added
        }
        return output;
    }

    @Override
    public UserMeal updateUserMeal(UserMeal currentUserMeal) {
        UserMeal output = null;
        int index = 0;
        for (UserMeal um: table) {
            if (um.getUser().getUsername().equals(currentUserMeal.getUser().getUsername()) && um.getMeal().getId() == currentUserMeal.getMeal().getId())
                break;
            index++;
        }
        if (index >= 0 ) {
            table.add(index, currentUserMeal);
            output = currentUserMeal;
        }
        return output;
    }

    @Override
    public void deleteUserMeal(UserMeal currentUserMeal) {
        table.remove(currentUserMeal);
    }

    public int userMealSize() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }
}
