package com.example.fitness_tracker.presentation;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.business.ValidateCardioUserInput;
import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;

public class CreateCardioExerciseActivity extends AppUtilities {

    RealDatabase database = RealDatabase.getDatabaseInstance();
    Workout workout; // This will be used to get the specific Workout the excersize is associated
    boolean isEditForm; // Will be passed from WorkoutActivity to say it is an edit of an exercise

    Snackbar messageToUser;
    private String selectedCardioExercise = ""; // This will be set in selectedCardioExercise
    Exercise newExercise; // This will hold the newly created Exercise object

    /**
     * onCreate
     *
     * Links the buttons and opens the activity
     * @param savedInstanceState this
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cardio_exercise);

        extractValuesPassedFromWorkoutActivity();

        // Create spinner for the selection of miles/km
        // Source: https://developer.android.com/guide/topics/ui/controls/spinner#java
        SpinnerObject spinner = new SpinnerObject(this,R.id.distanceUnitsSpinner,R.array.distance_units);

        setInitialFormValues("Cardio");

        // Set the default date to todays date
        setCalendar(R.id.editTextDate);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    /**
     * extractValuesPassedFromWorkoutActivity
     *
     * Grab the values passed in from the main Workout page. These values will
     * be the Workout object, and a boolean determining if this is an edit form
     * or a new Cardio creation form.
     */
    public void extractValuesPassedFromWorkoutActivity(){
        Bundle workoutBundle = getIntent().getExtras(); // Grab values passed from WorkoutActivity

        // Get the current workout associated
        workout = (Workout) getIntent().getSerializableExtra("workout"); // Get the current workout object
        isEditForm = workoutBundle.getBoolean("isEdit"); // Determines if its an edit or not
    }

    /**
     * setInitialFormValues
     *
     * If not an edit form, then just the time and title are set. If it is an edit
     * form then the values from the database are passed in and set to the inputs
     * of the form.
     *
     * @param workoutType
     */
    public void setInitialFormValues(String workoutType ){
        if(!isEditForm){
            setInitialTimeValue();
            setInitialFormTitle("New "+workoutType+" Exercise");
        }else{ // Otherwise if clicked edit button
            // Set the title to edit instead of 'new'
            setInitialFormTitle("Edit "+workoutType+" Exercise");

            Cardio thisExercise = (Cardio) getIntent().getSerializableExtra("exercise");
            setEditFormValues(thisExercise);
        }
    }

    private void setInitialTimeValue(){
        // Set default Time (00:00:00) if not part of an edit form
        EditText timeInput = findViewById(R.id.editTextTime);
        timeInput.setText("00:00:00"); // Set the default text for user input
    }

    private void setInitialFormTitle(String workoutType){
        // Set title to 'new' instead of 'edit'
        TextView title = findViewById(R.id.form_title);
        title.setText(workoutType);
    }
    /**
     * Back button
     *
     * @param v this
     */
    public void goBackAPage(View v){
        // This will go back to the main Workout menu from when in a create exercise
        // Source: https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent goBack = new Intent(this, WorkoutActivity.class);
        goBack.putExtra("workout", workout); // Pass the workout back to Workout page
        startActivity(goBack);
    }

    /**
     * selectedCardioExercise
     *
     * When user presses one of the 'Bicycle, Walk, Run' buttons, that one will change to a
     * brighter purple while the others will return to their normal colors
     * @param view this
     */
    public void selectedCardioExercise(View view){
        int[] buttonIDs = {R.id.buttonBicycle,R.id.buttonRun, R.id.buttonWalk};
        String[] buttonStrings = {"Bicycle", "Run","Walk"}; // Used to save for the submit
        int selectedID;

        // Get the id of the button pressed and change its color
        for(int i = 0; i< buttonIDs.length;i++){
            Button btn = findViewById(buttonIDs[i]); // Get the current button to check

            if(btn.getId() == view.getId()) {
                // If selected button then change the background color
                // Source: https://stackoverflow.com/questions/40426928/how-to-programmatically-set-backgroundtint-of-floatingactionbutton-with-colorsta
                btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorPrimaryDark));
                selectedCardioExercise = buttonStrings[i];
            } else{
                // Else change it back to the purle
                btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.button_press_color));

            };
        }
    }

    /**
     * submitExercise
     *
     * When submit button pressed, this method is called, it will take all the form data, extract
     * it and create a new Exercise object
     * @param view this
     * @return if it was submitted properly or not
     * @throws ParseException parsing error
     */
    public boolean submitExercise(View view) throws ParseException {
        String date = getDateFromInput(); // Get the date submitted
        String timeLength = getTimeFromInput(); // Get the time input
        String distance = getDistanceValueFromInput();
        String distanceUnits = getDistanceUnitsFromInput();

        ValidateCardioUserInput vUserInput = new ValidateCardioUserInput(date,timeLength,distance,distanceUnits,selectedCardioExercise);
        // Need to validate this input is proper formatting
        boolean isValidInput = vUserInput.getIsValidInput();

        if(isValidInput){
            // If everything is valid then submit to the database
            if(!isEditForm){
                createNewExercise(date,timeLength,distance,distanceUnits);
            }else{
                updateExercise(date,timeLength,distance,distanceUnits);
            }
            goBackAPage(view);
        }else{
            sendInputMessage(view,vUserInput.getReturnMessage());
        };

        return isValidInput;
    }

    /**
     * createNewExercise
     *
     * @param date
     * @param timeLength
     * @param distance
     * @param distanceUnits
     *
     * Create a new Cardio exercise and ExerciseWorkout, add them to the database.
     */
    private void createNewExercise(String date, String timeLength, String distance, String distanceUnits){
        // If valid input and a valid Workout was passed then create a new Exercise object
        int exID = database.exerciseSize()+1;

        // Create the Database objects
        newExercise = new Cardio(exID,selectedCardioExercise,"all","Cardio",Integer.parseInt(distance),distanceUnits,timeLength);
        ExerciseWorkout exeWrkt = new ExerciseWorkout(newExercise,workout);

        database.insert(newExercise);
        database.insert(exeWrkt);
    }

    /**
     * updateExercise
     *
     * @param date
     * @param timeLength
     * @param distance
     * @param distanceUnits
     *
     * Used when the form is in 'edit' mode and will update the current object being
     * edited and update the database value associated.
     */
    private void updateExercise(String date, String timeLength, String distance, String distanceUnits){
        // If it is an edit form, then update the current exercise, exerciseWorkout
        Cardio oldExercise = (Cardio) getIntent().getSerializableExtra("exercise");
        int exID = oldExercise.getExerciseID(); // Get old exercise id to update

        // Create the Database objects
        newExercise = new Cardio(exID,selectedCardioExercise,"all","Cardio",Integer.parseInt(distance),distanceUnits,timeLength);
        ExerciseWorkout exeWrkt = new ExerciseWorkout(newExercise,workout);

        database.update(newExercise);
        //database.update(exeWrkt);
    }

    /**
     * sendInputMessage
     *
     * sends the input message to the snackbar
     * @param v this
     * @param message the input message
     */
    private void sendInputMessage(View v, String message){
        // This will show a message at the bottom of the screen for what may have went wrong
        // Source: https://developer.android.com/training/snackbar/showing
        messageToUser = Snackbar.make(v,message, Snackbar.LENGTH_INDEFINITE);
        messageToUser.setDuration(6000); // Bar will show for 6s
        messageToUser.show();
    }


    /********************************************************************************************
     *                               Input Methods
     * ********************************************************************************************/
    private String getDateFromInput(){
        // Get the date input from user
        String date;

        // Get the date user input
        EditText dateInput = findViewById(R.id.editTextDate);
        date = dateInput.getText().toString(); // Get the text from the user input

        return date;
    }

    private String getTimeFromInput(){
        // Get the time length user input
        EditText timeInput = findViewById(R.id.editTextTime);
        String time = timeInput.getText().toString(); // Get the text from the user input

        return time;
    }

    private String getDistanceValueFromInput(){
        // Get the distance value from user input
        EditText distanceInput = findViewById(R.id.editTextDistance);
        String distance = distanceInput.getText().toString(); // Get the text from the user input

        return distance;
    }

    private String getDistanceUnitsFromInput(){
        // Get the distance units (km or mi) from user input
        // Source: https://stackoverflow.com/questions/1947933/how-to-get-spinner-value
        Spinner distanceUnitSpinner = findViewById(R.id.distanceUnitsSpinner);
        return distanceUnitSpinner.getSelectedItem().toString();
    }

    /********************************************************************************************
     *                               Edit form methods
     * ********************************************************************************************/
    public void setEditFormValues(Cardio thisExe){
        setTimeLengthForEditForm(thisExe.getLength());
        setDistanceValueForEditForm(Integer.toString(thisExe.getDistance()));
        setDistanceUnitsFromInput(thisExe.getDistanceUnits());
        setCardioExerciseType(thisExe.getName());
    }
//    private String setDate(){
        // Need to add date
//        // Get the date input from user
//        String date;
//
//        // Get the date user input
//        EditText dateInput = findViewById(R.id.editTextDate);
//        date = dateInput.getText().toString(); // Get the text from the user in put
//
//        return date;
//    }

    private void setTimeLengthForEditForm(String time){
        // Get the time length user input
        EditText timeInput = findViewById(R.id.editTextTime);
        timeInput.setText(time); // Set the vallue of time for edit form
    }

    private void setDistanceValueForEditForm(String distance){
        // Get the distance value from user input
        EditText distanceInput = findViewById(R.id.editTextDistance);
        distanceInput.setText(distance); // Set the value to edit
    }

    private void setDistanceUnitsFromInput(String unit){
        // Set distance units (km or mi)
        Spinner distanceUnitSpinner = findViewById(R.id.distanceUnitsSpinner);

        if(unit.equals("km")) distanceUnitSpinner.setSelection(1);
        else distanceUnitSpinner.setSelection(0);
    }

    private void setCardioExerciseType(String type){
        selectedCardioExerciseEdit(type);
    }
    private void selectedCardioExerciseEdit(String type){
        int[] buttonIDs = {R.id.buttonBicycle,R.id.buttonRun, R.id.buttonWalk};
        String[] buttonStrings = {"Bicycle", "Run","Walk"}; // Used to save for the submit


        // Get the id of the button pressed and change its color
        for(int i = 0; i< buttonIDs.length;i++){
            Button btn = findViewById(buttonIDs[i]); // Get the current button to check

            if(type.equals(buttonStrings[i])) {
                // If selected button then change the background color
                // Source: https://stackoverflow.com/questions/40426928/how-to-programmatically-set-backgroundtint-of-floatingactionbutton-with-colorsta
                btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorPrimaryDark));
                selectedCardioExercise = buttonStrings[i];
            } else{
                // Else change it back to the purle
                btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.button_press_color));

            };
        }
    }
}

