package com.example.fitness_tracker.business;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class CalendarLogic {
    private int day,month,year;
    private String time;

    private Calendar calendar;

    public CalendarLogic(){
        // Set the default date to todays date
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        time = Integer.toString((int)calendar.getTimeInMillis());
    }

    public CalendarLogic(int d, int mth, int yr){
        // Set the default date to todays date
        calendar = Calendar.getInstance();
        month = mth;
        day = d;
        year = yr;
    }

    public int getDay() {
        return day;
    }

    public int getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    public String getTime(){return time; }
    @NonNull
    @Override
    public String toString() {
        return day+"/"+(month+1)+"/"+year;
    }
}
