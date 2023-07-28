package com.example.fitness_tracker.presentation;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerObject {
    public Activity activity;
    public int layoutID;
    public int itemList;

    /**
     * SpinnerObject
     *
     * @param thisActivity
     * @param thisLayoutID
     * @param list
     *
     * Used to create a dropdown list
     */
    public SpinnerObject(Activity thisActivity, int thisLayoutID,int list){
        activity = thisActivity;
        layoutID = thisLayoutID;
        itemList = list;

        onCreate();
    }

    public void onCreate(){
        // This method is called when object created
        Spinner spinner = this.activity.findViewById(layoutID); // Used to create scroll list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                itemList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
