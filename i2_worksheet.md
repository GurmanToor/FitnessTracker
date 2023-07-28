# Paying off Technical Debt

## Debt Instance 1
- Originally the term 'Workout' was implemented for the forms, when it should have been 'Exercise', so that a single workout could contain multiple exercises. Thus, there was some technical debt in having to rename all the forms and associated values from 'Workout' to 'Exercise'. 
    - This can be seen in merge request !21 <br/>
        - commit: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/commit/8495d0eb0d5cd92ce79f71352ecd42ebda060b49 

- The Cardio and Strength classes had to be changed from being a subclass of Workout object to being a subclass of an Exercise object, and had to change their constructors as well.
    - commit: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/commit/5383f543be484b5f2015ebef8bf395e0de462125 
    - location showing changing Cardio constructor https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/commit/5383f543be484b5f2015ebef8bf395e0de462125#f9ad2eeb32956b14508dc4f86b470fa79cc02194_30_30 
    - location showing changing Strength constructor https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/commit/5383f543be484b5f2015ebef8bf395e0de462125#0082ef2e3a0b7ce5a9eb233a18d4c6e906994555_37_37 

- Debt Classification:
    - I would classify this as <b>Inadvertant and reckless</b> debt. It was reckless because we had one person working on the Domain Objects and then the other working on the forms for submitting a Workout. There probably should have been more communication from the person making the form with the person finishing the classes on how each thought the layout would occur. And thus it was inadvertant because it was a learning lesson for working in a group and making sure everyone lines up with how the program will be layed out.

## Debt Instance 2
- The class WeightLogger in the logic layer saves a user's weight to a database and also enable retrival of previous weights a user has logged from the database. But instead of using a fake databse, an ArrayList was used in the class implementation so that the class functionality could be tested with more data. The plan was to use the arraylist in areas that a database query returned a list, and then once we tested the class functionality, it could easily be swapped. However, when it was time to switch to the database there were a bunch of errors because the way the arraylist was constructed was slightly different from what the database returned, hence some functions had to be rewritten.
    - This can be seen in merge request !25 <br/>
        - commit: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/commit/5b63c0b9b7ab596cdfeb07997d513ac660fbb8b5#b765e496be232f08f769ee1e202a18ecd2d67031_80_103
        - commit: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/commit/5b63c0b9b7ab596cdfeb07997d513ac660fbb8b5#b765e496be232f08f769ee1e202a18ecd2d67031_114_178

- Debt Classification:
    - I would classify this as <b>Inadvertant and reckless</b> debt. It was inadvertant because we knew we should have used a databse for the tests, but we did not have one ready yet and we needed to ensure that the class works well given large amount of data. It was reckless because we did not take time to understand how the database was going to be constructed and what it would return.

# SOLID

https://code.cs.umanitoba.ca/winter-2022-a01/group-11/dnd-character-manager/-/issues/41


# Retrospective
Iteration 1 went pretty well, but there are still some places for improvement.
- We give more generals time consumption estimates this time since it gets more complicated as we need to implement a real database. [Find time estimates here](https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/issues/9).
- In interation 1 we commit our code after we finish our code, it cost more time and sometimes we forgot what the code does. We commit our code as we are writing them this time so less fuss is made. [Find our code here](https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/blob/main/app/src/main/java/com/example/fitness_tracker/presentation/CreateCardioExerciseActivity.java).


# Design Patterns
Throughout this iteration we used many different design patterns for to our advantage. We predominantly used Chain of Responsibility, with several layers of processing from the gui to the database, a majority of our methods are dependent on other methods beforehand. We find it especially helpful to ensure our code is neat and clean, ensuring that we meet our standards set as a group for our documentation rules. During debugging, because we used Chain of Responsibility, we are able to find where the exact error is. 

Examples:


 app/src/main/java/com/example/fitness_tracker/presentation/HomeActivity.java 

    - onCreate() -> dbImpl() & copyDatabaseToDevice() -> setProfilePic() & copyAssetsToDirectory()


 app/src/main/java/com/example/fitness_tracker/business/DailyCalorieAdapter.java

    - createFragment() -> BreakfastFragment() & LunchFragment() & DinnerFragment()


 app/src/main/java/com/example/fitness_tracker/presentation/WorkoutActivity.java


Additonally, we use a large amount of listeners, which we use to our advantage to execute custom code for our onclick or onchange events from the gui to the logic layers.


Examples:

app/src/main/java/com/example/fitness_tracker/presentation/SettingsActivity.java

app/src/main/java/com/example/fitness_tracker/presentation/HomeActivity.java

    - Predominantly listeners for the gui to logic layer


# Iteration 1 Feedback Fixes

## Fix 1
- In the issue opened last iteration by another group, found here: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/issues/47 
- This was fixed and the fix can be found here: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/blob/2111247a2307fd1bb640bc031dff692499738d66/app/src/main/java/com/example/fitness_tracker/CreateCardioWorkoutActivity.java 
## Fix 2
- The grader in their feedback to us they stated: 
    - "While adding the workout, though it shows the data has been added successfully but it is not listed anywhere!". 
- This has now been partially implemented, at least the exercises added to a workout can be seen. But in Iteration 3 is where 'Workout History' will be implemented, where the user will be able to access their workouts they created. 
- The creation of the workout to the Database can be found:
        https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/blob/main/app/src/main/java/com/example/fitness_tracker/business/WorkoutDatabaseHandler.java 
- The creation form forms for the exercises are:
1) Cardio: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/blob/main/app/src/main/java/com/example/fitness_tracker/presentation/CreateCardioExerciseActivity.java
2) Strength: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/blob/main/app/src/main/java/com/example/fitness_tracker/presentation/CreateStrengthExerciseActivity.java

- And once these forms are submitted, the new exercise is created and will be shown on the main Workout creation page in a list, the list growing for each exercise that is added. The page where exercises are listed after creation is here:
https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/blob/main/app/src/main/java/com/example/fitness_tracker/presentation/WorkoutActivity.java 

