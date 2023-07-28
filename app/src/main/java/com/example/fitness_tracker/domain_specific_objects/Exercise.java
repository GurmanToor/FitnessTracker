package com.example.fitness_tracker.domain_specific_objects;

import java.io.Serializable;
import java.util.Objects;

public abstract class Exercise implements Serializable {

    private final String name;
    private final int exerciseID;
    private final String bodyPart;
    private final String type;
    private final String length;

    public Exercise(final int exerciseID) {
        this.exerciseID = exerciseID;
        name = null;
        bodyPart = null;
        type = null;
        length = null;
    }

    public Exercise(final int newID, final String newName, final String newBodyPart, final String newType,final String newLength) {
        exerciseID = newID;
        name = newName;
        bodyPart = newBodyPart;
        type = newType;
        length = newLength;
    }

    /**
     * standard toString
     */
    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", exerciseID='" + exerciseID + '\'' +
                ", bodyPart='" + bodyPart + '\'' +
                ", type='" + type + '\'' +
                ", time='" + length + '\'' +
                '}';
    }

    /**
     * equals
     *
     * checks if the given object is equal to the current workout object
     * @param o the object
     * @return if it is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return exerciseID == exercise.getExerciseID();
    }

    public String getName() {
        return name;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public String getType() {
        return type;
    }

    public String getLength() {
        return length;
    }



    @Override
    public int hashCode() {
        return Objects.hash(exerciseID);
    }
}
