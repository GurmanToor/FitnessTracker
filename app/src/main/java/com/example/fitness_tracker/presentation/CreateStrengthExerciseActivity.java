package com.example.fitness_tracker.presentation;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.business.ValidateStrengthUserInput;
import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.business.ValidateDateInput;
import com.example.fitness_tracker.business.ValidateTimeLength;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateStrengthExerciseActivity extends AppUtilities {
    Snackbar messageToUser;

    RealDatabase database = RealDatabase.getDatabaseInstance();
    Workout workout; // This will be used to get the specific Workout the excersize is associated
    boolean isEditForm; // Will be passed from WorkoutActivity to say it is an edit of an exercise

    Exercise newExercise;

    /**
     * onCreate
     *
     * Links the buttons and opens the activity
     * @param savedInstanceState this
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_strength_exercise);

        // Get the current workout associated
        extractValuesPassedFromWorkoutActivity();

        // Set the spinner for the body type
        SpinnerObject spinner = new SpinnerObject(this,R.id.body_part_spinner,R.array.body_parts);

        setInitialFormValues("Strength");

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
            // Set default Time (00:00:00) if not part of an edit form
            EditText timeInput = findViewById(R.id.editTextTime);
            timeInput.setText("00:00:00"); // Set the default text for user input

            // Set title to 'new' instead of 'edit'
            TextView title = findViewById(R.id.form_title);
            title.setText("New "+workoutType+" Exercise");
        }else{ // Otherwise if clicked edit button
            // Set the title to edit instead of 'new'
            TextView title = findViewById(R.id.form_title);
            title.setText("Edit "+workoutType+" Exercise");

            Strength thisExercise = (Strength) getIntent().getSerializableExtra("exercise");
            setEditFormValues(thisExercise);
        }
    }


    /**
     * Back button
     * @param v this
     */
    public void goBackAPage(View v){
        Intent goBack = new Intent(this, WorkoutActivity.class);
        goBack.putExtra("workout", workout); // Pass the workout back to Workout page

        startActivity(goBack);
    }

    /**
     * Shows the calendar on click
     * @param v this
     */
    public void showCalendar(View v){
        // This is called when the calendar button pressed to show the calendar selector
        DialogFragment newFragment = new DateSelectorFragment();
        newFragment.show(getSupportFragmentManager(), "dateSelector");
    }

    /**
     * submitStrengthExercise
     *
     * When submit button pressed, this method is called, it will take all the form data, extract
     * it and create a new Exercise object
     * @param view this
     * @return if it was submitted properly or not
     * @throws ParseException parsing error
     */
    public boolean submitStrengthExercise(View view) throws ParseException {
        String bodyPart = getBodyPartFromInput();
        String exerciseName = getExerciseNameFromInput();
        String date = getDateFromInput(); // Get the date submitted
        String timeLength = getTimeFromInput(); // Get the time input
        String sets = getSetsValueFromInput();
        String reps = getRepsValueFromInput();

        ValidateStrengthUserInput vUserInput = new ValidateStrengthUserInput(bodyPart,exerciseName,date,timeLength, sets, reps);
        // Need to validate this input is proper formatting
        boolean isValidInput = vUserInput.getIsValidInput();

        if(isValidInput){
            if(!isEditForm){
                createNewExercise(bodyPart,exerciseName,date,timeLength,sets,reps);
            }else{
                updateExercise(bodyPart,exerciseName,date,timeLength,sets,reps);
            }

            goBackAPage(view);
        }else{
            // Send error message if isnt valid input
            sendInputMessage(view,vUserInput.getReturnMessage());
        };
        return isValidInput;
    }

    /**
     * createNewExercise
     *
     * @param bodyPart
     * @param exerciseName
     * @param date
     * @param timeLength
     * @param sets
     * @param reps
     *
     * Create new Strength Exercise object and add it to the Database.
     */
    public void createNewExercise(String bodyPart,String exerciseName,String date, String timeLength,String sets, String reps){
        // If valid input then create a new Exercise object, if not part of edit form
        int exID = database.exerciseSize()+1;

        newExercise = new Strength(exID,exerciseName,bodyPart,"Strength",Integer.parseInt(reps),Integer.parseInt(sets),timeLength);
        ExerciseWorkout exeWrkt = new ExerciseWorkout(newExercise,workout);
        // If is valid input, then save to an object
        database.insert(newExercise);
        database.insert(exeWrkt);
    }

    /**
     * updateExercise
     *
     * @param bodyPart
     * @param exerciseName
     * @param date
     * @param timeLength
     * @param sets
     * @param reps
     *
     * Used when the form is in 'edit' mode and will update the current object being
     * edited and update the database value associated.
     */
    public void updateExercise(String bodyPart,String exerciseName,String date, String timeLength,String sets, String reps){
        // If part of edit form
        // If it is an edit form, then update the current exercise, exerciseWorkout
        Strength oldExercise = (Strength) getIntent().getSerializableExtra("exercise");
        int exID = oldExercise.getExerciseID(); // Get old exercise id to update

        // Create the Database objects
        newExercise = new Strength(exID,exerciseName,bodyPart,"Strength",Integer.parseInt(reps),Integer.parseInt(sets),timeLength);
        ExerciseWorkout exeWrkt = new ExerciseWorkout(newExercise,workout);

        // Update the current strength exercise
        database.update(newExercise);
        //database.update(exeWrkt);
    }

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
    private String getBodyPartFromInput(){
        // Get the amount of reps a user did from a excercise
        Spinner bodyPartSpinner = findViewById(R.id.body_part_spinner);
        return bodyPartSpinner.getSelectedItem().toString();
    }
    private String getExerciseNameFromInput(){
        // Get the name of the excercise from user input
        EditText excerciseNameInput = findViewById(R.id.edit_exercise_name);
        return excerciseNameInput.getText().toString(); // Get the text from the user input
    }
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
    private String getSetsValueFromInput(){
        // Get the amount of sets the user did for a exercise
        EditText distanceInput = findViewById(R.id.number_of_sets);
        String distance = distanceInput.getText().toString(); // Get the text from the user input

        return distance;
    }
    private String getRepsValueFromInput(){
        // Get the amount of reps a user did from a exercise
        EditText distanceUnitSpinner = findViewById(R.id.number_of_reps);
        return distanceUnitSpinner.getText().toString();
    }

    /********************************************************************************************
     *                               Edit form methods
     * ********************************************************************************************/
    public void setEditFormValues(Strength thisExe){
        setTimeLengthForEditForm(thisExe.getLength());
        setBodyPartFromInput(thisExe.getBodyPart());
        setExerciseName(thisExe.getName());
        setReps(thisExe.getReps());
        setSets(thisExe.getSets());
    }

    private void setTimeLengthForEditForm(String time){
        // Get the time length user input
        EditText timeInput = findViewById(R.id.editTextTime);
        timeInput.setText(time); // Set the vallue of time for edit form
    }

    private void setBodyPartFromInput(String bodyPart){
        // Set the body part type for the spinner list selector
        Spinner bodyPartsSpinner = findViewById(R.id.body_part_spinner);
        String[] bodyPartsList = getResources().getStringArray(R.array.body_parts);

        for(int i = 0;i<bodyPartsList.length;i++){
            if(bodyPart.equals(bodyPartsList[i])) bodyPartsSpinner.setSelection(i);
        }
    }

    private void setExerciseName(String name){
        EditText nameInput = findViewById(R.id.edit_exercise_name);
        nameInput.setText(name);
    }

    private void setReps(int reps){
        EditText repsInput = findViewById(R.id.number_of_reps);
        repsInput.setText(Integer.toString(reps));
    }

    private void setSets(int sets){
        EditText setsInput = findViewById(R.id.number_of_sets);
        setsInput.setText(Integer.toString(sets));

    }
}