package com.android.tidegrab;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.MenuItem;
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
	
	public class MyLocationListener implements LocationListener {
		//Get GPS coordinates and allow sort by distance
		@Override
		public void onLocationChanged(Location loc) {
			MainActivity.latitude = loc.getLatitude();
			MainActivity.longitude = loc.getLongitude();
			MainActivity.showGPS();
			MainActivity.enableSortDistance = 1;
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