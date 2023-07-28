What technical debt has been cleaned up
========================================
Commit d615bea596ba1c437fa612d47dd5022184d0e139: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/commit/d615bea596ba1c437fa612d47dd5022184d0e139

Before this commit, we had some technical debt where we did not properly implement areas of the Settings as well as some code smells that the marker found and told us in the comments. This commit cleaned up those issues, and properly finalized the implementation of the settings area. The debt, being a Inadvertant and Prudent debt, should have been avoided in the first place however there was oversight and lack of proper planning with finishing the feature entirely by the end of the second iteration. Along with this commit, we also implemented the clearing of areas in the database that was in the settings as well. The update also came from Issue #59 as well.

What technical debt did you leave?
==================================

A technical debt that we could not fix, a Prudent and Deliberate debt: A class that we could not move to its proper area. "WeightLog.java" is supposed to be in java->presentation, however the uses of some static classes and imported classes could not be properly used for the functions of WeightLog when moving the class to its proper directory. Instead it was left in the "java" directory. 

Discuss a Feature or User Story that was cut/re-prioritized
============================================

When did you change the priority of a Feature or User Story? Why was it
re-prioritized? Provide a link to the Feature or User Story. This can be from any
iteration.

Having gone through each feature, we did not re-prioritize any features. Any 'cut' features or user stories were added to a "Future" milestone. A close instance could be issue, #56 Profile Page, where we combined the issue with, #51 Settings Page. 

A feature that we edited/changed, #3 Create meals & implementation, was originally meant for iteration 2, however we were forced to push to iteration 3 as the gui was finished but the implementation was not. We logged the change and reasoning in gitlab, and edited the feature to properly show our current position.

Acceptance test/end-to-end
==========================

An end-to-end test written was the complete creation of a Workout, which can be found in the createCompleteMultiExerciseWorkout() test in the file: https://code.cs.umanitoba.ca/winter-2022-a01/group-12/fitness-tracker/-/blob/main/app/src/androidTest/java/com/example/fitness_tracker/WorkoutAcceptanceTests.java.

The test starts from Login, and goes all the way to submitting the Workout. So it tests typing in a username and password, then clicking the associated buttons to navigate through Home page > Workout Home > Workout Creation. Then in the workout creation page, both a strenght and cardio exercise are created, then submitted.

In order to make this test not flaky, I added 'closeSoftKeyboard()' so after the user types in, the keyboard isnt in the way of the submit button, which was causing tests to not work, so closing the keyboard makes sure the button can be pressed and the keyboard wont get in the way. 

Acceptance test, untestable
===============

Testing the back buttons couldnt be done, it seemed to have been due to their small size and the simulation just couldnt pick them up or due to some back buttons being in the snackbar then the back button had continued usage throughout the application leaving the simulation confused to which back button to find. Testing the Settings area could not be done as properly as we had hoped, we spent many hours finding an implementation for PreferenceScreen classes with espresso, there seems to be no real method, instead we used the espresso test recorder to implement the needed methods as unfortunately there is no real method of testing Preference Fragments.

Velocity/teamwork
=================
SEE THE WEBSITE FOR THE TOTAL VELOCITY!

During this iteration, we went through a few hiccups with real life activities and having to move a issue from the second iteration
to the third iteration. Our time estimates actually lined up, but like the first iteration we actually had quite a few differences in our estimates vs time spent but it just luckily lined up. Our main outliers were [#3 Create meals & implementation] and [#31 Fitness history on past days] and [#26 Fasting mode]. As you can tell we overestimated and underestimated much like the first iteration, but we place the blame on outside factors affecting this iteration. We are very confident that every member of the group is able to properly estimate their workload as we implemented some effective tools such as putting in research before estimating and discussing our estimates as a team.
