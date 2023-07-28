package com.example.fitness_tracker.business;

public class UpdateDailyGoal {

    public UpdateDailyGoal()
    {

    }

    /**
     *
     * @param currentProgress the current value of the progress bar
     * @param addVal the value we need to add to the progress bar
     * @return
     */
    public int addMealToProgress(int currentProgress, int addVal)
    {
        int result = 0;
        float calories;
        //the current progress comes in as a percentage, it needs to be converted to calories
        calories = (currentProgress*1200)/100;

        //add the calories from the meal to the current calories
        calories += addVal;

        //convert back to percentage
        result = (int) ((calories*100)/1200);

        return result;
    }

    /**
     *
     * @param currentProgress the current value of the progress bar
     * @param addVal the value we need to remove to the progress bar
     * @return
     */
    public int removeCaloriesFromProgress(int currentProgress, int addVal)
    {
        int result = 0;
        float calories;
        //the current progress comes in as a percentage, it needs to be converted to calories
        calories = (currentProgress*1200)/100;

        //add the calories from the meal to the current calories
        calories -= addVal;

        //convert back to percentage
        result = (int) ((calories*100)/1200);

        return result;
    }
}
