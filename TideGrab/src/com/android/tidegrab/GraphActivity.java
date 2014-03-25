package com.android.tidegrab;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;


public class GraphActivity extends superActivity {
	private String sid = null;
	Scraper tideScrape;
	GraphView graph;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		
		//set up the back button in the action bar
		//getActionBar().setDisplayHomeAsUpEnabled(true);
         
		//Create the graph
        graph = new TideGraphView(this, "Tide Heights");
        ((LinearLayout) findViewById(R.id.graph1)).addView(graph);
		
        Intent intent = getIntent();
        sid = intent.getStringExtra(MainActivity.stationID);
    
  
        //Set the current Activity and GraphView in the TideApplication 
        setActivity(this);
        setGraph(graph);
      
        tideScrape = new Scraper(tideApp);
        tideScrape.tideInfo.execute(sid);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handles the home/back button press
		switch (item.getItemId()) {
        case android.R.id.home:
        	Intent intent = new Intent(GraphActivity.this, MainActivity.class);
        	startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
	}
		 
	
}
