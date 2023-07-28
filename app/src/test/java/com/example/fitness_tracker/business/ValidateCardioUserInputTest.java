package com.example.fitness_tracker.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValidateCardioUserInputTest {

    @Test
    public void validCardioInput(){
        System.out.println("Testing an valid input");

        ValidateCardioUserInput vCardio = new ValidateCardioUserInput("20/10/2022","22:10:10","11","mi","Walk");
        assertTrue(vCardio.isValidInput);
        assertEquals(vCardio.getReturnMessage(),"");
    }

    @Test
    public void invalidTimeCardioInput(){
        System.out.println("Testing invalid time input");

        ValidateCardioUserInput vCardio = new ValidateCardioUserInput("20/10/2022","22:10:101","11","mi","Walk");
        assertFalse(vCardio.isValidInput);
        assertEquals(vCardio.getReturnMessage(),"Improper Time Formatting");
    }

    @Test
    public void invalidDateCardioInput(){
        System.out.println("Testing invalid date input");

        ValidateCardioUserInput vCardio = new ValidateCardioUserInput("32/10/2022","22:10:10","11","mi","Walk");
        assertFalse(vCardio.isValidInput);
        assertEquals(vCardio.getReturnMessage(),"Improper Date Values");
    }

    @Test
    public void invalidDistanceCardioInput(){
        System.out.println("Testing invalid distance input");

        ValidateCardioUserInput vCardio = new ValidateCardioUserInput("30/10/2022","22:10:10","","mi","Walk");
        assertFalse(vCardio.isValidInput);
        assertEquals(vCardio.getReturnMessage(),"Improper Distance Formatting");
    }

    @Test
    public void invalidDistanceUnitsCardioInput(){
        System.out.println("Testing invalid distance unit input");

        ValidateCardioUserInput vCardio = new ValidateCardioUserInput("30/10/2022","22:10:10","11","m","Walk");
        assertFalse(vCardio.isValidInput);
        assertEquals(vCardio.getReturnMessage(),"Improper Distance Formatting");
    }

}