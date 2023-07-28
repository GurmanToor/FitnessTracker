package com.example.fitness_tracker.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValidateDateInputTest {

    @Test
    public void testValidDate(){
        System.out.println("Testing an valid day input");
        // Test the constructor with valid input
        ValidateDateInput vDate = new ValidateDateInput("22/02/2020");
        assertTrue("".equals(vDate.getReturnMessage()));
        assertTrue(vDate.getIsValid());
        assertTrue(vDate.validateDate());
    }

    @Test
    public void testInvalidDay(){
        System.out.println("Testing an invalid day input");

        // Test the constructor with invalid input
        ValidateDateInput vDate = new ValidateDateInput("32/10/2020");
        assertFalse("".equals(vDate.getReturnMessage()));
        assertFalse(vDate.getIsValid());
        assertFalse(vDate.validateDate());
    }

    @Test
    public void testTooManyDigitsInDay(){
        System.out.println("Testing an invalid day input");

        // Test the constructor with invalid input
        ValidateDateInput vDate = new ValidateDateInput("321/10/2020");
        assertFalse("".equals(vDate.getReturnMessage()));
        assertFalse(vDate.getIsValid());
        assertFalse(vDate.validateDate());
    }

    @Test
    public void testNoDigitsInDay(){
        System.out.println("Testing when no digits input for day");

        // Test the constructor with invalid input
        ValidateDateInput vDate = new ValidateDateInput("/10/2020");
        assertFalse("".equals(vDate.getReturnMessage()));
        assertFalse(vDate.getIsValid());
        assertFalse(vDate.validateDate());
    }

    @Test
    public void testInvalidMonth() {
        System.out.println("Testing when invalid month value is input");
        ValidateDateInput vDate = new ValidateDateInput("01/13/20202");

        assertFalse("".equals(vDate.getReturnMessage()));
        assertFalse(vDate.getIsValid());
        assertFalse(vDate.validateDate());
    }

    @Test
    public void testTooManyDigitsInMonth() {
        System.out.println("Testing when too many digits for month value is input");
        ValidateDateInput vDate = new ValidateDateInput("01/132/2020");

        assertFalse("".equals(vDate.getReturnMessage()));
        assertFalse(vDate.getIsValid());
        assertFalse(vDate.validateDate());
    }

    @Test
    public void testNoDigitsInMonth() {
        System.out.println("Testing when no digits for month value is input");
        ValidateDateInput vDate = new ValidateDateInput("01//2020");

        assertFalse("".equals(vDate.getReturnMessage()));
        assertFalse(vDate.getIsValid());
        assertFalse(vDate.validateDate());
    }

    @Test
    public void testTooManyDigitsInYear() {
        System.out.println("Testing when too many digits for year value is input");
        ValidateDateInput vDate = new ValidateDateInput("01/12/20202");
        assertFalse("".equals(vDate.getReturnMessage()));
        assertFalse(vDate.getIsValid());
        assertFalse(vDate.validateDate());
    }

    @Test
    public void testNoDigitsInYear() {
        System.out.println("Testing when no digits for month value is input");
        ValidateDateInput vDate = new ValidateDateInput("01/12/");
        assertFalse("".equals(vDate.getReturnMessage()));
        assertFalse(vDate.getIsValid());
        assertFalse(vDate.validateDate());
    }

}