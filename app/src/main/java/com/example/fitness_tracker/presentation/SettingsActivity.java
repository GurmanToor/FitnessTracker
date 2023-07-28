package com.example.fitness_tracker.presentation;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;

import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;
import com.example.fitness_tracker.domain_specific_objects.Workout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * SettingsActivity
 *
 * This is the area where the user settings
 * as well as profile settings are modified/shown
 */
public class SettingsActivity extends AppCompatActivity {
    /**
     * The Database
     */
    RealDatabase database = RealDatabase.getDatabaseInstance();

    /**
     * The current user
     */
    User currentUser = database.getCurrentUser();

    public void toProfile(View view) {
        startActivity(new Intent(SettingsActivity.this, AvatarSelector.class));
    }

    /**
     * onCreate
     *
     * Creating the activity from the xml
     * @param savedInstanceState this
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTheme(R.style.PreferenceScreen);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        editor.putString("name_title", currentUser.getFirstName() + " " + currentUser.getLastName());
        editor.putString("email_title", currentUser.getEmail());
        editor.putString("username_title", currentUser.getUsername());
        editor.putString("password_title", currentUser.getPassword());
        editor.putInt("weight_title", currentUser.getCurrentWeight().intValue());
        editor.putInt("goal_title", currentUser.getGoalWeight().intValue());
        editor.putBoolean("fasting_mode",currentUser.isFasting());
        editor.apply();
        editor.commit();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * onCreateOptionsMenu
     *
     * Creates the options menu for an
     * option in the top bar of the gui
     * @param menu this
     * @return if done successfully or nto
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * onOptionsItemSelected
     *
     * If the back button is selected!
     * @param item the back button
     * @return if it was successful or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
            finish();
            return true;
        } else if(item.getItemId() == R.id.profile) {
            startActivity(new Intent(SettingsActivity.this, AvatarSelector.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * SettingsFragment
     *
     * The fragments for the settings,
     * ie the different areas such as 'account info'
     *
     * Controllers for the buttons and settings
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {

        /**
         * The Database
         */
        RealDatabase database = RealDatabase.getDatabaseInstance();

        /**
         * The current user
         */
        User currentUser = database.getCurrentUser();


        /**
         * onCreatePreferences
         *
         * creating the different segments of the settings area
         * @param savedInstanceState this
         * @param rootKey the key for the preferences xml
         */
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);



            /**
             * RESET THE INSUITE DATABASE
             */
            String preferencesName = this.getPreferenceManager().getSharedPreferencesName();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            editor.commit();
            editor.putString("name_title", currentUser.getFirstName() + " " + currentUser.getLastName());
            editor.putString("email_title", currentUser.getEmail());
            editor.putString("username_title", currentUser.getUsername());
            editor.putString("password_title", currentUser.getPassword());
            editor.putInt("weight_title", currentUser.getCurrentWeight().intValue());
            editor.putInt("goal_title", currentUser.getGoalWeight().intValue());
            editor.putBoolean("fasting_mode",currentUser.isFasting());
            editor.apply();
            editor.commit();


            /**
             *  ---------------- BUTTON CONTROLLERS FROM SETTINGS AREA -----------------
             */


            /**
             * THE CLEAR WEIGHT LOG CONTROLLER
             */
            Preference button = getPreferenceManager().findPreference("clear_log");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        final boolean[] ret = {false};
                        //Toast.makeText(getActivity(), "Preference button is clicked", Toast.LENGTH_SHORT).show();
                       // createAlert(getContext());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Warning!");
                        builder.setMessage("Are you sure you want to clear your Weight Log Data?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ret[0] = true;
                                        Toast.makeText(getActivity(), "Weight Log Data Cleared.", Toast.LENGTH_SHORT).show();

                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return ret[0];
                    }
                });
            }


            /**
             * THE CLEAR WORKOUT LOG CONTROLLER
             */
            button = getPreferenceManager().findPreference("clear_workout");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        final boolean[] ret = {false};
                        //Toast.makeText(getActivity(), "Preference button is clicked", Toast.LENGTH_SHORT).show();
                        // createAlert(getContext());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Warning!");
                        builder.setMessage("Are you sure you want to clear your Workout Log Data?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ret[0] = true;
                                        String currentUsername = currentUser.getUsername();
                                        ArrayList<UserWorkout> listOfUserWorkouts = (ArrayList<UserWorkout>) database.getUserWorkout(currentUsername);
                                        for (int i = 0; i < listOfUserWorkouts.size() - 1; i++) {
                                            Workout currentWorkout = listOfUserWorkouts.get(i).getWorkout();
                                            database.delete(currentWorkout);
                                        }
                                        Toast.makeText(getActivity(), "Workout Log Data Cleared.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ret[0] = false;
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return ret[0];
                    }
                });
            }

            /**
             * THE FASTING CONTROLLER
             */
            SwitchPreference switchButton = (SwitchPreference) getPreferenceManager().findPreference("fasting_title");
            if (switchButton != null) {
                switchButton.setChecked(database.getCurrentUser().isFasting());
                switchButton.setChecked(getPreferenceManager().getSharedPreferences().getBoolean("fasting_mode",true));
                switchButton.setOnPreferenceChangeListener((preference, newValue) -> {
                    boolean isOn = (boolean) newValue;

                    if (isOn) {
                        database.getCurrentUser().setFasting(true);
                    }
                    else {
                        database.getCurrentUser().setFasting(false);
                    }
                    return true;
                });
            }

            /**
             * THE NAME CONTROLLER
             */
            button = getPreferenceManager().findPreference("name_title");
            if (button != null) {
                button.setKey(currentUser.getFirstName() + " " + currentUser.getLastName());
                //   button.setDefaultValue(fakeDatabase.getCurrentUser().getFirstName() + " " + fakeDatabase.getCurrentUser().getLastName());
                button.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        boolean ret = true;
                        if (!confirmName(newValue)) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Invalid Input");
                            builder.setMessage("You need a first and last name!");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.show();
                            ret = false;
                        }
                        String name = newValue.toString().trim();
                        String[] names = name.split(" ");
                        database.getCurrentUser().setFirstName(names[0]);
                        database.getCurrentUser().setLastName(names[1]);

                        return ret;
                    }
                });
            }

            /**
             * THE EMAIL CONTROLLER
             */
            button = getPreferenceManager().findPreference("email_title");
            if (button != null) {
                button.setKey(currentUser.getEmail());
                button.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        boolean ret = true;
                        if (!confirmEmail(newValue)) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Invalid Input");
                            builder.setMessage("You need a valid email address!");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.show();
                            ret = false;
                        }
                        String email = newValue.toString().trim();
                        database.getCurrentUser().setEmail(email);

                        return ret;
                    }
                });
            }

            /**
             * THE LOG OUT CONTROLLER
             */
            button = getPreferenceManager().findPreference("logout");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        final boolean[] ret = {false};
                        //Toast.makeText(getActivity(), "Preference button is clicked", Toast.LENGTH_SHORT).show();
                        // createAlert(getContext());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Warning!");
                        builder.setMessage("Are you sure you want to Log Out?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ret[0] = true;
                                        database.resetCurrentUser();
                                        Intent login = new Intent(getContext(), LoginActivity.class);
                                        getContext().startActivity(login);
                                        Toast.makeText(getActivity(), "Logged Out.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ret[0] = false;
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return ret[0];
                    }
                });
            }

            /**
             * THE USERNAME TITLE
             */
            button = getPreferenceManager().findPreference("username_title");
            if (button != null) {
                button.setKey(currentUser.getUsername());
                button.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        boolean ret = true;
                        if (!confirmUsername(newValue)) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Invalid Input");
                            builder.setMessage("Invalid Username!");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.show();
                            ret = false;
                        }

                        String username = newValue.toString().trim();
                        database.getCurrentUser().setUsername(username);

                        return ret;
                    }
                });
            }

            /**
             * Weight Log notification
             */
            button = getPreferenceManager().findPreference("notifications");

            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        final boolean[] ret = {false};
                        final int[] hour = new int[1];
                        final int[] minute = new int[1];

                        //setup the time picker dialog box
                        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            //store the hour and minute selected by the user
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hour[0] = i;
                                System.out.println("Done");
                                minute[0] = i1;
                            }
                        };
                        //show the time picker dialog box
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour[0], minute[0], true);
                        timePickerDialog.setTitle("Select Time");
                        timePickerDialog.show();

                        //notification set up
                        Calendar calendar = Calendar.getInstance();

                        // create the notificationReceiver intent
                        Intent intent= new Intent(getContext(), NotificationReceiver.class);
                        intent.putExtra("id","N_id");
                        intent.putExtra("name","weight log notification");
                        intent.putExtra("description","Log your weight");

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                        //setup the alarm manager
                        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(ALARM_SERVICE);

                        //create time
                        calendar =Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hour[0]);
                        calendar.set(Calendar.MINUTE, hour[0]);
                        calendar.set(Calendar.SECOND, 0);

                        //set the alarm to repeat daily
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


                        return ret[0];
                    }

                });
            }

            /**
             * THE CLEAR NOTIFICATIONS CONTROLLER
             */
            button = getPreferenceManager().findPreference("clear_notifications");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        final boolean[] ret = {false};
                        //Toast.makeText(getActivity(), "Preference button is clicked", Toast.LENGTH_SHORT).show();
                        // createAlert(getContext());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Warning!");
                        builder.setMessage("Are you sure you want to clear all notifications");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent= new Intent(getContext(), NotificationReceiver.class);
                                        intent.putExtra("delete_id","N_id");
                                        ret[0] = true;
                                        Toast.makeText(getActivity(), "Notifications Cleared.", Toast.LENGTH_SHORT).show();

                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ret[0] = false;
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return ret[0];
                    }
                });
            }


            /**
             * THE PASSWORD CONTROLLER
             */
            button = getPreferenceManager().findPreference("password_title");
            if (button != null) {
                button.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        boolean ret = true;
                        if (confirmPassword(newValue)) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Invalid Input");
                            builder.setMessage("Invalid Password!");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.show();
                            ret = false;
                        }
                        String password = newValue.toString().trim();
                        database.getCurrentUser().setPassword(password);
                        return ret;
                    }
                });
            }
            EditTextPreference pass_pref = getPreferenceManager().findPreference("password_title");
            if (pass_pref != null) {
                pass_pref.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                    @Override
                    public void onBindEditText(@NonNull EditText editText) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                });
                pass_pref.setSummaryProvider(new Preference.SummaryProvider() {
                    @Override
                    public CharSequence provideSummary(Preference preference) {
                        int valueSize= PreferenceManager.getDefaultSharedPreferences(getContext()).getString(preference.getKey(),"").length();
                        String newSummary = "";
                        for (int x = 0; x < valueSize; x++) {
                            newSummary += "*";
                        }
                        return newSummary;
                    }
                });
            }

            /**
             * THE PROFILE PIC CONTROLLER
             */
            button = getPreferenceManager().findPreference("profile");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {

                        return true;
                    }
                });
            }

            /**
             * THE GOAL WEIGHT SLIDER CONTROLLER
             */
            SeekBarPreference seekBarPreference = getPreferenceManager().findPreference("goal_title");
            if (seekBarPreference != null) {
                seekBarPreference.setValue(currentUser.getGoalWeight().intValue());
                seekBarPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        int num = Integer.parseInt(newValue.toString());
                        database.getCurrentUser().setGoalWeight(new BigDecimal(num));
                        return true;
                    }
                });
            }

            /**
             * THE WEIGHT SLIDER CONTROLLER
             */
            seekBarPreference = getPreferenceManager().findPreference("weight_title");
            if (seekBarPreference != null) {
                seekBarPreference.setValue(currentUser.getCurrentWeight().intValue());
                seekBarPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        int num = Integer.parseInt(newValue.toString());
                        database.getCurrentUser().setCurrentWeight(new BigDecimal(num));
                        return true;
                    }
                });
            }

            button = getPreferenceManager().findPreference("sync");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        Toast.makeText(getActivity(), "Email will now be synced", Toast.LENGTH_SHORT).show();
                        //createAlert(getContext());
                        return true;
                    }
                });
            }
        }

        /**
         * confirmName
         *
         * confirms if the given input has a first and last name
         * @param input
         * @return if it does or not
         */
        private boolean confirmName(Object input) {
            String s = input.toString();
            return s.contains(" ");
        }

        /**
         * confirmEmail
         *
         * confirms if the given input is a proper email address
         * @param input
         * @return if it does or not
         */
        private boolean confirmEmail(Object input) {
            String email = input.toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            return email.matches(emailPattern);
        }

        /**
         * confirmUsername
         *
         * confirms if the given input is a proper username
         * @param input
         * @return if it does or not
         */
        private boolean confirmUsername(Object input) {
            String s = input.toString();
            return !s.isEmpty();
        }

        /**
         * confirmPassword
         *
         * confirms if the given input is a proper password
         * @param input
         * @return if it does or not
         */
        private boolean confirmPassword(Object input) {
            String s = input.toString();
            return s.isEmpty();
        }
    }
}