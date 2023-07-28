package com.example.fitness_tracker.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.User;

import java.text.DateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AvatarSelector extends AppCompatActivity {
    /**
     * The Database instance
     */
    RealDatabase fakeDatabase = RealDatabase.getDatabaseInstance();

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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_avatar_selector);

        invalidateOptionsMenu();
    }

    public void toProfileChange(View view) {
        fakeDatabase.getCurrentUser().setProfilePicId(0);
        startActivity(new Intent(AvatarSelector.this, HomeActivity.class));
        finish();
        Toast.makeText(this, "Profile picture changed..", Toast.LENGTH_SHORT).show();
    }

    public void toProfileChange2(View view) {
        fakeDatabase.getCurrentUser().setProfilePicId(2);
        startActivity(new Intent(AvatarSelector.this, HomeActivity.class));
        finish();
        Toast.makeText(this, "Profile picture changed..", Toast.LENGTH_SHORT).show();
    }

    public void toProfileChange3(View view) {
        fakeDatabase.getCurrentUser().setProfilePicId(3);
        startActivity(new Intent(AvatarSelector.this, HomeActivity.class));
        finish();
        Toast.makeText(this, "Profile picture changed..", Toast.LENGTH_SHORT).show();
    }

    public void toProfileChange5(View view) {
        fakeDatabase.getCurrentUser().setProfilePicId(5);
        startActivity(new Intent(AvatarSelector.this, HomeActivity.class));
        finish();
        Toast.makeText(this, "Profile picture changed..", Toast.LENGTH_SHORT).show();
    }

    public void toProfileChange6(View view) {
        fakeDatabase.getCurrentUser().setProfilePicId(6);
        startActivity(new Intent(AvatarSelector.this, HomeActivity.class));
        finish();
        Toast.makeText(this, "Profile picture changed..", Toast.LENGTH_SHORT).show();
    }

    public void toProfileChange7(View view) {
        fakeDatabase.getCurrentUser().setProfilePicId(7);
        startActivity(new Intent(AvatarSelector.this, HomeActivity.class));
        finish();
        Toast.makeText(this, "Profile picture changed..", Toast.LENGTH_SHORT).show();
    }

}