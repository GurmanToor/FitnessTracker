package com.example.fitness_tracker.presentation;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.fitness_tracker.R;
import com.example.fitness_tracker.business.CalendarLogic;

public class AppUtilities extends AppCompatActivity {
    /**
     * setCalendar
     *
     * Set up the current date as default for the calendar
     */
    public void setCalendar(int ID){
        // Set the default date to todays date
        CalendarLogic calendar = new CalendarLogic(); // Set the default date to todays date

        TextView dateInput = findViewById(ID);
        dateInput.setText(calendar.toString()); // Set default date int "dd/mm/yyyy"
    }

    /**
     * showCalendar
     *
     * Sets the calendar in view
     * @param v this
     */
    public void showCalendar(View v){
        // This is called when the calendar button pressed to show the calendar selector
        DialogFragment newFragment = new DateSelectorFragment();
        newFragment.show(getSupportFragmentManager(), "dateSelector");
    }

}
