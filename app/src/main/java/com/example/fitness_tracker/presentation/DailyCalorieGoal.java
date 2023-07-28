package com.example.fitness_tracker.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.business.DailyCalorieAdapter;
import com.example.fitness_tracker.business.UpdateDailyGoal;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class DailyCalorieGoal extends AppCompatActivity{

    ViewPager2 mealViewPager;
    TabLayout mealTabLayout;

    DailyCalorieAdapter mealFragmentAdapter;
    ImageView addWater;
    ImageView removeWater;
    EditText snackCalories;
    Button addSnack;
    Button backButton;
    TextView waterTaken;
    RealDatabase database = RealDatabase.getDatabaseInstance();

    int currentProgress;
    int cups ;//cups of water drank in a meal
    int amount ;//amount of water per cup
    UpdateDailyGoal updateDailyGoal;
    /**
     * onCreate
     *
     * Links the buttons and opens the activity
     * @param savedInstanceState this
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_calorie_goal);

        //get the ui components
        mealViewPager = findViewById(R.id.meal_view_pager);
        mealTabLayout = findViewById(R.id.meal_tab_layout);
        addWater = findViewById(R.id.add_water);
        removeWater = findViewById(R.id.remove_water);
        snackCalories = findViewById(R.id.snack_calories);
        addSnack = findViewById(R.id.add_snack_calories);
        backButton = findViewById(R.id.back_button);
        waterTaken = findViewById(R.id.water_taken);

        //fragment setup
        FragmentManager fragManager = getSupportFragmentManager();
        mealFragmentAdapter = new DailyCalorieAdapter(fragManager,getLifecycle());
        mealViewPager.setAdapter(mealFragmentAdapter);

        //get the selected tab
        mealTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mealViewPager.setCurrentItem(mealTabLayout.getSelectedTabPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        //handle changing to another tab
        mealViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mealTabLayout.selectTab(mealTabLayout.getTabAt(position));
            }
        });

        //get value of the progress bar from the home page
        Intent intent = getIntent();
        currentProgress = intent.getIntExtra("current progress",0);

        //water values for a meal
        cups = 0;//cups of water drank in a meal
        amount = 0;//amount of water per cup

        //logic for updating the progress bar with values
        updateDailyGoal = new UpdateDailyGoal();

        goBack();
        removeWaterCalories();
        addWaterCalories();
        addSnackCalories();
    }

    /**
     * goBack
     *
     * handles the button that takes a user back to the home page
     *
     */
    public void goBack()
    {
        //go back to the home page
        backButton.setOnClickListener(view -> {
            //create new intent
            Intent i = new Intent(this, HomeActivity.class);
            //pass the users goal progress to the next activity
            i.putExtra("new progress" ,currentProgress );
            //lunch the activity
            startActivity(i);
            finish();
        });

    }

    /**
     * removeWaterCalories
     *
     * remove the water calories entered by the user to the current amount of calories a user has
     *
     */
    public void removeWaterCalories()
    {
        //remove water
        removeWater.setOnClickListener(view -> {
            int baseCalories = 6; // amount of calories in one cup of water
            currentProgress = updateDailyGoal.addMealToProgress(currentProgress,baseCalories);
            cups -=1;
            amount -= 250;

            //ensure proper english
            if(cups == 1) {
                waterTaken.setText(amount + "ml (" + cups + " cup)");
            }
            else
            {
                waterTaken.setText(amount+"ml ("+cups+" cups)");
            }

        });
    }

    /**
     * addWaterCalories
     *
     * add the water calories entered by the user to the current amount of calories a user has
     *
     */
    public void addWaterCalories()
    {
        //add water
        addWater.setOnClickListener(view -> {

            int baseCalories = 6; // amount of calories in one cup of water
            currentProgress = updateDailyGoal.addMealToProgress(currentProgress,baseCalories);
            cups +=1;
            amount += 250;

            //ensure proper english
            if(cups == 1) {
                waterTaken.setText(amount + "ml (" + cups + " cup)");
            }
            else
            {
                waterTaken.setText(amount+"ml ("+cups+" cups)");
            }

        });
    }


    /**
     * addSnackCalories
     *
     * add the snack calories entered by the user to the current amount of calories a user has
     *
     */
    public void addSnackCalories()
    {
        // add snack calories
        addSnack.setOnClickListener(view -> {
            if (!database.getCurrentUser().isFasting()) {
                int calories;
                //get the value the user entered as the calories of the snack
                String userInput = snackCalories.getText().toString();
                if (!userInput.equals("")) {
                    //convert the string input to integer
                    calories = Integer.parseInt(userInput);

                    //clear the input field
                    snackCalories.setText("");

                    //add to the new calories to the progress
                    currentProgress = updateDailyGoal.addMealToProgress(currentProgress, calories);

                    //update the user that the input was successful
                    Snackbar.make(view, "Successfully added calories", Snackbar.LENGTH_SHORT).show();
                } else {
                    //alert the user that there is a mistake
                    Snackbar.make(view, "Enter snack calories", Snackbar.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this,"Can not log snacks in fasting mode!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * getMealCalories
     *
     * used by fragments to add the meal calories to the
     * current amount of calories a user has
     *
     * @param view the fragment calling the method
     * @param m the text field(for meal) of the fragment
     * @param m the text field(for calories) of the fragment
     */

    public void getMealCalories(View view, EditText m, EditText cal) {

        int calories = 0;
        String meal;

        //get the value the user entered as the calories for the meal
        String caloriesInput =cal.getText().toString();
        String mealInput =m.getText().toString();

        //allow meal to be empty for now
        if(!caloriesInput.equals(""))
        {
            //convert the string input to integer
            calories = Integer.parseInt(caloriesInput);
            meal = mealInput;

            //clear the input fields
            cal.setText("");
            m.setText("");

            //update the user that the input was successful
            Snackbar.make(view, "Successfully added calories", Snackbar.LENGTH_SHORT).show();


        }
        else
        {
            //alert the user that there is a mistake
            Snackbar.make(view, "Enter meal calories", Snackbar.LENGTH_SHORT).show();
        }
        //add to the new calories to the progress
        currentProgress = updateDailyGoal.addMealToProgress(currentProgress,calories);


    }
}
