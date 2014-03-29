package com.example.testmain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	public final static String xcor = "0";
	public final static String ycor = "0";
	
    AlertDialog alertDialogStores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // a button to show the pop up with a list view
        View.OnClickListener handler = new View.OnClickListener(){
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.listviewbtn:
                        showPopUp();
                        break;
                }
            }
        };
        findViewById(R.id.listviewbtn).setOnClickListener(handler);
        
        
      //button to go to gps activity
		Button get_coords = (Button) findViewById(R.id.get_coords);
		findViewById(R.id.get_coords).setOnClickListener(handler);
		// Capture graph button click
		get_coords.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
            	TextView xcoord = (TextView)findViewById(R.id.xcoord);
            	String xco = xcoord.getText().toString();
            	
            	TextView ycoord = (TextView)findViewById(R.id.ycoord);
            	String yco = ycoord.getText().toString();
            	
            	Intent intent = new Intent(MainActivity.this, OrganizeList.class);
            	Bundle extras = new Bundle();
            	extras.putString("xcor", xco);
            	extras.putString("ycor", yco);
            	intent.putExtras(extras);
            	startActivityForResult(intent,0);
                finish();
                
            }
        });
    }
    public void showPopUp(){
        // add your items, this can be done programatically
        // your items can be from a database
        ObjectItem[] ObjectItemData = new ObjectItem[5];
        ObjectItemData[0] = new ObjectItem(175, "Vancouver", 49, -123);
        ObjectItemData[1] = new ObjectItem(175, "Watson", 51, -125);
        ObjectItemData[2] = new ObjectItem(175, "Nissan", 53, -127);
        ObjectItemData[3] = new ObjectItem(175, "Puregold", 55, -129);
        ObjectItemData[4] = new ObjectItem(175, "SM", 57, -131);
        //our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, ObjectItemData);
        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = new ListView(this);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new OnItemClickListenerListViewItem());
        // put the ListView in the pop up
        alertDialogStores = new AlertDialog.Builder(MainActivity.this)
            .setView(listViewItems)
            .setTitle("Stores")
            .show();
    }
}
