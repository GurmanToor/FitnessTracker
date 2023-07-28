package com.example.fitness_tracker.business;

import com.example.fitness_tracker.FakeDatabase;
import com.example.fitness_tracker.RealDatabase;
import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.UserWeight;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.hsqldb.PersistenceException;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * WeightLogger
 *
 * logs the users new weight
 */
public class WeightLogger {

    /**
     * the specifier
     */
    static int id = 1;//weight ids
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

    FakeDatabase fd;
    RealDatabase rd;

    /**
     * EMPTY CONSTRUCTOR
     *
     * tobe @deprecated
     */
    public WeightLogger() {
        rd = RealDatabase.getDatabaseInstance();
        //fd = FakeDatabase.getDatabaseInstance();
    }


    /**
     * logWeight
     * Database integrated
     * logs the users weight for the day and displays an updated graph
     * this function adds the weight a user inputs to their history and displays an updated version of the graph process
     * @param weight the new weight
     * @param date the new date
     * @return if it was added or not
     */
    public boolean logWeight(String weight, String date)
    {
        boolean updated = false;
        int id ;

        //convert the weight into big decimal
        float weightInFloat = Float.parseFloat(weight);
        BigDecimal weightInBigDecimal = new BigDecimal(weightInFloat);

        //get the current user
        User user = rd.getCurrentUser();

        //user weight cannot be zero or negative
        if(weightInBigDecimal.doubleValue() >0) {
            try {
                Date d1 = formatter.parse(date);

                //ensure the date is valid (users can't log future dates)
                if (d1 != null) {
                    if (d1.before(new Date())) {
                        //create a new userWeight

                        // the id of the new userWeight is the current size of the users weight table +1
                        id = rd.userWeightSize() + 1;
                        //create a new weight and add it to the weight table in the database
                        Weight newWeight = new Weight(id,weightInBigDecimal);
                        rd.insert(newWeight);

                        UserWeight newUserWeight = new UserWeight(user,newWeight,date, ""); //create user weight
                        //add to the database
                        rd.insert(newUserWeight);

                        updated = true;
                    } else {
                        System.out.println("This date is not valid");
                    }
                }

            } catch (ParseException e) {
                //e.printStackTrace();
                System.out.println("This date not readable valid");
            }
            catch (Exception e) {
                System.out.println(e);
                //System.out.println("This date is not valid");
            }
        }
        return updated;
    }

    /**
     * sortDate
     *Database integrated
     * sorts the dates in the given list with the weights
     */
    public List<UserWeight> sortDate(List<UserWeight> uwe) {
        UserWeight key;
        String date;
        String nextDate;
        int j;
        List<UserWeight> uwHistory = uwe;

        //to prevent an array out of bounds, make sure the user's weight is not empty
            if (!uwHistory.isEmpty()) {
//                System.out.println("This should print one user weight " + uwe);
                //*************insertion sort******************
                int length = uwHistory.size();
                for (int i = 1; i < length; ++i) {
                    key = uwHistory.get(i);
                    date = key.getDate();
                    j = i - 1;
                    nextDate = uwHistory.get(j).getDate();

                    while (j >= 0 && (nextDate.compareTo(date) > 0)) {
                        uwHistory.set(j + 1, uwHistory.get(j)); //swap user weights
                        j--;
                        nextDate = uwHistory.get(j).getDate();
                    }
                    uwHistory.set(j + 1, key);
                }
            }

            return uwHistory;
    }





    /**
     * calculateProcess
     * Database integrated
     * This function will calculate the difference between the first weight
     * logged by the user and the last weight logged by the user.
     *
     * @return the difference
     */
    public BigDecimal calculateProgress(List<UserWeight> uwe) {
        System.out.println("This should print one user weight " + uwe);
        BigDecimal weightLost = null;
        //sort the users weight, this allows us to get the earliest and latest logged weight
        List<UserWeight> uwHistory = sortDate(uwe);

        if(uwHistory.size() >1) {
            BigDecimal startWeight = uwHistory.get(0).getWeight().getWeight();
            BigDecimal lastWeight = uwHistory.get(uwHistory.size() - 1).getWeight().getWeight();
            weightLost = startWeight.subtract(lastWeight);
        }
        System.out.println(weightLost);
        return weightLost;
    }


    /**
     * analyseProgress
     * Database integrated
     * This function performs different analysis on the users weight history and returns the result of the analysis as a string
     * @return the result
     */
    public String analyseProgress() {
        String result = "You need to log in first";
        User user = rd.getCurrentUser();
        if(user != null) {
            List<UserWeight> uwHistory = rd.getUserWeight(user.getUsername());
            if(uwHistory != null) {
                if (uwHistory.size() > 2) {
                    BigDecimal progress = calculateProgress(uwHistory);

                    if (progress.doubleValue() > 0) {
                        result = "You lost " + progress.doubleValue()  + " lb.";
                        //rate of change
                    } else if (progress.doubleValue() < 0) {
                        result = "You gained " + (progress.doubleValue() * -1) + " lb.";
                    } else {
                        result = "there is no change in your condition";
                    }
                } else {
                    result = "Log your weight for 2 days to get analysis";
                }
            }
        }

        return result;
    }



    /**
     * createDataPoints
     * Database Integrated
     * creates  data points that is needed to populate a graph
     */
    public LineGraphSeries<DataPoint> createDataPoints(){
        //instead of passing two arraylist, pass the database, and query the database to get user weight weight
        Date parse_date;
        float xaxis ;
        float yaxis ;
        int n ;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        //get the current user
        User user = rd.getCurrentUser();
        if(user != null){
            //get the user's weight history
            List<UserWeight> uwHistory = rd.getUserWeight(user.getUsername());
            // ensure the user's weight isn't null
            if(uwHistory != null) {

                if (!uwHistory.isEmpty()) {
                    xaxis = -1;
                    yaxis = -1;
                    n = uwHistory.size();
                    //sort the list
                    uwHistory = sortDate(uwHistory);

                    for (int i = 0; i < n; i++) {
                        // get the x(date) and y(weight) values, then convert them to double
                        try {
                            yaxis = uwHistory.get(i).getWeight().getWeight().floatValue();//get weight
                            parse_date = formatter.parse(uwHistory.get(i).getDate()); //get date
                            xaxis = parse_date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        series.appendData(new DataPoint(xaxis, yaxis), true, 1000);
                    }


                } else {
                    System.out.println("There is a problem in the users weight record");
                }
            }
        }

        //weightGraph.addSeries(series);
        return series;
    }


    //*******************************************************************
    //                  OLD IMPLEMENTATIONS(NO DATABASE REQUIRED)
    //***************************************************************
    // leaving this in because the test was built on these implementation
    //will replace with integrated tests

    /**
     * analyseProgress
     *
     * This function performs different analysis on the users weight history and returns the result of the analysis as a string
     * @param weightList the weight list
     * @param dateList the date list
     * @return the result
     */
    public String analyseProgress(ArrayList<Weight> weightList , ArrayList<Date> dateList) {
        String result;

        if (weightList.size() >1) {
            BigDecimal progress = calculateProgress(weightList);

            if (progress.doubleValue() > 0) {
                result = "You lost " + (progress.doubleValue() * -1) + " lb.";
                //rate of change
            } else if (progress.doubleValue() < 0) {
                result = "You gained " + progress.doubleValue() + " lb.";
            } else {
                result = "there is no change in your condition";
            }
        }
        else {
            result ="Log your weight for 2 days to get analysis";
        }

        return result;
    }


    /**
     * calculateProcess
     *
     * This function will calculate the difference between the first weight
     * logged by the user and the last weight logged by the user.
     * @param weightList the weight list
     * @return the difference
     */
    public BigDecimal calculateProgress(ArrayList<Weight> weightList) {
        BigDecimal weightLost = null;

        if(weightList.size() >1) {

            BigDecimal startWeight = weightList.get(0).getWeight();
            BigDecimal lastWeight = weightList.get(weightList.size() - 1).getWeight();
            weightLost = startWeight.subtract(lastWeight);
        } return weightLost;
    }

    /**
     * logWeight
     *
     * logs the users weight for the day and displays an updated graph
     * this function adds the weight a user inputs to their history and displays an updated version of the graph process
     * @param weightList the users weight history
     * @param dateList the users date history
     * @param weight the new weight
     * @param date the new date
     * @return if it was added or not
     */
    public boolean logWeight(ArrayList<Weight> weightList, ArrayList<Date> dateList, String weight, String date)  {
        boolean updated = false;

        float weightInFloat = Float.parseFloat(weight);
        BigDecimal weightInBigDecimal = new BigDecimal(weightInFloat);
        //User user = fd.getCurrentUser();


        if(weightInBigDecimal.doubleValue() >0) {
            try {
                //do checks
                //UserWeight newUserWeight = new UserWeight(user,new Weight(id,weightInBigDecimal),date, "");
                //fd.insert(newUserWeight);
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date d1 = formatter.parse(date);
                //ensure the date is valid (users can't log future dates)

                if (d1 != null) {
                    if (d1.before(new Date())) {
                        dateList.add(d1);// add the date to the list
                        Weight w = new Weight(id, weightInBigDecimal);
                        weightList.add(w);//add the weight
                        if(dateList.size() >=2) {
                            sortDate(weightList,dateList);
                        }
                        updated = true;
                    } else {
                        System.out.println("This date is not valid--");
                    }
                }

            } catch (ParseException e) {
                //e.printStackTrace();
                System.out.println("This date is not valid");
            } catch (Exception e) {
                System.out.println(e);

            }
        }
        return updated;
    }

    /**
     * sortDate
     *
     * sorts the dates in the given list with the weights
     * @param weightList the list of weights
     * @param dateList the list of dates
     */
    public void sortDate(ArrayList<Weight> weightList, ArrayList<Date> dateList) {
        Date lastDay = dateList.get(dateList.size()-2);
        Date newDay = dateList.get(dateList.size()-1);
        Date key;
        Weight temp;
        int j;

        //check to see if the day the user is adding is before the last date in the array
        if(newDay.compareTo(lastDay) < 0) {
            //sort
            int length = dateList.size();
            for(int i =1 ; i < length; ++i) {
                key =dateList.get(i);
                temp = weightList.get(i);
                j = i-1;

                while (j >= 0 && (dateList.get(j).compareTo(key) > 0) ) {
                    dateList.set(j+1, dateList.get(j)); //swap date
                    weightList.set(j+1, weightList.get(j)); //swap weight
                    j--;
                }
                dateList.set(j+1, key);
                weightList.set(j+1, temp);
            }
        }
    }


    /**
     * createDataPoints
     *
     * creates  data points that is needed to populate a graph
     * @param weightList the given list
     * @param dateList the given list
     */
    public LineGraphSeries<DataPoint> createDataPoints(ArrayList<Weight> weightList, ArrayList<Date> dateList){
        //instead of passing two arraylist, pass the database, and query the database to get user weight weight

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        float xaxis = -1;
        float yaxis = -1;
        int n = weightList.size();

        for(int i=0;i<n;i++){
            // get the x and y values, then convert them to double
            if(weightList.get(i) != null && dateList.get(i) != null) {
                yaxis = weightList.get(i).getWeight().floatValue();
                xaxis = dateList.get(i).getTime();
            }
            else {
                System.out.println("There is a problem in the users weight record");
            }

            series.appendData(new DataPoint(xaxis,yaxis), true, 1000);
        }
        //weightGraph.addSeries(series);
        return series;
    }
}
