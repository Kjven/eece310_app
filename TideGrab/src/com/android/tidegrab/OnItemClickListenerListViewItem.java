package com.android.tidegrab;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
 
public class OnItemClickListenerListViewItem implements OnItemClickListener {
	public final static String stationID = "0";
	
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	Context context = view.getContext();
        TextView textViewItem = ((TextView) view.findViewById(R.id.textViewItem));
        // get the clicked item name
        String listItemText = textViewItem.getText().toString();
        
        // get the clicked item ID
        String listItemId = textViewItem.getTag().toString();
        String[] values = listItemId.split( "," );
        
        // just toast it
        Toast.makeText(context, "City: " + listItemText + ", Station ID: " + values[0]+ ", xco: " + values[1]+ ", yco: " + values[2], Toast.LENGTH_SHORT).show();
        //((MainActivity) context).alertDialogStores.cancel();
        
        Intent graphIntent = new Intent(context, GraphActivity.class);
    	graphIntent.putExtra(stationID, values[0]);
    	context.startActivity(graphIntent);
    } 
}
