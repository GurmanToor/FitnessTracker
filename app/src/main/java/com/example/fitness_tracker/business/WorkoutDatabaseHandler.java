package com.example.fitness_tracker.business;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserWorkout;
import com.example.fitness_tracker.domain_specific_objects.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDatabaseHandler {
    RealDatabase database;
    Workout workout;
    CalendarLogic calendarLogic;
    User theUser;
    String time;
    String date;
    UserWorkout userWorkout;
    List<Workout> workoutList = new ArrayList<>();

    public WorkoutDatabaseHandler(RealDatabase db){
        calendarLogic = new CalendarLogic();
        database = db;
        time = calendarLogic.getTime();
        date = calendarLogic.toString();
        getCurrentUser();
    }

    private String getDate(){
        return date;
    }
    private String getTime(){
        return time;
    }
    private void setNewWorkout(){
        int id = database.workoutSize()+1;// Fake DB
        System.out.println("ID of new workout = "+id);
        workout = new Workout(id); // Real DB
    }
    public Workout getWorkout(){
        return workout;
    }

    /**
     * newWorkoutObject
     *
     * Creates a new Workout and UserWorkout object
     *
     * @return workout object that was created
     */
    public Workout newWorkoutObject(){
        // Instantiate the id based off the size of database if a workout hasnt been started

        setNewWorkout();

        // User workout to associate user with workout
        userWorkout = new UserWorkout(theUser,getWorkout(),getDate(),getTime());
        try{
            database.insert(getWorkout());
            database.insert(userWorkout);
            List<UserWorkout> list = database.getUserWorkout(theUser.getUsername());
            System.out.println("Size: "+list.size());
        }catch(NullPointerException e){
            System.out.println("Couldnt insert user workout, error: "+e);
        }

        return workout;
    }

    public void setWorkout(Workout wrkt){
        workout = wrkt;
    }

    /**
     * getCurrentUser
     *
     * Extract current user from the db
     */
    public String getCurrentUser() {
        theUser = database.getCurrentUser();
        String username = "";

        if(theUser != null) {
            // Save the user name of the current user
            String fullName = theUser.getFirstName() + " " + theUser.getLastName();
            username = fullName;
        }
        return username;
    }

    /**
     * deleteExercise
     *
     * @param exerciseID exercise from list of exercises in workout to be deleted
     */
    public void deleteExercise(int exerciseID){
        Exercise exe = database.getExercise(exerciseID);
        ExerciseWorkout exeWrkt;
        if(database.getExerciseWorkoutByExercise(exerciseID) != null && exe != null){
            exeWrkt = database.getExerciseWorkoutByExercise(exerciseID).get(0);

            // Delete exercise and exercise workout
            database.delete(exe);
            database.delete((exeWrkt));
        }
    }

    /**
     * deleteWorkout
     *
     * Delete the workout and the UserWorkout object
     */
    public void deleteWorkout(){

        try{
            database.delete(workout);
            database.delete(userWorkout);
        }catch(NullPointerException e){
            System.out.println("Couldnt delete user workout object: "+e);
        }
    }

    /**
     * deleteWorkout
     *
     * Delete the workout and the UserWorkout object
     */
    public void deleteListWorkout(int workoutID){
        // Get all the users UserWorkout objects
        List<UserWorkout> list = database.getUserWorkout(theUser.getUsername());
        UserWorkout usrWrkt = database.getUserWorkout(workoutID).get(0);

        Workout wrkt = database.getWorkout(workoutID);
//        for(int i = 0; i<workoutList.size();i++){
//            // Loop through and find the associated workout
//            if(workoutList.get(i).equals(wrkt)){
//
//            };
//        }

        try{
            database.delete(wrkt);
            database.delete(usrWrkt);
        }catch(NullPointerException e){
            System.out.println("Couldnt delete user workout object: "+e);
        }
    }

    /**
     *
     * @return list of ExerciseWorkout objects from the DB associated with a Workout
     */
    public List<ExerciseWorkout> getExerciseWorkoutList(){
        return database.getExerciseWorkoutByWorkout(workout.getWorkoutID());
    }

    /**
     *
     * @return list of Workout objects from the DB associated with a Workout
     */
    public List<Workout> getWorkoutList(){
        // Get all the users UserWorkout objects
        List<UserWorkout> list = database.getUserWorkout(theUser.getUsername());

        // Extract all the Workout objects
        for(int i = 0; i<list.size();i++){
            // Loop through the users workouts and extract them
            workoutList.add(list.get(i).getWorkout());
            System.out.println(list.get(i).getWorkout().getWorkoutID() + "i value "+i);
        }

        return workoutList;
    }

    public Exercise getExercise(int exerciseID){
        return database.getExercise(exerciseID);
    }
}
