package com.example.fitness_tracker.business;

import com.example.fitness_tracker.domain_specific_objects.Weight;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightLoggerTest_Junit4 {


    @Test
    public void logWeightTest() {
        testValidWeightLogging();
        testInvalidWeightLogging();
    }



    @Test
    public void sortDateTest() {
        ArrayList<Weight> weights = new ArrayList<>();
        ArrayList<Date> dates =  new ArrayList<>();
        WeightLogger weightLogger = new WeightLogger();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date d1;

        System.out.println("Testing date sorting");
        try {
            //adding a bunch of dates in random order
            d1 = sdf.parse("02/17/2022");
            dates.add(d1);
            weights.add(new Weight(1,new BigDecimal(180)));
            d1 = sdf.parse("02/18/2022");
            dates.add(d1);
            weights.add(new Weight(1,new BigDecimal(190)));
            d1 = sdf.parse("02/16/2022");
            dates.add(d1);
            weights.add(new Weight(1,new BigDecimal(200)));
            d1 = sdf.parse("02/27/2022");
            dates.add(d1);
            weights.add(new Weight(1,new BigDecimal(150)));
            d1 = sdf.parse("02/22/2022");
            dates.add(d1);
            weights.add(new Weight(1,new BigDecimal(160)));

            weightLogger.sortDate(weights,dates);
            //16th should be the lowest therefore
            assertEquals(sdf.format(dates.get(0)), "02/16/2022");
            assertEquals(weights.get(0).getWeight().floatValue(), 200.0);//the associated weight

            assertEquals(sdf.format(dates.get(1)), "02/17/2022");
            assertEquals(weights.get(1).getWeight().floatValue(), 180.0);//the associated weight

            assertEquals(sdf.format(dates.get(2)), "02/18/2022");
            assertEquals(weights.get(2).getWeight().floatValue(), 190.0);//the associated weight

            assertEquals(sdf.format(dates.get(3)), "02/22/2022");
            assertEquals(weights.get(3).getWeight().floatValue(), 160.0);//the associated weight

            //27th should be the highest therefore
            assertEquals(sdf.format(dates.get(dates.size()-1)), "02/27/2022");
            assertEquals(weights.get(dates.size()-1).getWeight().floatValue(), 150.0);//the associated weight


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void calculateProgressTest() {
        testValidCalculateProgress();
        testInvalidCalculateProgress();
    }

    @Test
    public void analyseProgress() {
        ArrayList<Weight> weights = new ArrayList<>();
        ArrayList<Date> dates =  new ArrayList<>();
        WeightLogger weightLogger = new WeightLogger();

        //"_____________Testing weight logging with invalid inputs________________");
        assertTrue(weightLogger.analyseProgress(weights,dates).equals("Log your weight for 2 days to get analysis")  );
    }


    public void testValidWeightLogging() {
        ArrayList<Weight> weights = new ArrayList<>();
        ArrayList<Date> dates =  new ArrayList<>();
        WeightLogger weightLogger = new WeightLogger();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        //Testing weight logging with valid inputs");
        //Added 150lb on 02/26/2022 to a users weight history ");
        Assertions.assertTrue(weightLogger.logWeight(weights,dates,"150", "02/26/2022"));
        assertEquals(weights.get(0).getWeight().floatValue(), 150.0);
        assertEquals(sdf.format(dates.get(0)), "02/26/2022");

        //Added 250lb on 02/28/2022 to a users weight history ");
        assertTrue(weightLogger.logWeight(weights,dates,"250", "02/28/2022"));
        assertEquals(weights.get(1).getWeight().floatValue(), 250.0);
        assertEquals(sdf.format(dates.get(1)), "02/28/2022");
    }


    public void testInvalidWeightLogging() {
        ArrayList<Weight> weights = new ArrayList<>();
        ArrayList<Date> dates =  new ArrayList<>();
        WeightLogger weightLogger = new WeightLogger();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        //_____________Testing weight logging with invalid inputs________________");
        //Trying to add -150lb on 02/26/2022 to a users weight history ");
        assertFalse(weightLogger.logWeight(weights,dates,"-150", "02/26/2022"));
        assertThrows(IndexOutOfBoundsException.class, ()-> weights.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()-> dates.get(0));

        //Trying to add 150lb on 12/19/2023 to a users weight history ");
        assertFalse(weightLogger.logWeight(weights,dates,"150", "12/19/2023"));
        assertThrows(IndexOutOfBoundsException.class, ()-> weights.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()-> dates.get(0));

        //Trying to add 150lb on 12/19/2023 to a users weight history ");
        assertFalse(weightLogger.logWeight(weights,dates,"150", "55/19/2022"));
        assertThrows(IndexOutOfBoundsException.class, ()-> weights.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()-> dates.get(0));

        //Trying to add -150lb on 12/19/2023 to a users weight history ");
        assertFalse(weightLogger.logWeight(weights,dates,"-150", "12/19/2023"));
        assertThrows(IndexOutOfBoundsException.class, ()-> weights.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()-> dates.get(0));

        //Trying to add valid weights on a valid date but wrong format to a users weight history ");
        assertFalse(weightLogger.logWeight(weights,dates,"-150", "02-26-2022"));
        assertThrows(IndexOutOfBoundsException.class, ()-> weights.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()-> dates.get(0));

        //Trying to add valid weights on a invalid date but wrong format to a users weight history
        assertFalse(weightLogger.logWeight(weights,dates,"-150", "03-26-2082"));
        assertThrows(IndexOutOfBoundsException.class, ()-> weights.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()-> dates.get(0));

        //passing unparsable date
        //assertFalse( weightLogger.logWeight(weights,dates,new BigDecimal(150), "kl-45=iopp"));
        //assertFalse( weightLogger.logWeight(weights,dates,new BigDecimal(150), "------"));



    }


    public void testInvalidCalculateProgress() {
        ArrayList<Weight> weights = new ArrayList<>();
        WeightLogger weightLogger = new WeightLogger();


        System.out.println("_____________Testing calculate progress with invalid inputs________________");
        System.out.println("Trying to perform calculations on an empty weight history. ");
        assertNull(weightLogger.calculateProgress(weights));

        System.out.println("Trying to perform calculations on a weight history of just one day.");
        weights.add(new Weight(1, new BigDecimal(200)));
        assertNull(weightLogger.calculateProgress(weights));

    }


    public void testValidCalculateProgress() {
        ArrayList<Weight> weights = new ArrayList<>();
        WeightLogger weightLogger = new WeightLogger();
        weights.add(new Weight(1, new BigDecimal(200)));
        weights.add(new Weight(2, new BigDecimal(190)));

        BigDecimal res = weightLogger.calculateProgress(weights);


        System.out.println("_____________Testing calculate progress with valid inputs________________");
        System.out.println("Trying to perform calculations weight history of 2 days. ");
        //weight loss
        assertTrue(res.floatValue() ==10.0);

        //weight gain
        weights.add(new Weight(3, new BigDecimal(210)));
        res = weightLogger.calculateProgress(weights);
        assertTrue(res.floatValue() == -10.0 );


    }
}