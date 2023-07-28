package com.example.fitness_tracker.business;

import android.view.View;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidateDistance
 *
 * Validates if the time input is proper for use
 */
public class ValidateDistance {

    /**
     * the distance
     */
    private String distance;

    /**
     * the units
     */
    private String distanceUnits;

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
     * @param dis the distance
     * @param disUnits the units
     */
    public ValidateDistance(String dis, String disUnits){
        distance = dis;
        distanceUnits = disUnits;
        returnMessage = "";
        isValidInput = checkDistanceValid();
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
    public boolean checkDistanceValid(){
        boolean validInput = checkValidDistanceValue();
        if(validInput){
            validInput = checkValidDistanceUnit(distanceUnits);
        }

        // If there was an error with the distance, then let the user know
        if(!validInput)  setReturnMessage("Improper Distance Formatting");
        return validInput;
    }


    private boolean checkValidDistanceValue(){
        // Make sure distance is a valid integer and greater than 0
        // Source: https://www.w3schools.com/java/java_regex.asp
        Pattern distancePattern = Pattern.compile("^[0-9]{1,3}$");
        Matcher matchInput = distancePattern.matcher(distance); // Compare to user input

        return matchInput.find();
    }

    private boolean checkValidDistanceUnit(String distanceUnit){
        return distanceUnit.equals("km") || distanceUnit.equals("mi");
    }
}
