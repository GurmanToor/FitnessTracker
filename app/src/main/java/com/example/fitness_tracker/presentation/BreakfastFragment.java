package com.example.fitness_tracker.presentation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;


public class BreakfastFragment extends Fragment {

    EditText mealCalories;
    EditText mealName;
    Button saveButton;
    RealDatabase database = RealDatabase.getDatabaseInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.breakfast_fragment, container, false);
        View v = inflater.inflate(R.layout.breakfast_fragment, container, false);


        //get ui component
        mealCalories = v.findViewById(R.id.breakfast_calories);
        mealName = v.findViewById(R.id.breakfast_meal_name);
        saveButton = v.findViewById(R.id.save_breakfast_calories);

        saveButton.setOnClickListener(view -> {
            if (!database.getCurrentUser().isFasting()) {


                //pass the meal calories back to the main activity
                DailyCalorieGoal activity = (DailyCalorieGoal) getActivity();
                activity.getMealCalories(view, mealName, mealCalories);
            }
            else {
                Toast.makeText(getActivity(),"Can not log breakfast in fasting mode!",Toast.LENGTH_SHORT).show();
            }

        });



        return v;
    }


}