package com.example.tidegrab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import android.os.AsyncTask;
import android.os.Bundle;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;



public class MainActivity extends Activity {
	
	
	//Row elements
	Elements rows;
	
	// URL Address
    String url = "http://www.waterlevels.gc.ca/eng/station?sid=7795";
    ProgressDialog mProgressDialog;
    
    //List for scraped data storage
    ArrayList<String> tideTuples = new ArrayList<String>();
    
    //ListView to display scraped data
    private ListView listview;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button titlebutton = (Button)findViewById(R.id.titlebutton);
		
		// Capture button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
                new Title().execute();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
            	Document doc = Jsoup.connect(url).get();
            	
            	for (Element table : doc.select("table[title=Predicted Hourly Heights (m)]")) {
                    for (Element row : table.select("tr")) {
                    	title = "";
                    	//Grabbing the hours
                    	if(row.hasClass("hourlyHeightsHeader2")){
                    		Elements ths = row.select("th[scope=col]");
                            if (ths.size() > 1) {
                            	for( int i = 0; i < ths.size(); i++){
                            		//title += "\n" + (tds.get(0).text() + "    :    " + tds.get(1).text());
                            		title += ths.get(i).text();
                            		title += "  :  ";
                            	}
                            		title += "\n";
                            }
                    		
                    	}
                    	
                    	//Grabbing the heights
                        Elements tds = row.select("td");
                        if (tds.size() > 1) {
                        	for( int i = 0; i < tds.size(); i++){
                        		//title += "\n" + (tds.get(0).text() + "    :    " + tds.get(1).text());
                        		title += tds.get(i).text();
                        		title += "  :  ";
                        		
                        	}
                        		tideTuples.add(title);
                        		title += "\n";
                        }
                    }
                }
            	
            	
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            TextView txttitle = (TextView) findViewById(R.id.titletext);
            txttitle.setText(title);
            listview = (ListView) findViewById(R.id.listView1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, tideTuples);
            listview.setAdapter(adapter);
            mProgressDialog.dismiss();
        }
    }
}
