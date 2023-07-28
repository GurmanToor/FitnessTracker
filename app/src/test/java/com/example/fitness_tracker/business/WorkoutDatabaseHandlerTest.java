/*********************************************
 * Integration tests for WorkoutDatabaseHandler
 * - Integration between the Logic Layer and Database
 */
package com.example.fitness_tracker.business;

import static org.junit.Assert.*;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.ExerciseWorkout;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.persistence.IExercisePersistence;
import com.example.fitness_tracker.persistence.IExerciseWorkoutPersistence;
import com.example.fitness_tracker.persistence.IFoodMealPersistence;
import com.example.fitness_tracker.persistence.IFoodPersistence;
import com.example.fitness_tracker.persistence.IMealPersistence;
import com.example.fitness_tracker.persistence.IUserMealPersistence;
import com.example.fitness_tracker.persistence.IUserPersistence;
import com.example.fitness_tracker.persistence.IUserWeightPersistence;
import com.example.fitness_tracker.persistence.IUserWorkoutPersistence;
import com.example.fitness_tracker.persistence.IWeightPersistence;
import com.example.fitness_tracker.persistence.IWorkoutPersistence;
import com.example.fitness_tracker.persistence.hsqldb.ExercisePersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.ExerciseWorkoutPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.FoodMealPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.FoodPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.MealPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.UserMealPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.UserPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.UserWeightPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.UserWorkoutPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.WeightPersistenceHSQLDB;
import com.example.fitness_tracker.persistence.hsqldb.WorkoutPersistenceHSQLDB;
import com.example.fitness_tracker.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class WorkoutDatabaseHandlerTest {
    private static RealDatabase db;
    private File tempDB;
    private static WorkoutDatabaseHandler dbHandler;
    private static User theUser;

    @Before
    public void setUp() throws Exception {
        this.tempDB = TestUtils.copyDB();
        final IUserPersistence userPersistence = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserWorkoutPersistence userWorkoutPersistence = new UserWorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserWeightPersistence userWeightPersistence = new UserWeightPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserMealPersistence userMealPersistence = new UserMealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IExercisePersistence exercisePersistence = new ExercisePersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IExerciseWorkoutPersistence exerciseWorkoutPersistence = new ExerciseWorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IWorkoutPersistence workoutPersistence = new WorkoutPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IFoodMealPersistence foodMealPersistence = new FoodMealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IWeightPersistence weightPersistence = new WeightPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IMealPersistence mealPersistence = new MealPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IFoodPersistence foodPersistence = new FoodPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        db = new RealDatabase(true);
        db.setUserTable(new AccessUser(userPersistence));
        db.setUserWorkoutTable(new AccessUserWorkout(userWorkoutPersistence));
        db.setUserWeightTable(new AccessUserWeight(userWeightPersistence));
        db.setUserMealTable(new AccessUserMeal(userMealPersistence));
        db.setExerciseTable(new AccessExercise(exercisePersistence));
        db.setExerciseWorkoutTable(new AccessExerciseWorkout(exerciseWorkoutPersistence));
        db.setWorkoutTable(new AccessWorkout(workoutPersistence));
        db.setFoodMealTable(new AccessFoodMeal(foodMealPersistence));
        db.setWeightTable(new AccessWeight(weightPersistence));
        db.setMealTable(new AccessMeal(mealPersistence));
        db.setFoodTable(new AccessFood(foodPersistence));
        dbHandler = new WorkoutDatabaseHandler(db);
        theUser = db.getUser("janesmith123");
        db.setCurrentUser(theUser);
        dbHandler.getCurrentUser();
    }

    @After
    public void tearDown() throws Exception {
        dbHandler.deleteWorkout();
        dbHandler.deleteExercise(0);
    }

    @Test
    public void newWorkoutObjectCreated() {
        System.out.println("Testing to make sure a new Workout object added with the handler.");
        int initialDBSize = db.workoutSize();
        assertEquals(db.workoutSize(),initialDBSize);
        dbHandler.newWorkoutObject();
        assertEquals(db.workoutSize(),initialDBSize+1);
    }


    @Test
    public void getCurrentUser() {
        System.out.println("Testing to make sure the user can be accessed with the handler.");
        assertEquals(dbHandler.getCurrentUser(),theUser.getFirstName()+" "+theUser.getLastName());
    }

    @Test
    public void deleteExercise() {
        // Set up the exercise in DB
        Cardio exe = new Cardio(5,"Crunch","Abs","Cardio",22,"km","22:11:11");
        dbHandler.newWorkoutObject();

        ExerciseWorkout exeWrkt = new ExerciseWorkout(exe, dbHandler.getWorkout());
        int initialExerciseDBSize = db.exerciseSize();

        db.insert(exe);
        db.insert(exeWrkt);
        System.out.println("hi");
        // Now test that exercise was inserted and deleted
        assertTrue(db.getExercise(exe.getExerciseID()).equals(exe));
        assertEquals(db.exerciseSize(),initialExerciseDBSize+1);
        dbHandler.deleteExercise(exe.getExerciseID());
        assertEquals(db.exerciseSize(),initialExerciseDBSize);
    }

    @Test
    public void deleteWorkout() {
        int initialWorkoutSize = db.workoutSize();
        dbHandler.newWorkoutObject();
        assertEquals(db.workoutSize(),initialWorkoutSize+1);
        dbHandler.deleteWorkout();
        assertEquals(db.workoutSize(),initialWorkoutSize);
    }

    @Test
    public void getExerciseWorkoutList() {
        // Set up the exercise in DB
        Cardio exe = new Cardio(6,"Crunch","Abs","Cardio",22,"km","22:11:11");
        dbHandler.newWorkoutObject();

        ExerciseWorkout exeWrkt = new ExerciseWorkout(exe, dbHandler.getWorkout());

        db.insert(exe);
        db.insert(exeWrkt);

        List<ExerciseWorkout> list = dbHandler.getExerciseWorkoutList();
        assertTrue(list.get(0).equals(exeWrkt));
    }

}