package com.example.testmain;

import org.apache.commons.lang3.ArrayUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
       
        double xcodub = Double.parseDouble(xco);
        double ycodub = Double.parseDouble(yco);
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
        for(int i=0;i <5;i++){
        	shortestObject = ObjectItemData[objectRemoved];
        	for(int j = 0;j < ObjectItemData.length-1; j++){
        		previousObject = shortestObject;
        		shortestObject = compare(shortestObject, ObjectItemData[j+1],xco, yco);
        		if (previousObject != shortestObject){
        			objectRemoved = j+1;
        		}
        			
            }
        	NewObjectItemData[i] = ObjectItemData[objectRemoved];
        	ObjectItemData = ArrayUtils.remove(ObjectItemData, objectRemoved);
        }
        
        for(int i=0;i <5;i++){
        	String temp = NewObjectItemData[i].cityName;
        	Log.d("tag",temp);
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
            .setTitle("Stores")
            .show();
    }
	
	public ObjectItem compare(ObjectItem a, ObjectItem b, double xco, double yco) {
    	double Axcoord = a.xCo;
    	double Aycoord = a.yCo;
    	double Bxcoord = b.xCo;
    	double Bycoord = b.yCo;
//    	double refxcoord = reference.xCo;
//    	double refycoord = reference.yCo;
    	
    	double Ahypotenuse;
    	double Bhypotenuse;
    	
    	Ahypotenuse = Math.sqrt(Math.pow(2,xco+Axcoord) + Math.pow(2,yco + Aycoord));
    	Bhypotenuse = Math.sqrt(Math.pow(2,xco+Bxcoord) + Math.pow(2,yco + Bycoord));
    	
    	if (Math.abs(Ahypotenuse) > Math.abs(Bhypotenuse))
    		return b;
    	else
    		return a;
    }
}
