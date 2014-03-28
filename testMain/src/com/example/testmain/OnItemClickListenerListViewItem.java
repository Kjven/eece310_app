package com.example.testmain;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
 
/*
11
 * Here you can control what to do next when the user selects an item
12
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {
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
        ((MainActivity) context).alertDialogStores.cancel();
    } 
}
