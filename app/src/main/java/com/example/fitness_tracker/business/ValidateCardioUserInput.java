package com.example.fitness_tracker.business;

public class ValidateCardioUserInput {
    boolean isValidInput;
    ValidateDateInput vDate;
    ValidateTimeLength vTime;
    ValidateDistance vDistance;
    String selectedCardioExercise;
    String returnMessage;

    /**
     * validateCardioUserInput
     *
     * Ensures that the given input is correct for use
     * @param date date input
     * @param timeLength timeLength input
     * @param distance distance input
     * @param distanceUnits distanceUnits input
     * @return if it was valid or not
     */
    public ValidateCardioUserInput(String date, String timeLength, String distance, String distanceUnits, String selCardioExercise){
        isValidInput = true; // This will determine of valid input or not
        vDate = new ValidateDateInput(date);
        vTime = new ValidateTimeLength(timeLength);
        vDistance = new ValidateDistance(distance,distanceUnits);
        selectedCardioExercise =selCardioExercise;
        returnMessage = "";
        validateInput();
    }

    /**
     * setReturnMessage
     *
     * @param message to set depending on if user input error occured
     */
    private void setReturnMessage(String message){
        // Return a message to be used to print to the user
        returnMessage = message;
    }

    private void setValidInput(boolean valid){
        isValidInput = valid;
    }

    /**
     *
     * @return true or false depending on if user input was valid
     */
    public boolean getIsValidInput(){
        return isValidInput;
    }

    /**
     *
     * @return message depending on if any certain user input errors occured
     */
    public String getReturnMessage(){
        return returnMessage;
    }

    private void validateInput(){
        // Will return true if all the inputs pass the validations tests
        if(!vDate.getIsValid()){
            // If date is invalid
            setReturnMessage(vDate.getReturnMessage());
            setValidInput(false);
        }else if(selectedCardioExercise.equals("")){
            // If no Exercise type selected
            setReturnMessage("Select exercise type");
            setValidInput(false);
        }else if(!vTime.checkValidTime()){
            // If improper exercise time input
            setReturnMessage(vTime.getReturnMessage());
            setValidInput(false);
        }else if(!vDistance.checkDistanceValid()){
            // If distance formatting isnt correct
            setReturnMessage(vDistance.getReturnMessage());
            setValidInput(false);
        };
    }
}
