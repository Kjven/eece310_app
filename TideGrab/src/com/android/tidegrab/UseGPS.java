package com.android.tidegrab;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UseGPS extends superActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		//Get new GPS
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
		
		//button to display the list view
		Button gps_button = (Button) findViewById(R.id.displayListView);

		// Capture the list view display button
		gps_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view0) {
            	
            	Intent intent = new Intent(UseGPS.this, OrganizeList.class);
            	Bundle extras = new Bundle();
            	extras.putString("xcor", "50");
            	extras.putString("ycor", "120");
            	intent.putExtras(extras);
            	startActivityForResult(intent,0);
                finish();
            }
        });
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