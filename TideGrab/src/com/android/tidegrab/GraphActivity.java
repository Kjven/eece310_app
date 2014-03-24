package com.android.tidegrab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;


public class GraphActivity extends superActivity {
	private String sid = null;
	Scraper tideScrape;
	GraphView graph;
	Spinner spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
         
		//Create the graph
        graph = new TideGraphView(this, "Tide Heights");
        ((LinearLayout) findViewById(R.id.graph1)).addView(graph);
		
        Intent intent = getIntent();
        sid = intent.getStringExtra(MainActivity.stationID);
        Log.d("tag", sid);
        Log.d("hello","testing4");
        
        Button titlebutton = (Button)findViewById(R.id.titlebutton);
     // Capture button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
            	
            	tideScrape = new Scraper(tideApp);
                tideScrape.tideInfo.execute(sid);
                
            }
        });
  
        //Set the current Activity and GraphView in the TideApplication 
        setActivity(this);
        setGraph(graph);
      
	}
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		switch (item.getItemId()) {
//        case android.R.id.home:
//            NavUtils.navigateUpFromSameTask(this);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//	}
		 
	
}
