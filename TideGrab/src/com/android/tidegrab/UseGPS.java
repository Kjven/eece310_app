package com.android.tidegrab;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class UseGPS extends superActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		//Display previously known location
		Location old_loc = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); 
		EditText t = (EditText) findViewById(R.id.prev_loc);
		t.setText("Latitude = " + old_loc.getLatitude() + "\n"
				+ "Longitude = " + old_loc.getLongitude());
		
		//Get new GPS
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
	}
	
	public class MyLocationListener implements LocationListener {
	
		//Get current latitude and longitude and display in text box
		@Override
		public void onLocationChanged(Location loc) {
			String Text = "Latitude = " + loc.getLatitude() + "\n"
					+ "Longitude = " + loc.getLongitude();	
				
			EditText et = (EditText) findViewById(R.id.gps_loc);
			et.setText(Text);
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

}