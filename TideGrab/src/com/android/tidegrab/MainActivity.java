package com.android.tidegrab;

import android.os.Bundle;

import com.android.tidegrab.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends superActivity {
		
	Spinner spinner;
	Scraper tideScrape;
	GraphView graph;
          
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
         
		//Create the graph
        graph = new TideGraphView(this, "Tide Heights");
        ((LinearLayout) findViewById(R.id.graph1)).addView(graph);
		
		Button titlebutton = (Button)findViewById(R.id.titlebutton);
		
		//Setting up the Spinner
		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.stations_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		
		// Capture button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
            	TextView sidInput = (TextView)findViewById(R.id.sidInput);
            	String sid = sidInput.getText().toString();
            	
            	tideScrape = new Scraper(tideApp);
                tideScrape.tideInfo.execute(sid);
                
            }
        });
        //Set the current Activity and GraphView in the TideApplication 
        setActivity(this);
        setGraph(graph);
      
	}
	
	//Creates an example sine series displayed on the provided graph.
	private void createExampleSineSeries(GraphView graph){
		// sin curve
		int num = 150;
		GraphViewData[] data = new GraphViewData[num];
		double v=0;
		for (int i=0; i<num; i++) {
		   v += 0.2;
		   data[i] = new GraphViewData(i, Math.sin(v));
		}
		GraphViewSeries seriesSin = new GraphViewSeries("Sine Curve", null, data);
		graph.removeAllSeries();
		graph.addSeries(seriesSin);

        graph.setViewPort(25, 25);
        graph.setScrollable(true);
        graph.setScalable(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		 
	
}
