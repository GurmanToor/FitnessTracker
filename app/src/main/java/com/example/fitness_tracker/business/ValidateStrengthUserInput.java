package com.example.fitness_tracker.business;

import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

    /**
     * validateStrengthUserInput
     *
     * Ensures that the given input is correct for Strength object
     */
public class ValidateStrengthUserInput {
    boolean isValidInput;
    ValidateDateInput vDate;
    ValidateTimeLength vTime;
    Matcher matchSets;
    Matcher matchReps;
    Matcher matchBodyPart;
    Matcher matchExerciseName;
    String returnMessage;

    /**
     * validateUserInput
     *
     * Ensures that the given input is correct for use
     * @param bodyPart the body part the strength workout
     * @param exerciseName name of the exercise
     * @param date date input
     * @param timeLength timeLength input
     * @param sets Set the amount of sets
     * @param reps Set the amount of reps
     * @return if it was valid or not
     */
    public ValidateStrengthUserInput(String bodyPart,String exerciseName,String date, String timeLength,String sets, String reps){
        isValidInput = true; // This will determine of valid input or not
        vDate = new ValidateDateInput(date);
        vTime = new ValidateTimeLength(timeLength);
        returnMessage = "";
        // PAttern to make sure the sets and reps are numbers
        Pattern integerPattern = Pattern.compile("^[0-9]{1,3}$");
        matchSets = integerPattern.matcher(sets); // Compare to user input
        matchReps = integerPattern.matcher(reps); // Compare to user input

        // Pattern to make sure the excercise type and names are words less than 30 characters
        Pattern stringPattern = Pattern.compile("^[a-zA-Z ]{1,30}$");
        matchBodyPart = stringPattern.matcher(bodyPart); // Compare to user input
        matchExerciseName = stringPattern.matcher(exerciseName); // Compare to user input

        validateInput();
    }

    private void setReturnMessage(String message){
        returnMessage = message;
    }

    private void setValidInput(boolean valid){
        isValidInput = valid;
    }

    public boolean getIsValidInput(){
        return isValidInput;
    }

    public String getReturnMessage(){
        return returnMessage;
    }

    private void validateInput(){
        if(!matchBodyPart.find()){
            // If didnt pick a body part
            setReturnMessage("Choose Body Part");
            setValidInput(false);
        } else if(!matchExerciseName.find()){
            // If didnt put in a valid excercise name
            setReturnMessage("Input Valid Exercise Name");
            setValidInput(false);
        } else if(!vDate.getIsValid()){
            // If date is invalid
            setReturnMessage(vDate.getReturnMessage());
            setValidInput(false);
        } else if(!vTime.checkValidTime()){
            // If improper excercise time input
            setReturnMessage(vTime.getReturnMessage());
            setValidInput(false);
        } else if(!matchSets.find()){
            // If no sets input or not proper value
            setReturnMessage("Invalid Sets Input");
            setValidInput(false);
        } else if(!matchReps.find()){
            // If no sets input or not proper value
            setReturnMessage("Invalid Reps Input");
            setValidInput(false);
        }
    }
}
