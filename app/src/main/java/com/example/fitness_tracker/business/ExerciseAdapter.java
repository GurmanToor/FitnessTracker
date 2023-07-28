/*********************************************
Source Tutorial Playlist:
    Link: https://www.youtube.com/playlist?list=PLhPyEFL5u-i0Ld3UbwqO9eJ8Gl8WuSBl5
    Channel Name: "Programming with Professor Sluiter"
 *********************************************/
package com.example.fitness_tracker.business;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.R;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;

import java.util.List;

public class ExerciseAdapter extends BaseAdapter {
    Activity mActivity;
    List<ExerciseWorkout> exerciseList;
//    RealDatabase fakeDatabase;

    public ExerciseAdapter(Activity activity, List<ExerciseWorkout> exercises){
        this.mActivity = activity;
        exerciseList = exercises;
    };
    @Override
    public int getCount() {

        return exerciseList.size();
    }

    @Override
    public Exercise getItem(int i) {
        return exerciseList.get(i).getExercise();
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
        // Send back the properties to use in the layout
        View currentWorkoutExercizeListItem; // This is associated with the layout 'current_workout_exersize_list_item'

        // Turns XML file and lines up with View
        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        currentWorkoutExercizeListItem = layoutInflater.inflate(R.layout.current_workout_exersize_list_item,viewGroup,false);

        // Get the current Exercise Workout
        Exercise exeWrkt = this.getItem(i);

        // Link to the associated Text holder in the layout
        TextView exerciseType = currentWorkoutExercizeListItem.findViewById(R.id.exercise_type);
        TextView exerciseLength = currentWorkoutExercizeListItem.findViewById(R.id.exercise_length);
        TextView bodyPart = currentWorkoutExercizeListItem.findViewById(R.id.body_part);
        ImageView wrktIcon = (ImageView) currentWorkoutExercizeListItem.findViewById(R.id.exercise_icon);

        // Need to set icont value so know which to delete
        ImageView deleteIcon = (ImageView) currentWorkoutExercizeListItem.findViewById(R.id.delete_button);
        deleteIcon.setTag(exeWrkt.getExerciseID()); // Set the id for which exercise to delete

        // Need to set icont value so know which to edit
        ImageView editIcon = (ImageView) currentWorkoutExercizeListItem.findViewById(R.id.edit_exercise_button);
        editIcon.setTag(exeWrkt.getExerciseID()); // Set the id for which exercise to delete

        // Extract the object values
        String getName = exeWrkt.getType(); // Use to determine which picture to use
        String type = exeWrkt.getName();

        exerciseType.setText(type);
        exerciseLength.setText(exeWrkt.getLength());
        bodyPart.setText(exeWrkt.getBodyPart());

        if(getName.equals("Cardio")){
            if(type.equals("Walk")){
                wrktIcon.setImageResource(R.drawable.ic_walk);
            }else if(type.equals("Bicycle")){
                wrktIcon.setImageResource(R.drawable.ic_bicycle);

            }else if(type.equals("Run")){
                wrktIcon.setImageResource(R.drawable.ic_run);
            }
        }

        // Need to set up the onClick so that when delete button pressed, it will pass ID back through view
        // Source: https://stackoverflow.com/questions/8527101/how-to-know-which-view-inside-a-specific-listview-item-that-was-clicked
        currentWorkoutExercizeListItem.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) viewGroup).performItemClick(v, i, 0);
            }
        });

        // Need to set up the onClick so that when edit button pressed, it will pass ID back through view
        currentWorkoutExercizeListItem.findViewById(R.id.edit_exercise_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) viewGroup).performItemClick(v, i, 0);
            }
        });

        return currentWorkoutExercizeListItem;
    }
}
