// Source : https://developer.android.com/guide/topics/ui/controls/pickers
package com.example.fitness_tracker.presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.fitness_tracker.R;
import com.example.fitness_tracker.business.CalendarLogic;

public class DateSelectorFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    /**
     * onCreate
     *
     * Links the buttons and opens the activity
     * @param savedInstanceState this
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the current date to use as the calendar date
        final CalendarLogic c = new CalendarLogic();

        return new DatePickerDialog(getActivity(), this, c.getYear(), c.getMonth(), c.getDay());
    }

    /**
     * onDateSet
     *
     * sets the date input given the particular inputs
     * @param view this
     * @param year input year
     * @param month input month
     * @param day input day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Return a string form of the date selected that was selected when date set
        Activity activity = getActivity();
        TextView dateInput = (TextView) activity.findViewById(R.id.editTextDate);

        // Have to add 1 to the month since its in programming value
        CalendarLogic c = new CalendarLogic(day,month,year);

        dateInput.setText(c.toString());
    }
}
