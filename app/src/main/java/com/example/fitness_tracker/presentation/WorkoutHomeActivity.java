package com.example.fitness_tracker.presentation;

import static com.example.fitness_tracker.R.layout.activity_workout_home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkoutHomeActivity extends AppCompatActivity {
    /**
     * nameLabel
     *
     * the current users name
     */
    TextView nameLabel;

    /**
     * The profile pic
     */
    CircleImageView profilePic;

    /**
     * The database instance
     */
    RealDatabase fakeDatabase = RealDatabase.getDatabaseInstance();

    /**
     * weightLabel
     *
     * the current users weight
     * @tochange to current users GOAL weight
     */
    TextView weightLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_home);

        nameLabel = findViewById(R.id.username_workout_home);
        weightLabel =  findViewById(R.id.user_weight_workout_home);
        profilePic = findViewById(R.id.profile_pic);

        dbImpl();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    /**
     * toWorkout
     *
     * Switches to workout activity
     * @param view this
     */
    public void toWorkout(View view) {
        startActivity(new Intent(WorkoutHomeActivity.this, WorkoutActivity.class));
    }

    /**
     * Back button method
     *
     * @param v this
     */
    public void goBackAPage(View v){
        // This will go back to the main Workout menu from when in a create workout
        // Source: https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent goBack = new Intent(this, HomeActivity.class);
        startActivity(goBack);

    }

    /**
     * Method attached to the 'new exercise button' in the Workouts section
     *
     * @param v this
     */
    public void newExercise(View v){
        // Switch to the CreateWorkout Activity
        Intent newExercisePage = new Intent(this, WorkoutActivity.class);
        startActivity(newExercisePage);
        finish();
    }

    /**
     * Method attached to the 'history' button in Workout home
     *
     * @param v this
     */
    public void viewHistory(View v){
        // Switch to the CreateWorkout Activity
        Intent viewWorkoutHistory = new Intent(this, WorkoutHistoryActivity.class);
        startActivity(viewWorkoutHistory);
        finish();
    }

    /**
     * dbImpl
     *
     * changes the information fields based off the current user from the db
     */
    public void dbImpl() {
        User theUser = fakeDatabase.getCurrentUser();

        if(theUser != null) {
            String fullName = theUser.getFirstName() + " " + theUser.getLastName();
            nameLabel.setText(fullName);
            if(theUser.getGoalWeight() != null) {
                weightLabel.setText(theUser.getGoalWeight().toString() + "lbs.");
            } else weightLabel.setText("180lbs.");
            setProfilePic(theUser);
        }
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