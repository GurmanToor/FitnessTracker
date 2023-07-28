package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.Workout;
import com.example.fitness_tracker.persistence.IWorkoutPersistence;

import java.util.Collections;
import java.util.List;

public class AccessWorkout
    {
        private IWorkoutPersistence workoutPersistence;
        private List<Workout> workouts;
        private Workout workout;
        private int currentWorkout;

    public AccessWorkout()
        {
            workoutPersistence = Services.getWorkoutPersistence();
            workouts = null;
            workout = null;
            currentWorkout = 0;
        }

    public AccessWorkout(final IWorkoutPersistence workoutPersistence) {
        this();
        this.workoutPersistence = workoutPersistence;
    }

        public List<Workout> getWorkouts()
        {
            workouts = workoutPersistence.getWorkoutSequential();
            return Collections.unmodifiableList(workouts);
        }

        public Workout getSequential()
        {
            if (workouts == null)
            {
                workouts = workoutPersistence.getWorkoutSequential();
                currentWorkout = 0;
            }
            if (currentWorkout < workouts.size())
            {
                workout = workouts.get(currentWorkout);
                currentWorkout++;
            }
            else
            {
                workouts = null;
                workout = null;
                currentWorkout = 0;
            }
            return workout;
        }

        public Workout getRandom(int workoutID)
        {
            workout = null;
            if (workoutID == 0)
            {
                //System.out.println("*** Invalid student number");
            }
            else
            {
                workouts = workoutPersistence.getWorkoutRandom(new Workout(workoutID));
                if (workouts.size()==1)
                {
                    workout = (Workout) workouts.get(0);
                }
            }
            return workout;
        }

        public Workout insertWorkout(Workout currentWorkout)
        {
            return workoutPersistence.insertWorkout(currentWorkout);
        }

        public Workout updateWorkout(Workout currentWorkout)
        {
            return workoutPersistence.updateWorkout(currentWorkout);
        }

        public void deleteWorkout(Workout currentWorkout)
        {
            workoutPersistence.deleteWorkout(currentWorkout);
        }

        public int workoutSize() {
        return workoutPersistence.workoutSize();
        }
}
