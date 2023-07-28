package com.example.fitness_tracker.business;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateStrengthUserInputTest {


    @Test
    public void validStrengthInput(){
        System.out.println("Testing an valid input");

        ValidateStrengthUserInput vStrength = new ValidateStrengthUserInput("Forearms","Sit ups","20/10/2022","22:10:10","11","1");
        assertTrue(vStrength.isValidInput);
        assertEquals(vStrength.getReturnMessage(),"");
    }

    @Test
    public void invalidTimeStrengthInput(){
        System.out.println("Testing an invalid body part");

        ValidateStrengthUserInput vStrength = new ValidateStrengthUserInput("","Sit ups","20/10/2022","22:10:10","11","1");
        assertFalse(vStrength.isValidInput);
        assertEquals(vStrength.getReturnMessage(),"Choose Body Part");
    }

    @Test
    public void invalidDateStrengthInput(){
        System.out.println("Testing invalid date input");

        ValidateStrengthUserInput vStrength = new ValidateStrengthUserInput("Forearms","Sit ups","202/10/2022","22:10:10","11","1");
        assertFalse(vStrength.isValidInput);
        assertEquals(vStrength.getReturnMessage(),"Improper Date Formatting");
    }

    @Test
    public void invalidExerciseNameStrengthInput(){
        System.out.println("Testing invalid exercise name input");

        ValidateStrengthUserInput vStrength = new ValidateStrengthUserInput("Forearms","","20/10/2022","22:10:10","11","1");
        assertFalse(vStrength.isValidInput);
        assertEquals(vStrength.getReturnMessage(),"Input Valid Exercise Name");
    }

    @Test
    public void invalidTimeInput(){
        System.out.println("Testing invalid time input");

        ValidateStrengthUserInput vStrength = new ValidateStrengthUserInput("Forearms","Curls","20/10/2022","32:10:10","11","1");
        assertFalse(vStrength.isValidInput);
        assertEquals(vStrength.getReturnMessage(),"Improper Time Formatting");
    }

    @Test
    public void invalidSetsInput(){
        System.out.println("Testing invalid sets input");

        ValidateStrengthUserInput vStrength = new ValidateStrengthUserInput("Forearms","Curls","20/10/2022","10:10:10","","1");
        assertFalse(vStrength.isValidInput);
        assertEquals(vStrength.getReturnMessage(),"Invalid Sets Input");
    }

    @Test
    public void invalidRepsInput(){
        System.out.println("Testing invalid reps input");

        ValidateStrengthUserInput vStrength = new ValidateStrengthUserInput("Forearms","Curls","20/10/2022","10:10:10","11","");
        assertFalse(vStrength.isValidInput);
        assertEquals(vStrength.getReturnMessage(),"Invalid Reps Input");
    }

}