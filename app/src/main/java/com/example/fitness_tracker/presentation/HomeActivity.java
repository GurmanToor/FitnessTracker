package com.example.fitness_tracker.presentation;

// IMPORTS
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.WeightLog;
import com.example.fitness_tracker.application.Main;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.google.android.material.snackbar.Snackbar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * HomeActivity
 *
 * Home screen gui and controller
 */
public class HomeActivity extends AppCompatActivity {
    /**
     * nameLabel
     *
     * the current users name
     */
    TextView nameLabel;

    /**
     * weightLabel
     *
     * the current users weight
     * @tochange to current users GOAL weight
     */
    TextView weightLabel;

    /**
     * the current day field
     */
    TextView currentDayLabel;

    /**
     * The profile pic
     */
    CircleImageView profilePic;

    /**
     * The database instance
     */
    RealDatabase database = RealDatabase.getDatabaseInstance();

    /**
     * Progress bar for the caloric goals
     */
    ProgressBar progressBar;



    /**
     * onCreate
     *
     * Links the buttons and opens the activity
     * @param savedInstanceState this
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_home);

        invalidateOptionsMenu();

    //    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));

        nameLabel = findViewById(R.id.textViewName);
        weightLabel =  findViewById(R.id.textViewWeight);
        progressBar = findViewById(R.id.daily_goal_progressBar);

        //get updated user progress for their daily calories
        Intent intent = getIntent();
        int newProgress = intent.getIntExtra("new progress",0);
        //update the progress bar
        progressBar.setProgress(newProgress);
        //reset the calories progress bar
        progressBar.setOnLongClickListener(view -> {
            progressBar.setProgress(0);
            //update the user that the reset was successful
            Snackbar.make(view, "Progress reset", Snackbar.LENGTH_SHORT).show();
            return true;
        });
        weightLabel = findViewById(R.id.textViewWeight);
        currentDayLabel = findViewById(R.id.current_day);
        profilePic = findViewById(R.id.imageView);

        dbImpl();
        currentDayLabel.setText(DateFormat.getDateInstance().format(new Date()));

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //dbImpl();
        //copyDatabaseToDevice();
    }

    /**
     * dbImpl
     *
     * changes the information fields based off the current user from the db
     */
    public void dbImpl() {
        User theUser = database.getCurrentUser();

        if(theUser != null) {
            String fullName = theUser.getFirstName() + " " + theUser.getLastName();
            nameLabel.setText(fullName);
            weightLabel.setText(theUser.getGoalWeight().toString() + "lbs.");
            // CURRENT WEIGHT
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

    /**
     * toNotes
     *
     * Switches to notes activity
     * @param view this
     */
    public void toNotes(View view) {
        startActivity(new Intent(HomeActivity.this, DiaryActivity.class));
        finish();
    }

    /**
     * toWorkout
     *
     * Switches to workout activity
     * @param view this
     */
    public void toWorkout(View view) {
        startActivity(new Intent(HomeActivity.this, WorkoutHomeActivity.class));
    }

    /**
     * toSettings
     *
     * Switches to settings activity
     * @param view this
     */
    public void toSettings(View view) {
        startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
        finish();
    }

    /**
     * openWeighLogging
     *
     * Switches to weight logging activity
     * @param view this
     * @tofix the method name!
     */
    public void openWeighLogging(View view) {
        startActivity(new Intent(this, WeightLog.class));
    }

    /**
     * toDailyCalorie
     *
     * Switches to daily calorie activity
     * @param view this
     * @tofix the method name!
     */
    public void toDailyCalorie(View view) {
        //create new intent
        Intent i = new Intent(this, DailyCalorieGoal.class);
        //pass the users goal progress to the next activity
        i.putExtra("current progress" ,progressBar.getProgress() );
        //lunch the activity
        startActivity(i);
    }

    /**
     * toMealActivity
     *
     * Switches to Meal activity
     * @param view this
     */
    public void toMyMeal(View view) {
        startActivity(new Intent(this, MealActivity.class));
    }

    /**
     * refreshPage
     *
     * refreshes the activity
     */
    @Deprecated
    public void refreshPage() {
        finish();
        startActivity(getIntent());
    }

    /**
     * copyDatabaseToDevice
     *
     * Adds paths to the data directory in order
     * for the database to be copied to the device
     *
     * FROM THE EXAMPLE IN CLASS
     */
    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }
            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            Toast.makeText(this, ioe.getMessage(),Toast.LENGTH_LONG);
        }
    }

    /**
     * copyAssetsToDirectory
     *
     * copies the assets to the local directory
     * for the local database
     *
     * FROM THE EXAMPLE IN CLASS
     * @param assets the array of assets
     * @param directory the db
     * @throws IOException if the split does not work
     */
    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}
