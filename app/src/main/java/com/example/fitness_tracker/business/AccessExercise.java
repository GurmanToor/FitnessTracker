package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.Cardio;
import com.example.fitness_tracker.domain_specific_objects.Exercise;
import com.example.fitness_tracker.domain_specific_objects.Strength;
import com.example.fitness_tracker.persistence.IExercisePersistence;

import java.util.Collections;
import java.util.List;

public class AccessExercise {

        private IExercisePersistence exercisePersistence;
        private List<Exercise> exercises;
        private Exercise exercise;
        private int currentExercise;

    public AccessExercise()
        {
            exercisePersistence = Services.getExercisePersistence();
            exercises = null;
            exercise = null;
            currentExercise = 0;
        }

    public AccessExercise(final IExercisePersistence exercisePersistence) {
        this();
        this.exercisePersistence = exercisePersistence;
    }

        public List<Exercise> getExercises()
        {
            exercises = exercisePersistence.getExerciseSequential();
            return Collections.unmodifiableList(exercises);
        }

        public Exercise getSequential()
        {
            if (exercises == null)
            {
                exercises = exercisePersistence.getExerciseSequential();
                currentExercise = 0;
            }
            if (currentExercise < exercises.size())
            {
                exercise = exercises.get(currentExercise);
                currentExercise++;
            }
            else
            {
                exercises = null;
                exercise = null;
                currentExercise = 0;
            }
            return exercise;
        }

        public Exercise getRandom(int exerciseID)
        {
            exercise = null;
            if (exerciseID == 0)
            {
            }
            else
            {
                exercises = exercisePersistence.getExerciseRandom(new Strength(exerciseID));
                if (exercises.size()==1)
                {
                    exercise = (Exercise) exercises.get(0);
                }
            }
            return exercise;
        }

        public Exercise insertExercise(Exercise currentExercise)
        {
            return exercisePersistence.insertExercise(currentExercise);
        }

        public Exercise updateExercise(Exercise currentExercise)
        {
            return exercisePersistence.updateExercise(currentExercise);
        }

        public void deleteExercise(Exercise currentExercise)
        {
            exercisePersistence.deleteExercise(currentExercise);
        }

        public int exerciseSize() {
            return exercisePersistence.exerciseSize();
        }
}
