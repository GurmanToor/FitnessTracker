package com.example.fitness_tracker.presentation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.application.Main;
import com.example.fitness_tracker.domain_specific_objects.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * LoginActivity
 *
 * The login screen gui and controller
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * the linked username field
     */
    EditText usernameField;

    /**
     * the linked password field
     */
    EditText passwordField;

    /**
     * the database instance
     */
    RealDatabase database = null;

    /**
     * onCreate
     *
     * Links the buttons and opens the activity
     * @param savedInstanceState this
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.editText6);
        passwordField =  findViewById(R.id.editText7);

        copyDatabaseToDevice();
        database = RealDatabase.getDatabaseInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fitness Tracker Group 12 :)");
    }

    /**
     * openRegister
     *
     * opens the registration area
     * @param view this
     */
    public void openRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    /**
     * openHome
     *
     * opens the home screen
     * IF the user exists, if not 'try again'
     *
     * @toDeprecate if fields are empty and login is clicked,
     *              BYPASS with default values [developer mode]
     * @param view this
     */
    public void openHome(View view) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        // if there is input
        if(!username.isEmpty() && !password.isEmpty()) {
            if(database.getUser(username) != null) {
                User theUser = database.getUser(username);
                if(theUser.getPassword().equals(password)) {
                    database.setCurrentUser(theUser);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    alertDialog();
                }
            } else {
                alertDialog();
            }
        } else {
            System.out.println("RUNNING BYPASS FOR USER!");
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }

    /**
     * alertDialog
     *
     * opens an alert dialog view if needed
     * when needed: when login is wrong
     */
    public void alertDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Wrong username or password");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Create an Account",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Try Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

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
