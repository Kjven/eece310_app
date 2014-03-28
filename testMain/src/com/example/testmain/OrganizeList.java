package com.example.testmain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
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
        alertDialogStores = new AlertDialog.Builder(OrganizeList.this)
            .setView(listViewItems)
            .setTitle("Stores")
            .show();
    }
}
