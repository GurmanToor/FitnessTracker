package com.example.fitness_tracker.business;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateDistanceTest {

    @Test
    public void testValidDistance(){
        System.out.println("Testing valid input distance");
        // Test the constructor with valid input
        ValidateDistance vDistance = new ValidateDistance("10","km");
        System.out.println(vDistance.getReturnMessage());
        assertTrue("".equals(vDistance.getReturnMessage()));
        assertTrue(vDistance.getIsValid());
        assertTrue(vDistance.checkDistanceValid());
    }

    @Test
    public void testDistanceTooBig(){
        System.out.println("Testing valid input distance");
        // Test the constructor with valid input
        ValidateDistance vDistance = new ValidateDistance("10000","km");
        System.out.println(vDistance.getReturnMessage());
        assertTrue("Improper Distance Formatting".equals(vDistance.getReturnMessage()));
        assertFalse(vDistance.getIsValid());
        assertFalse(vDistance.checkDistanceValid());
    }

    @Test
    public void testCharactersInDistanceInput(){
        System.out.println("Testing no distance input");
        // Test the constructor with valid input
        ValidateDistance vDistance = new ValidateDistance("0-9","km");
        System.out.println(vDistance.getReturnMessage());
        assertTrue("Improper Distance Formatting".equals(vDistance.getReturnMessage()));
        assertFalse(vDistance.getIsValid());
        assertFalse(vDistance.checkDistanceValid());
    }

    @Test
    public void testNoDistanceInput(){
        System.out.println("Testing no distance input");
        // Test the constructor with valid input
        ValidateDistance vDistance = new ValidateDistance("","km");
        System.out.println(vDistance.getReturnMessage());
        assertTrue("Improper Distance Formatting".equals(vDistance.getReturnMessage()));
        assertFalse(vDistance.getIsValid());
        assertFalse(vDistance.checkDistanceValid());
    }

}