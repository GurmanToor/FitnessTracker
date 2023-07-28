package com.example.fitness_tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitness_tracker.R;
import com.example.fitness_tracker.business.WeightLogger;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.presentation.AvatarSelector;
import com.example.fitness_tracker.presentation.HomeActivity;
import com.example.fitness_tracker.presentation.SettingsActivity;
import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * WeightLog
 *
 * the controller and gui for the weightlog activity
 */
public class WeightLog extends AppCompatActivity {


    /**
     * formatter class
     */
    private SimpleDateFormat formatter;

    /**
     * Weight logging logic class
     */
    private WeightLogger weightLogger;

    /**
     * UI COMPONENTS!
     */
    Button saveButton;
    Button addButton ;
    Button analysisButton;
    TextView weightLabel;
    TextView dateLabel;
    TextView inputWeight;
    TextView inputDate ;
    TextView metrix;
    TextView analysisText;
    ImageView calender;
    GraphView weightGraph;
    Viewport vp;


    /**
     * onCreate
     *
     * Links the buttons and opens the activity
     * @param savedInstanceState this
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.fitness_tracker.R.layout.weight_logging_page);

        
        saveButton= findViewById(com.example.fitness_tracker.R.id.save_button);
        addButton =  findViewById(com.example.fitness_tracker.R.id.addWeight_button);
        analysisButton = findViewById(com.example.fitness_tracker.R.id.analysis_button);
        weightLabel = findViewById(com.example.fitness_tracker.R.id.weight_label);
        dateLabel = findViewById(com.example.fitness_tracker.R.id.date_label);
        inputWeight= findViewById(com.example.fitness_tracker.R.id.input_weight);
        inputDate = findViewById(com.example.fitness_tracker.R.id.input_date);
        metrix= findViewById(com.example.fitness_tracker.R.id.metrix);
        analysisText = findViewById(com.example.fitness_tracker.R.id.analysis_text);
        calender = findViewById(com.example.fitness_tracker.R.id.weight_log_calender);

        weightLogger = new WeightLogger();


        hideComponents();
        //handle hide button clicked
        addWeight();
        //load up the graph
        weightGraph = findViewById(R.id.weight_graph);

        plotGraph(weightGraph);
        updateGraph();
        //handle save button  clicked
        saveInput();
        //handle analyse button clicked
        analyseHistory();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Weight Log");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * onOptionsItemSelected
     *
     * If the back button is selected!
     * @param item the back button
     * @return if it was successful or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(WeightLog.this, HomeActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * CONSTRUCTOR
     *
     * for default vals
     */
    public WeightLog() {

    }

    /**
     * CONSTRUCTOR
     * @param weight the given weight
     * @param date the given date
     */
    public WeightLog(Weight weight,String date) {

        try{
            formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date d1 = formatter.parse(date);
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("There is an issue with the date");
        }

    }





    /**
     * hideComponents
     *
     * hides all buttons and labels
     */
    public void hideComponents() {
        //hide input details and only show the graph


        saveButton.setVisibility(View.INVISIBLE);
        weightLabel.setVisibility(View.INVISIBLE);
        dateLabel.setVisibility(View.INVISIBLE);
        inputWeight.setVisibility(View.INVISIBLE);
        inputDate.setVisibility(View.INVISIBLE);
        metrix.setVisibility(View.INVISIBLE);
        analysisText.setVisibility(View.INVISIBLE);
        calender.setVisibility(View.INVISIBLE);

    }

    /**
     * addWeight
     *
     * adds the weight from the button
     */
    public void addWeight() {
        addButton.setOnClickListener(view -> {
            //show input details and only show the graph
            saveButton.setVisibility(View.VISIBLE);
            weightLabel.setVisibility(View.VISIBLE);
            dateLabel.setVisibility(View.VISIBLE);
            inputWeight.setVisibility(View.VISIBLE);
            inputDate.setVisibility(View.VISIBLE);
            metrix.setVisibility(View.VISIBLE);
            analysisText.setVisibility(View.INVISIBLE);
            calender.setVisibility(View.VISIBLE);
            formatter = new SimpleDateFormat("yyyy/MM/dd");
            inputDate.setText(formatter.format(new Date()));


        });

    }

    /**
     * saveInput
     *
     * saves the input fields
     */
    public void saveInput() {
        // collect the user input & UPDATE THE GRAPH
        saveButton.setOnClickListener(view -> {


            String newWeight = inputWeight.getText().toString(); // weight the user wants to log
            String newDate = inputDate.getText().toString();
            if(!newWeight.equals("") && !newDate.equals("")) {
                //clear input fields
                inputWeight.setText("");
                inputDate.setText("");
                // log the user inputs
                weightLogger.logWeight(newWeight ,newDate);
                hideComponents();
                //update the graph
                updateGraph();
                Snackbar.make(view, "Successfully added weight", Snackbar.LENGTH_SHORT).show();
            }
            else
            {
                Snackbar.make(view, "Enter your weight and date", Snackbar.LENGTH_SHORT).show();
            }


        });
    }

    /**
     * analysisHistory
     *
     * shows the history once button is clicked
     */
    public void analyseHistory() {
        analysisButton.setOnClickListener(view -> {

            hideComponents();
            analysisText.setVisibility(View.VISIBLE);
            analysisText.setText(weightLogger.analyseProgress());
        });

    }

    /**
     * plotGraph
     *
     * this function uses the points created to draw the graph
     * @param graph the current input graph
     */
    public void plotGraph(GraphView graph) {
        //design
        //First two lines change the grid line color
        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setHighlightZeroLines(false);
        //Below two lines change the label color
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().reloadStyles();

        buildX_axis(graph, "MM/dd", 7);
    }

    /**
     * updateGraph
     *
     * updates the graph when a new weight is logged
     */
    public void updateGraph(){
        LineGraphSeries<DataPoint> series;
        series = weightLogger.createDataPoints();
        weightGraph.addSeries(series);
    }

    /**
     * buildX_axis
     *
     * set the x-axis to show the date in a given date format, and show a certain range of x values
     * @param graph the graph
     * @param format the format needed
     * @param range the range of the graph
     */
    public static void buildX_axis(GraphView graph, String format,int range) {
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            final SimpleDateFormat sdf = new SimpleDateFormat(format);

            @Override
            public String formatLabel(double value, boolean isValueX) {
                String form;
                if(isValueX) {
                    form = sdf.format(new Date((long)value));
                }
                else {
                    form= super.formatLabel(value, isValueX);
                }
                return form;
            }
        });
        graph.getGridLabelRenderer().setNumHorizontalLabels(range);
        GridLabelRenderer renderer = graph.getGridLabelRenderer();
        //renderer.setHorizontalLabelsAngle(325);
        graph.getGridLabelRenderer().setTextSize(30f);
        graph.getGridLabelRenderer().reloadStyles();
        graph.getGridLabelRenderer().setLabelsSpace(10);
    }
}
