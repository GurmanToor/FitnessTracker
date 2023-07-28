package com.example.fitness_tracker.presentation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.business.ExerciseAdapter;
import com.example.fitness_tracker.business.WorkoutAdapter;
import com.example.fitness_tracker.business.WorkoutDatabaseHandler;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;
import com.example.fitness_tracker.domain_specific_objects.Workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {
    String username; // Hold user name of current person using app
    // Real DB implementation
    RealDatabase database = RealDatabase.getDatabaseInstance();
    WorkoutDatabaseHandler dbHandler = new WorkoutDatabaseHandler(database);

    // Variables to deal with adding list to the Activity layout
    WorkoutAdapter workoutAdapter;
    ListView workoutList; // This will reference the list being made to show current exercises for a workout
    List<Workout> wrktList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        username = dbHandler.getCurrentUser();

//        List<UserWorkout> list = database.getUserWorkout(theUser.getUsername());
//        System.out.println("Size: "+list.size());

        listenForButtonPressedFromList(); // Either edit or delete

    }
    /**
     * listenForButtonPressedFromList
     *
     * This will listen for the user to press one of either the 'edit' button or
     * 'delete' buttons on an exercise in the list of objects.
     */
    public void listenForButtonPressedFromList(){
        wrktList = dbHandler.getWorkoutList();
        if(wrktList != null && !wrktList.isEmpty()) {
            workoutList = findViewById(R.id.workout_list);
            showCurrentWorkouts();
            System.out.println("Current Workouts");

            workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int listItem, long l) {
                    long clickedID = view.getId();

//                    if(clickedID == R.id.delete_wrkt){
//                        handleDeleteButtonPressed(view);
//                        refreshPage();
//                    }
//                    else if(clickedID == R.id.edit_exercise_button){
//                        handleEditButtonPressed(view);
//                    }
                }
            });
        }else{
            System.out.println("No workouts");
        }
    }

    /**
     * handleDeleteButtonPressed
     *
     * @param view
     *
     * When user presses delete button on an exercise, this will take the tag (ID)
     * attached and use it to delete the associated workout. The tag is set in WorkoutAdapter.
     */
    public void handleDeleteButtonPressed(View view){
        // If delete button was clicked on list item, then remove it
        ImageView thisView = (ImageView)view.findViewById(R.id.delete_wrkt); // Get the button

        int workoutID = (int)thisView.getTag(); // Extract exercise ID from the garbage icon
        dbHandler.deleteListWorkout(workoutID); // Delete the id associated with this list item
        refreshPage();
    }

    private void refreshPage(){
        // Refresh the page
        Intent goBack = new Intent(this, WorkoutHistoryActivity.class);
        startActivity(goBack);
    }
    /**
     * Method to get the ExcerciseWorkout objects from the current workout and show them
     * to the screen.
     */
    private void showCurrentWorkouts(){
        // Obtain the list of ExerciseWorkout objects by workout ID (current workout)
        List<Workout> list = wrktList;

        if(list !=null){
            workoutAdapter = new WorkoutAdapter(WorkoutHistoryActivity.this,list); // Instantiate adapter
            workoutList.setAdapter(workoutAdapter); // Attach to "layout/current_workout_exercise_list.xml"
        }
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
        startActivity(goBack);
    }
}