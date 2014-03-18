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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;



public class MainActivity extends Activity {
	
	//Spinner
	Spinner spinner;
	
	//Row elements
	Elements rows;
	
	// URL Address
    String url = "";
    ProgressDialog mProgressDialog;
    
    //List for scraped data storage
    ArrayList<String> tideTuples = new ArrayList<String>();
    ArrayList<GraphViewData> tideData = new ArrayList<GraphViewData>();
    
    //ListView to display scraped data
    private ListView listview;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
         
        ((LinearLayout) findViewById(R.id.graph1)).addView(createGraphView("Tide Heights"));
		
		Button titlebutton = (Button)findViewById(R.id.titlebutton);
		
		//Setting up the Spinner
		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.stations_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		
		// Capture button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
            	TextView sidInput = (TextView)findViewById(R.id.sidInput);
            	url = "http://www.waterlevels.gc.ca/eng/station?sid=" + sidInput.getText().toString();
            	tideTuples.clear();
                new TideInfo().execute();
            }
        });
	}
	
	//Creates an example sine series displayed on the provided graph.
	private void createExampleSineSeries(GraphView graph){
		// sin curve
		int num = 150;
		GraphViewData[] data = new GraphViewData[num];
		double v=0;
		for (int i=0; i<num; i++) {
		   v += 0.2;
		   data[i] = new GraphViewData(i, Math.sin(v));
		}
		GraphViewSeries seriesSin = new GraphViewSeries("Sine Curve", null, data);
		graph.addSeries(seriesSin);

        graph.setViewPort(25, 25);
        graph.setScrollable(true);
        graph.setScalable(true);
	}
	
	private GraphView createGraphView(String title){
		    GraphView graphView = new LineGraphView(this, "Tide Hights");
	        createExampleSineSeries(graphView);
	        return graphView;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 // Title AsyncTask
    private class TideInfo extends AsyncTask<Void, Void, Void> {
        String title;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Retreiving Tide Information");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
            	Document doc = Jsoup.connect(url).get();
            	int hour = 0;
            	
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
                        		
                        		String text = tds.get(i).text();
                        		
                        		title += Integer.toString(i); //Outputting the associated hour as well
                        		title += ": " + text;
                        		title += "  ,  ";
                        		
                        		//need a unimodally increasing hour.
                        		tideData.add(new GraphViewData(hour++, Float.parseFloat(text)));
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
            //TextView txttitle = (TextView) findViewById(R.id.titletext);
            //txttitle.setText(title);
        	
            //listview = (ListView) findViewById(R.id.listView1);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, tideTuples);
            //listview.setAdapter(adapter);
            
            //Update the graph
            GraphView graph = (GraphView) ((LinearLayout) findViewById(R.id.graph1)).getChildAt(0);
            graph.removeAllSeries();
            GraphViewData[] data = new GraphViewData[tideData.size()];
            data = tideData.toArray(data);
            graph.addSeries(new GraphViewSeries("Tide Data", null, data));
            graph.setViewPort(0, 23);
            
            mProgressDialog.dismiss();
        }
    }
}
