package com.example.fitness_tracker.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.google.android.material.snackbar.Snackbar;


public class DinnerFragment extends Fragment {
    EditText mealCalories;
    EditText mealName;
    Button saveButton;
    RealDatabase database = RealDatabase.getDatabaseInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dinner_fragment, container, false);

        //get ui component
        mealCalories = v.findViewById(R.id.dinner_calories);
        mealName = v.findViewById(R.id.dinner_meal_name);
        saveButton = v.findViewById(R.id.save_dinner_calories);

        saveButton.setOnClickListener(view -> {
            //don't let the user log meals in fasting mode
            if (!database.getCurrentUser().isFasting()) {

                //pass the meal calories back to the main activity
                DailyCalorieGoal activity = (DailyCalorieGoal) getActivity();
                activity.getMealCalories(view, mealName, mealCalories);


            }
            else {
                Toast.makeText(getActivity(),"Can not log dinner in fasting mode!",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}