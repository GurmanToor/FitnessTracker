package com.example.fitness_tracker.business;

import android.view.View;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidateTimeLength
 *
 * Validates if the time input is proper for use
 */
public class ValidateTimeLength {

    /**
     * the current time
     */
    private String time;

    /**
     * if valid or not
     */
    private boolean isValidInput;

    /**
     * the return message
     */
    private String returnMessage;

    /**
     * CONSTRUCTOR
     * @param t the time
     */
    public ValidateTimeLength(String t){
        time = t;
        returnMessage = "";
        isValidInput = checkValidTime();

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
    public boolean checkValidTime(){
        boolean isValidTime = checkProperTimeFormat();
        if(isValidTime) isValidTime = checkProperIntegerValues();

        // If there was an error with the time, then let the user know
        if(!isValidTime)  setReturnMessage("Improper Time Formatting");
        return isValidTime;
    }
    private boolean checkProperTimeFormat(){
        // Should be layed out hh:mm:ss
        Pattern datePattern = Pattern.compile("^[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$");
        Matcher matchInput = datePattern.matcher(time); // Compare to user input
        return matchInput.find();
    }
    private boolean checkProperIntegerValues(){
        // Will return true if valid time format hh:mm:ss
        String[] timeSplit = time.split(":",3);

        // The integers should each be certain amount depending on hour (24), min/sec (60)
        return Integer.parseInt(timeSplit[0]) < 24
                && Integer.parseInt(timeSplit[1]) < 60
                && Integer.parseInt(timeSplit[2]) < 60;
    }
}
