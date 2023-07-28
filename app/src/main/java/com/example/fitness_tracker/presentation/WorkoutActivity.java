package com.example.fitness_tracker.presentation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.business.AccessExerciseWorkout;
import com.example.fitness_tracker.business.AccessWorkout;
import com.example.fitness_tracker.business.ExerciseAdapter;
import com.example.fitness_tracker.business.WorkoutDatabaseHandler;
import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.Workout;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * WorkoutActivity
 *
 * The user interface for the workout section, separated by other activities
 * from the workout sections
 */

public class WorkoutActivity extends AppCompatActivity implements Serializable {
    String username; // Hold user name of current person using app
    boolean isEdit; // Used to pass determine if form is for editing or creating
    /**
     * The profile pic
     */
    CircleImageView profilePic;

    // Fake DB implementation
    RealDatabase database = RealDatabase.getDatabaseInstance();
    WorkoutDatabaseHandler dbHandler = new WorkoutDatabaseHandler(database);

    // Variables to deal with adding list to the Activity layout
    ExerciseAdapter exerciseAdapter;
    ListView exerciseList; // This will reference the list being made to show current exercises for a workout

    Workout workout; // Object to create new workout

    /**
     * On creation, open the activity
     *
     * @param savedInstanceState the current instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        username = dbHandler.getCurrentUser();

        setupWorkoutArea();

        listenForButtonPressedFromList(); // Either edit or delete

        profilePic = findViewById(R.id.user_profile_workout);

        setProfilePic(database.getCurrentUser());

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    /**
     * listenForButtonPressedFromList
     *
     * This will listen for the user to press one of either the 'edit' button or
     * 'delete' buttons on an exercise in the list of objects.
     */
    public void listenForButtonPressedFromList(){
        List <ExerciseWorkout> exeWrkt = dbHandler.getExerciseWorkoutList();
        if(exeWrkt != null && !exeWrkt.isEmpty()) {
            exerciseList = findViewById(R.id.exercise_list);
            showCurrentExercises();

            exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int listItem, long l) {
                    long clickedID = view.getId();

                    if(clickedID == R.id.delete_button){
                        handleDeleteButtonPressed(view);
                        refreshPage();
                    }else if(clickedID == R.id.edit_exercise_button){
                        handleEditButtonPressed(view);
                    }
                }
            });
        }
    }

    /**
     * handleDeleteButtonPressed
     *
     * @param view
     *
     * When user presses delete button on an exercise, this will take the tag (ID)
     * attached and use it to delete the associated exercise.
     */
    public void handleDeleteButtonPressed(View view){
        // If delete button was clicked on list item, then remove it
        ImageView thisView = (ImageView)view.findViewById(R.id.delete_button); // Get the button

        int exerciseID = (int)thisView.getTag(); // Extract exercise ID from the garbage icon
        dbHandler.deleteExercise(exerciseID); // Delete the id associated with this list item
        refreshPage();
    }

    /**
     * handleEditButtonPressed
     *
     * @param view
     *
     * When user presses blue pencil 'edit' button on the exercise list object, this will
     * send the user to the edit form.
     */
    public void handleEditButtonPressed(View view){
        // If edit button was clicked on list item, then remove it
        ImageView thisView = (ImageView)view.findViewById(R.id.edit_exercise_button); // Get the button

        int exerciseID = (int)thisView.getTag(); // Extract exercise ID from the garbage icon
        System.out.println("Exercise ID: "+exerciseID);
        editExercise(exerciseID);
    }

    private void refreshPage(){
        // Refresh the page
        Intent goBack = new Intent(this, WorkoutActivity.class);
        goBack.putExtra("workout", workout); // Pass the workout back to Workout page
        startActivity(goBack);
    }

    /**
     * setupWorkoutArea
     *
     * If this is a new workout then a new Workout object created. Otherwise the
     * one that has already been created will be used.
     */
    private void setupWorkoutArea(){
        if(getIntent().getSerializableExtra("workout") == null){
            // Instantiate the id based off the size of database if a workout hasnt been started
            workout = dbHandler.newWorkoutObject();
        }else{
            // Otherwise load in the current workout which is passed from one of the cardio or strength
            // creation pages
            workout = (Workout) getIntent().getSerializableExtra("workout");
            dbHandler.setWorkout(workout);
        }
    }

    /**
     * Method to get the ExcerciseWorkout objects from the current workout and show them
     * to the screen.
     */
    private void showCurrentExercises(){
        // Obtain the list of ExerciseWorkout objects by workout ID (current workout)
        List<ExerciseWorkout> list = dbHandler.getExerciseWorkoutList();

        if(list !=null){
            exerciseAdapter = new ExerciseAdapter(WorkoutActivity.this,list); // Instantiate adapter
            exerciseList.setAdapter(exerciseAdapter); // Attach to "layout/current_workout_exercise_list.xml"
        }
    }

    /**
     * newCardioExercise
     *
     * Method attached to the 'new cardio button' in the Workouts section
     *
     * @param v this
     */
    public void newCardioExercise(View v){
        // Switch to the CreateWorkout Activity
        // Src: https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent newExercisePage = new Intent(this, CreateCardioExerciseActivity.class);

        // Will access through a serializable in the child class
        newExercisePage.putExtra("workout", workout);
        newExercisePage.putExtra("isEdit",isEdit); // False since new cardio creation
        startActivity(newExercisePage);
    }

    /**
     * editExercise
     *
     * Method attached to the 'edit exercise' in the Workouts section
     *
     * @param exerciseID this
     */
    public void editExercise(int exerciseID){
        // Switch to the CreateWorkout Activity
        // Src: https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Exercise exe = dbHandler.getExercise(exerciseID);

        isEdit = true; // Used to tell the form that it is an edit form
        String name = exe.getType();

        if(name.equals("Cardio")){
            Cardio cardioExe = (Cardio)exe;
            goToCardioEditForm(cardioExe);
        }else if(name.equals("Strength")){
            Strength strengthExe = (Strength)exe;
            goToStrengthEditForm(strengthExe);
        }
    }

    public void goToCardioEditForm(Cardio exercise){
        // Switch to the CreateWorkout Activity
        // Src: https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent newExercisePage = new Intent(this, CreateCardioExerciseActivity.class);

        // Will access through a serializable in the child class
        newExercisePage.putExtra("exercise", exercise);
        launchForm(newExercisePage);
    }

    public void goToStrengthEditForm(Strength exercise){
        // Switch to the CreateWorkout Activity
        // Src: https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent newExercisePage = new Intent(this, CreateStrengthExerciseActivity.class);

        // Will access through a serializable in the child class
        newExercisePage.putExtra("exercise", exercise);
        launchForm(newExercisePage);
    }

    private void launchForm(Intent newExercisePage){
        newExercisePage.putExtra("workout", workout); // Need to pass the current workout
        newExercisePage.putExtra("isEdit",isEdit); // False since new exercise creation
        startActivity(newExercisePage);
    }

    /**
     * Method attached to the 'new strength button' in the Workouts section
     *
     * @param v this
     */
    public void newStrengthExercise(View v){
        Intent newStrengthExercisePage = new Intent(this, CreateStrengthExerciseActivity.class);

        // Will access through a serializable in the child class
        newStrengthExercisePage.putExtra("workout", workout);
        startActivity(newStrengthExercisePage); // Move to the create strength page
    }

    /**
     * Back button method
     *
     * @param v this
     */
    public void goBackAPage(View v){
        // This will go back to the main Workout menu from when in a create workout
        // Source: https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent goBack = new Intent(this, WorkoutHomeActivity.class);
        deleteWorkoutIfNoExercisesAdded();
        startActivity(goBack);
    }

    /**
     * deleteWorkoutIfNoExercisesAdded
     *
     * Found in the goBackAPage method, it will delete the Workout object if there
     * were no Exercises added.
     */
    private void deleteWorkoutIfNoExercisesAdded(){
        if(dbHandler.getExerciseWorkoutList() == null || dbHandler.getExerciseWorkoutList().isEmpty() || exerciseList == null){
            // If no exercises were added, then delete the workout
            dbHandler.deleteWorkout();
            System.out.println("Deleted workout");
        }
    }

    /**
     * finishWorkout
     *
     * @param view
     *
     * Method is called when user clicks 'Finish'
     */
    public void finishWorkout(View view){
        // If user clicks 'Finish' button on the workout page
        goBackAPage(view);
    }

    /**
     * setProfilePic
     *
     * sets the profile picture based
     * off the user preferences
     * @param user the current user
     */
    public void setProfilePic(User user) {
        int id = user.getProfilePicId();

        switch (id) {
            case 0:
                profilePic.setImageResource(R.drawable.gympic);
                break;
            case 2:
                profilePic.setImageResource(R.drawable.gympic2);
                break;
            case 3:
                profilePic.setImageResource(R.drawable.gympic3);
                break;
            case 5:
                profilePic.setImageResource(R.drawable.gympic5);
                break;
            case 6:
                profilePic.setImageResource(R.drawable.gympic6);
                break;
            default:
                profilePic.setImageResource(R.drawable.gympic7);
                break;
        }
    }
}
