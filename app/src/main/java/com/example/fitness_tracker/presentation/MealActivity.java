package com.example.fitness_tracker.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.Food;
import com.example.fitness_tracker.domain_specific_objects.FoodMeal;
import com.example.fitness_tracker.domain_specific_objects.Meal;
import com.google.android.material.snackbar.Snackbar;


public class MealActivity extends AppCompatActivity {


    RealDatabase realDatabase = RealDatabase.getDatabaseInstance();

    TextView mealName;
    TextView foodName;
    TextView foodCalories;
    Button backButton;
    Button saveButton;


    private static int mealId = 1;
    private static int foodId = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);

        mealName = findViewById(R.id.meal_name);
        foodName = findViewById(R.id.food_name);
        foodCalories = findViewById(R.id.food_calories);
        backButton= findViewById(R.id.go_back);
        saveButton=findViewById(R.id.save_button);
        goBackAPage();
        createMeal();
    }

    /**
     * Back button method
     *
     *
     */
    public void goBackAPage(){
        // This will go back to the home page of the APP from create meal page
        // Source: https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        backButton.setOnClickListener(view -> {
            //create new intent
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        });
    }

    /**
     *
     *
     * When save button pressed, this method is called, it will take all the data and
     * create a new FoodMeal object
     *
     */
    public void createMeal(){

        saveButton.setOnClickListener(view -> {
            if(mealName.getText().toString()!="" && foodName.getText().toString()!="" && foodCalories.getText().toString()!="") {
                    Food food = new Food(foodId++,  mealName.getText().toString(), Integer.valueOf(foodCalories.getText().toString()));

                    Meal meal = new Meal(mealId++, mealName.getText().toString());

                    FoodMeal foodMeal = new FoodMeal(food, meal);

                    foodMeal.getMeal();

            Snackbar.make(view, "Successfully added meal", Snackbar.LENGTH_SHORT).show();
            }

        });

    }

}
