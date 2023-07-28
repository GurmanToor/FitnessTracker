package com.example.fitness_tracker.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.example.fitness_tracker.R;
import java.util.HashSet;

/**
 * Note Editor
 *
 * The notepad area for the daily diary
 * Inspired by: https://www.geeksforgeeks.org/how-to-build-a-simple-notes-app-in-android/
 */
public class NoteEditorActivity extends AppCompatActivity {

    /**
     * The id specifier for the
     */
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        /**
         * The notepad field
         */
        EditText editText = findViewById(R.id.editText);

        /**
         * The current intent instance that we are in
         */
        Intent intent = getIntent();

        /**
         * Accessing the data from the given instance
         */
        noteId = intent.getIntExtra("noteId", -1);

        /**
         * If empty
         */
        if (noteId != -1) {
            editText.setText(DiaryActivity.notes.get(noteId));
        } else {
            DiaryActivity.notes.add("");
            noteId = DiaryActivity.notes.size() - 1;
            DiaryActivity.arrayAdapter.notifyDataSetChanged();
        }

        /**
         * Adds the new text to the phones storage to be
         * accessed whenever
         */
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // required impl
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DiaryActivity.notes.set(noteId, String.valueOf(charSequence));
                DiaryActivity.arrayAdapter.notifyDataSetChanged();

                /**
                 * Creating the note object to store the data in the phone
                 */
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(DiaryActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply(); // storing the data!
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // required impl
            }
        });
    }

    /**
     * backPage
     *
     * Goes back to the previous page on click
     * @param view the current instance
     */
    public void backPage(View view) {
        startActivity(new Intent(NoteEditorActivity.this, DiaryActivity.class));
        finish();
    }
}