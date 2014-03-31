package com.android.tidegrab;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;


public class MainActivity extends superActivity {
	public final static String stationID = "sid";
	Spinner spinner;
	Scraper tideScrape;
	GraphView graph;
	
	//dont know what this is for
	AlertDialog alertDialogStores;
          
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
         
		//Button titlebutton = (Button)findViewById(R.id.titlebutton);
		
		//button to go to gps activity
		Button gps_button = (Button) findViewById(R.id.gps_button);

		// Capture gps button click
		gps_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view0) {
            	Intent gpsIntent = new Intent(MainActivity.this, UseGPS.class);
            	startActivityForResult(gpsIntent, 0);
                //finish();
                
            }
        });

		/*
		// Capture graph button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
            	TextView sidInput = (TextView)findViewById(R.id.sidInput);
            	String sid = sidInput.getText().toString();
            	
            	Intent graphIntent = new Intent(MainActivity.this, GraphActivity.class);
            	graphIntent.putExtra(stationID, sid);
            	startActivityForResult(graphIntent, 0);
                //finish();
                
            }
        });
        */
        showPopUp();
      
	}
	
	public void showPopUp(){
        // add your items, this can be done programatically
        // your items can be from a database
		ObjectItem[] ObjectItemData = new ObjectItem[15];
        ObjectItemData[0] = new ObjectItem(7480, "Boat Harbour", 49.09, 123.80);
        ObjectItemData[1] = new ObjectItem(9227, "Bonilla Island", 53.49, 130.64);
        ObjectItemData[2] = new ObjectItem(7535, "Dionisio Point", 49.01, 123.57);
        ObjectItemData[3] = new ObjectItem(7820, "Gibsons", 49.40, 123.51);
        ObjectItemData[4] = new ObjectItem(7830, "Halfmoon Bay", 49.51, 123.51);
        ObjectItemData[5] = new ObjectItem(7917, "Nanaimo", 49.17, 123.93);
        ObjectItemData[6] = new ObjectItem(7654, "New Westminster", 51, -125);
        ObjectItemData[7] = new ObjectItem(7795, "Point Atkinson", 53, -127);
        ObjectItemData[8] = new ObjectItem(7635, "Point Grey", 49.25, 123.27);
        ObjectItemData[9] = new ObjectItem(7852, "Porpoise Bay", 49.48, 123.76);
        ObjectItemData[10] = new ObjectItem(7755, "Port Moody", 49.29, 122.87);
        ObjectItemData[11] = new ObjectItem(7594, "Sand Heads", 49.13, 123.20);
        ObjectItemData[12] = new ObjectItem(7550, "Silva Bay", 49.15, 123.70);
        ObjectItemData[13] = new ObjectItem(7810, "Squamish", 49.68, 123.17);
        ObjectItemData[14] = new ObjectItem(7607, "Steveston", 49.13, 123.18);
        ObjectItemData[12] = new ObjectItem(7590, "Tsawwassen", 49.00, 123.13);
        ObjectItemData[13] = new ObjectItem(7735, "Vancouver", 55, -129);
        ObjectItemData[14] = new ObjectItem(7577, "White Rock", 49.02, 122.80);
        
        //our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, ObjectItemData);
        // create a new ListView, set the adapter and item click listener
        ListView list = (ListView) findViewById(R.id.listview2);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListenerListViewItem());
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		 
	
}
