package com.example.fitness_tracker.business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fitness_tracker.R;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.Workout;

import java.util.List;

public class WorkoutAdapter extends BaseAdapter {
    Activity mActivity;
    List<Workout> workoutList;

    public WorkoutAdapter(Activity activity, List<Workout> workouts){
        this.mActivity = activity;
        workoutList = workouts;
    };
    @Override
    public int getCount() {
        // Get the size of the list
        return workoutList.size();
    }

    @Override
    public Workout getItem(int i) {
        // Get the workout
        return workoutList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     *
     * @param i position of the value
     * @param view
     * @param viewGroup parent connector
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Send back the properties to use in the layout, this is an individual item
        View currentWorkoutListItem; // This is associated with the layout 'current_workout_exersize_list_item'

        // Turns XML file and lines up with View, associates inflater with activity
        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Item that will be inflated (parent viewGroup)
        currentWorkoutListItem = layoutInflater.inflate(R.layout.workout_list_item, viewGroup, false);

        // Get the current Workout
        Workout wrkt = this.getItem(i);

        // Link to the associated Text holder in the layout
        TextView title = currentWorkoutListItem.findViewById(R.id.wrkt_title);
//        TextView length = currentWorkoutListItem.findViewById(R.id.wrkt_length);


        // Need to set icont value so know which to delete
        ImageView deleteIcon = (ImageView) currentWorkoutListItem.findViewById(R.id.delete_wrkt);
        deleteIcon.setTag(wrkt.getWorkoutID()); // Set the id for which exercise to delete


        title.setText("Workout #" + wrkt.getWorkoutID());
//        length.setText(wrkt.getLength());

        // Need to set up the onClick so that when delete button pressed, it will pass ID back through view
        // Source: https://stackoverflow.com/questions/8527101/how-to-know-which-view-inside-a-specific-listview-item-that-was-clicked
        currentWorkoutListItem.findViewById(R.id.delete_wrkt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) viewGroup).performItemClick(v, i, 0);
            }
        });

        return currentWorkoutListItem;
    }
}
