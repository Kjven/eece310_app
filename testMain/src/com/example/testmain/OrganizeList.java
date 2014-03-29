package com.example.testmain;

import org.apache.commons.lang3.ArrayUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class OrganizeList extends Activity {
	AlertDialog alertDialogStores;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        Bundle extras = getIntent().getExtras();
        String xco = extras.getString("xcor");
        String yco = extras.getString("ycor");
        
        Context context = getBaseContext();
        // just toast it
        Toast.makeText(context, "xco: " + xco + " yco: " + yco, Toast.LENGTH_SHORT).show();
        Log.d("tag","compare x: " + xco);
    	Log.d("tag","compare y: " + yco);
        
        ObjectItem bogus1 = new ObjectItem(175, "Vancouver", 49, -123);
        ObjectItem bogus2 = new ObjectItem(175, "Watson", 51, -125);
        
        
       
        double xcodub = Double.parseDouble(xco);
        double ycodub = Double.parseDouble(yco);
        
        ObjectItem shortestObject = compare(bogus1, bogus2,xcodub, ycodub);
        Log.d("tag",shortestObject.cityName);
        
        showPopUp(xcodub,ycodub);
    }
	
	public void showPopUp(double xco, double yco){
        // add your items, this can be done programatically
        // your items can be from a database
		ObjectItem[] ObjectItemData = new ObjectItem[5];
        ObjectItemData[0] = new ObjectItem(175, "Vancouver", 49, -123);
        ObjectItemData[1] = new ObjectItem(175, "Watson", 51, -125);
        ObjectItemData[2] = new ObjectItem(175, "Nissan", 53, -127);
        ObjectItemData[3] = new ObjectItem(175, "Puregold", 55, -129);
        ObjectItemData[4] = new ObjectItem(175, "SM", 57, -131);
        
        ObjectItem[] NewObjectItemData = new ObjectItem[5];
        
//        test = ArrayUtils.remove(test, 2); //removing element at index 2
        ObjectItem shortestObject;
        ObjectItem previousObject;
        int objectRemoved=0;
        
        //this should potential go up to objectitemdata size
        for(int i=0;i < 5;i++){
        	Log.d("tag","i1: " + String.valueOf(i));
        	shortestObject = ObjectItemData[0];
        	
        	objectRemoved =0;
        	
        	if (ObjectItemData.length == 1){
        		NewObjectItemData[i] = ObjectItemData[objectRemoved];
            	Log.d("tag","size of ObjectItemData: " + String.valueOf(ObjectItemData.length));
            	Log.d("tag","stored city: " + ObjectItemData[objectRemoved].cityName);
            	ObjectItemData = ArrayUtils.remove(ObjectItemData, objectRemoved);
            	Log.d("tag","size of ObjectItemData: " + String.valueOf(ObjectItemData.length));
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
	        	Log.d("tag","size of ObjectItemData: " + String.valueOf(ObjectItemData.length));
	        	Log.d("tag","stored city: " + ObjectItemData[objectRemoved].cityName);
	        	ObjectItemData = ArrayUtils.remove(ObjectItemData, objectRemoved);
        	}
//        	NewObjectItemData[i] = ObjectItemData[objectRemoved];
//        	Log.d("tag","size of ObjectItemData: " + String.valueOf(ObjectItemData.length));
//        	Log.d("tag","stored city: " + ObjectItemData[objectRemoved].cityName);
//        	ObjectItemData = ArrayUtils.remove(ObjectItemData, objectRemoved);
        	
//        	//if only one item left, add it to the array
//        	if (ObjectItemData.length == 1){
//        		NewObjectItemData[i] = ObjectItemData[objectRemoved];
//            	Log.d("tag","size of ObjectItemData: " + String.valueOf(ObjectItemData.length));
//            	Log.d("tag","stored city: " + ObjectItemData[objectRemoved].cityName);
//            	ObjectItemData = ArrayUtils.remove(ObjectItemData, objectRemoved);
//            	Log.d("tag","size of ObjectItemData: " + String.valueOf(ObjectItemData.length));
//        	} 
        	Log.d("tag","stored city in New: " + NewObjectItemData[i].cityName);
        	Log.d("tag","size of NewObjectItemData: " + String.valueOf(NewObjectItemData.length));
        	Log.d("tag","i2: " + String.valueOf(i));
        }
        
        Log.d("tag","out of big for loop");
        
        for(int i=0;i < NewObjectItemData.length ;i++){
        	Log.d("tag","in small for loop");
        	String temp = NewObjectItemData[i].cityName;
        	Log.d("tag","NEW: " + temp);
        }
        
        //our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, NewObjectItemData);
        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = new ListView(this);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new OnItemClickListenerListViewItem());
        // put the ListView in the pop up
        alertDialogStores = new AlertDialog.Builder(OrganizeList.this)
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

//    	Log.d("tag",a.cityName);
//    	Log.d("tag",b.cityName);
    	
    	double Ahypotenuse;
    	double Bhypotenuse;
    	
    	Ahypotenuse = Math.sqrt(Math.pow(2,Axcoord-xco) + Math.pow(2,Aycoord-yco));
    	Bhypotenuse = Math.sqrt(Math.pow(2,Bxcoord-xco) + Math.pow(2,Bycoord-yco));
    	
    	if (Math.abs(Ahypotenuse) > Math.abs(Bhypotenuse))
    		return b;
    	else
    		return a;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handles the home/back button press
		switch (item.getItemId()) {
        case android.R.id.home:
        	Intent intent = new Intent(OrganizeList.this, MainActivity.class);
        	startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
	}
}
