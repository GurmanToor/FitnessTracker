package com.example.fitness_tracker.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.User;

import java.math.BigDecimal;

/**
 * RegisterActivity
 *
 * The controller and gui for the account registration section
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * The Inputted Full Name
     */
    EditText fullNameField;

    /**
     * The Inputted Username
     */
    EditText usernameField;

    /**
     * The Inputted Password
     */
    EditText passwordField;

    /**
     * The inputted confirmation password
     */
    EditText confirmPasswordField;

    /**
     * The database instance
     */
    RealDatabase database = RealDatabase.getDatabaseInstance();

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

        setContentView(R.layout.activity_register);

        fullNameField = findViewById(R.id.editText_name);
        usernameField = findViewById(R.id.editText);
        passwordField =  findViewById(R.id.editText2);
        confirmPasswordField = findViewById(R.id.editText3);
    }

    /**
     * onRegister
     *
     * function ran once "register now" button is clicked
     * @param view this
     */
    public void onRegister(View view) {

        /**
         * the current username
         * @string
         */
        String username = usernameField.getText().toString();

        /**
         * the current fullname
         * @string
         */
        String fullName = fullNameField.getText().toString();

        /**
         * the current password
         * @string
         */
        String password = passwordField.getText().toString();

        /**
         * the confirmed password
         * @string
         */
        String confirmPassword = confirmPasswordField.getText().toString();

        /**
         * separation of fullname to the first and last name
         */
        String firstName = fullName;
        String lastName = "";

        /**
         * if the inputs were valid or not
         */
        boolean moveOn = true;

        /**
         * If there was actually input
         *
         */
        if(!username.isEmpty() && !password.isEmpty() && !fullName.isEmpty() && !confirmPassword.isEmpty()) {
            try {
                if(fullName.contains(" ")) {
                    String[] names = fullName.split(" ");
                    firstName = names[0];
                    lastName = names[1];
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Invalid Input");
                    builder.setMessage("Invalid Name! Please Try Again.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.show();
                    moveOn = false;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                moveOn = false;
            }

            if(!password.equals(confirmPassword)) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Invalid Input");
                builder.setMessage("Passwords do not line up! Please Try Again.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.show();
                moveOn = false;
            }
            if(moveOn) {
                User user = new User(username, password, firstName, lastName, new BigDecimal("0.0"),"temp");
                database.insert(user);
                database.setCurrentUser(user);
            }
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid Input");
            builder.setMessage("Something was left blank. Please Try Again!");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.show();
            moveOn = false;
        }
        if(moveOn) {
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
        } else {
            usernameField.getText().clear();
            passwordField.getText().clear();
            confirmPasswordField.getText().clear();
            fullNameField.getText().clear();
        }
    }

    /**
     * onLogin
     *
     * if switching to login screen
     * @param view this
     */
    public void onLogin(View view) {
        finish();
    }
}
