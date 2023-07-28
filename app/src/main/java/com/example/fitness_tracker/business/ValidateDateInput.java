package com.example.fitness_tracker.business;

import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidateDateInput
 *
 * Validates if the time input is proper for use
 */
public class ValidateDateInput {

    /**
     * the date
     */
    private String date;

    /**
     * the return message
     */
    private String returnMessage;

    /**
     * if valid or not
     */
    private boolean isValidInput;

    /**
     * CONSTRUCTOR
     * @param d the date input
     */
    public ValidateDateInput(String d){
        date = d;
        returnMessage = "";
        isValidInput = validateDate();

    }

    /********************************************************************************************
     *                               GETTERS AND SETTERS!
     * ********************************************************************************************/
    private void setReturnMessage(String message){
        returnMessage = message;
    }

    public String getReturnMessage(){
        return this.returnMessage;
    }

    private void setIsValid(boolean isValid){
        isValidInput = isValid;
    }

    public boolean getIsValid(){
        return isValidInput;
    }

    /********************************************************************************************
     *                               CHECKERS!
     * ********************************************************************************************/
    public boolean validateDate(){
        boolean isValidDate = checkDateProperFormat();
        if(!isValidDate) setReturnMessage("Improper Date Formatting");
        if(isValidDate){
            isValidDate = checkDateProperValues();
            if(!isValidDate) setReturnMessage("Improper Date Values");
        }

        return isValidDate;
    }

    private boolean checkDateProperFormat(){
        boolean isValidFormat = true;
        // Source: https://www.w3schools.com/java/java_regex.asp
        Pattern datePattern = Pattern.compile("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}$"); // dd/mm/yyyy
        Matcher matchInput = datePattern.matcher(date); // Compare to user input
        isValidFormat = matchInput.find();

        return isValidFormat;
    }

    private boolean checkDateProperValues(){
        boolean isProperValues = true;
        String[] dateSplit = date.split("/",3); // Split string to check proper format

        // If contains valid integers
        int day = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int year = Integer.parseInt(dateSplit[2]);
        isProperValues = checkProperMonth(month);
        if(this.isValidInput){
            isProperValues = checkProperDay(day, month);
        }
        if(this.isValidInput) checkProperYear(year);

        return isProperValues;
    }

    private boolean checkProperDay(int day, int month){
        int[] monthDays = {31,28,31,30,31,30,31,31,30,31,30,31}; // Check to make sure less than the amount of actual days in that month

        boolean isProperDay = (day <= monthDays[month-1]);
        // Need to make sure for DD that its less than equal to 31 (check months to determine which)
        if(!isProperDay) setReturnMessage("Day isnt valid");

        return isProperDay;
    }


    private boolean checkProperMonth(int month){
        boolean isProperMonth = (month <=12);
        // Need to make sure for DD that its less than equal to 31 (check months to determine which)
        if(!isProperMonth) setReturnMessage("Month isnt valid");
        setIsValid(isProperMonth);

        return isProperMonth;
    }

    private boolean checkProperYear(int year){
        // Need to make sure for DD that its less than equal to 31 (check months to determine which)
        Calendar calendar = Calendar.getInstance();
        return (year <= calendar.get(Calendar.YEAR));
    }

}
