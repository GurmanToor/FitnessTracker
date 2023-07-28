package com.example.fitness_tracker.business;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateTimeLengthTest {

    @Test
    public void testValidTime(){
        System.out.println("Testing an valid time input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("02:10:11");
        assertTrue("".equals(vTime.getReturnMessage()));
        assertTrue(vTime.getIsValid());
        assertTrue(vTime.checkValidTime());
    }

    @Test
    public void testNoTimeInput(){
        System.out.println("Testing when no time is input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testSecondsTooHigh(){
        System.out.println("Testing an valid seconds value has been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("02:10:61");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testMinutesTooHigh(){
        System.out.println("Testing a valid minutes value has been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("02:80:30");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testHoursTooHigh(){
        System.out.println("Testing a valid hours value has been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("25:20:30");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testSecondsNotInput(){
        System.out.println("Testing when no seconds has been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("02:10:");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testMinutesNotInput(){
        System.out.println("Testing when no minutes has been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("02::30");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testHoursNotInput(){
        System.out.println("Testing when no hours has been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength(":20:30");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testTooManyDigitsForSecondsInput(){
        System.out.println("Testing when too many seconds digits have been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("02:10:000");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testTooManyDigitsForMinutesInput(){
        System.out.println("Testing when too many minutes digits have been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("02:000:30");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }

    @Test
    public void testTooManyDigitsForHoursInput(){
        System.out.println("Testing when too many hours digits have been input");
        // Test the constructor with valid input
        ValidateTimeLength vTime = new ValidateTimeLength("000:20:30");
        assertTrue("Improper Time Formatting".equals(vTime.getReturnMessage()));
        assertFalse(vTime.getIsValid());
        assertFalse(vTime.checkValidTime());
    }
}