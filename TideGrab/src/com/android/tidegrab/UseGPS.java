package com.android.tidegrab;

<<<<<<< HEAD
import android.app.AlertDialog;
=======
import org.apache.commons.lang3.ArrayUtils;

import android.app.AlertDialog;
import android.content.Context;
>>>>>>> 6dd959fcc4fb7ffe20516c693f29a6d2fd53d56d
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.MenuItem;
<<<<<<< HEAD
import android.widget.Toast;

public class UseGPS extends MainActivity {
	AlertDialog alertDialogStores;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {}

	public ObjectItem compare(ObjectItem a, ObjectItem b, double xco, double yco) {
    	double Axcoord = a.xCo;
    	double Aycoord = a.yCo;
    	double Bxcoord = b.xCo;
    	double Bycoord = b.yCo;
    	
    	double Ahypotenuse;
    	double Bhypotenuse;
    	
    	Ahypotenuse = Math.sqrt(Math.pow(2,Axcoord-xco) + Math.pow(2,Aycoord-yco));
    	Bhypotenuse = Math.sqrt(Math.pow(2,Bxcoord-xco) + Math.pow(2,Bycoord-yco));
    	
    	if (Math.abs(Ahypotenuse) > Math.abs(Bhypotenuse))
    		return b;
    	else
    		return a;
    }
=======
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class UseGPS extends superActivity {
	AlertDialog alertDialogStores;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		//Get new GPS
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
		
//		// a button to show the pop up with a list view
//        View.OnClickListener handler = new View.OnClickListener(){
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.displayListView:
//                        showPopUp();
//                        break;
//                }
//            }
//        };
//        findViewById(R.id.displayListView).setOnClickListener(handler);
	}
>>>>>>> 6dd959fcc4fb7ffe20516c693f29a6d2fd53d56d
	
	public void showPopUp(double xco, double yco){
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
        
        ObjectItem[] NewObjectItemData = new ObjectItem[ObjectItemData.length];
        
        ObjectItem shortestObject;
        ObjectItem previousObject;
        int objectRemoved=0;
        
        for(int i=0;i < NewObjectItemData.length;i++){
        	shortestObject = ObjectItemData[0];
        	objectRemoved =0;
        	
        	//if there is only one item left to store, then store it
        	if (ObjectItemData.length == 1){
        		NewObjectItemData[i] = ObjectItemData[objectRemoved];
            	ObjectItemData = ArrayUtils.remove(ObjectItemData, objectRemoved);
        	}
        	else {
	        	//loop to check for the closest city
	        	for(int j = 0;j < ObjectItemData.length-1; j++){
	        		previousObject = shortestObject; //keep track of the current closest city
	        		shortestObject = compare(shortestObject, ObjectItemData[j+1],xco, yco);
	        		if (previousObject != shortestObject){//if the nearest city has been replaced then take note of which array item to delete later
	        			objectRemoved = j+1; 
	        		}
	        			
	            }
	        	NewObjectItemData[i] = ObjectItemData[objectRemoved];
	        	ObjectItemData = ArrayUtils.remove(ObjectItemData, objectRemoved);
        	}
        }
        
        //our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, NewObjectItemData);
        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = new ListView(this);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new OnItemClickListenerListViewItem());
        // put the ListView in the pop up
        alertDialogStores = new AlertDialog.Builder(UseGPS.this)
            .setView(listViewItems)
            .setTitle("Cities")
            .show();
    }
	
	//assuming that the earth is flat, compare() will calculate the closest point
	public ObjectItem compare(ObjectItem a, ObjectItem b, double xco, double yco) {
    	double Axcoord = a.xCo;
    	double Aycoord = a.yCo;
    	double Bxcoord = b.xCo;
    	double Bycoord = b.yCo;
    	
    	double Ahypotenuse;
    	double Bhypotenuse;
    	
    	Ahypotenuse = Math.sqrt(Math.pow(2,Axcoord-xco) + Math.pow(2,Aycoord-yco));
    	Bhypotenuse = Math.sqrt(Math.pow(2,Bxcoord-xco) + Math.pow(2,Bycoord-yco));
    	
    	if (Math.abs(Ahypotenuse) > Math.abs(Bhypotenuse))
    		return b;
    	else
    		return a;
    }
	
	public class MyLocationListener implements LocationListener {
		//Get GPS coordinates and allow sort by distance
		@Override
		public void onLocationChanged(Location loc) {
<<<<<<< HEAD
			MainActivity.latitude = loc.getLatitude();
			MainActivity.longitude = loc.getLongitude();
			MainActivity.showGPS();
			MainActivity.enableSortDistance = 1;
=======
			String Text = "Latitude = " + loc.getLatitude() + "\n"
					+ "Longitude = " + loc.getLongitude();	
				
			EditText et = (EditText) findViewById(R.id.gps_loc);
			et.setText(Text);
			showPopUp(loc.getLatitude(),loc.getLongitude());
>>>>>>> 6dd959fcc4fb7ffe20516c693f29a6d2fd53d56d
		}
		
		//Toast for when GPS is disabled
		@Override
		public void onProviderDisabled(String provider)	{
			Toast.makeText( getApplicationContext(),"Gps Disabled", Toast.LENGTH_SHORT ).show();
		}
		
		//Toast for when GPS is enabled
		@Override	
		public void onProviderEnabled(String provider) {
			Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)	{}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handles the home/back button press
		switch (item.getItemId()) {
        case android.R.id.home:
        	Intent intent = new Intent(UseGPS.this, MainActivity.class);
        	startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
	}
}