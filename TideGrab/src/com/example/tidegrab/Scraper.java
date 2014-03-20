package com.example.tidegrab;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

public class Scraper {
	
	private String url;
	private ProgressDialog mProgressDialog;
	private TideApplication tideApp;
	private GraphView graph;
	private GraphViewSeries series;
	
	//List for scraped data storage
    ArrayList<GraphViewData> tideData;
	
	TideInfo tideInfo;
	
	public Scraper(TideApplication tideApplication){
		 tideInfo = new TideInfo();
		 tideData = new ArrayList<GraphViewData>();
		 this.tideApp = tideApplication;
	}
	
	//Updates the GraphView of the Activity currently running
	public void updateTideGraph(){
		this.graph = tideApp.getGraph();

		tideApp.getActivity().runOnUiThread(new Runnable() {
		    public void run() {
		    	graph.removeAllSeries();
				series = createSeries();
		        graph.addSeries(series);
		        graph.setViewPort(25, 25);
		        graph.setScrollable(true);
		        graph.setScalable(true);
		    }
		});		
	}
	
	//Converts the list of GraphViewData objects into a single GraphViewSeries object
	GraphViewSeries createSeries(){ 
		return new GraphViewSeries("Tide Heights", null, tideData.toArray(new GraphViewData[tideData.size()]));
	}
	
	//Handles the extraction of data from the internet. Invokes methods to update the Graph View.
    class TideInfo extends AsyncTask<String, Void, GraphViewSeries>{
        String info;
    	Elements rows;

        @Override
        protected void onPreExecute() {
        	Log.d("Gbug", "AsyncTask called");
        	super.onPreExecute();
            mProgressDialog = new ProgressDialog(tideApp.getActivity());
            mProgressDialog.setTitle("Retreiving Tide Information");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
            Log.d("Gbug", "preExecute finished");
        }
 
        @Override
        protected GraphViewSeries doInBackground(String... params) {
        	int iteration = 0;
            try {
            	Log.d("Gbug", "GraphView doInBackground started");
                // Connect to the web site
            	url = "http://www.waterlevels.gc.ca/eng/station?sid=" + params[0];
            	Document doc = Jsoup.connect(url).get();
            	tideData.clear();
            	
            	for (Element table : doc.select("table[title=Predicted Hourly Heights (m)]")) {
                    for (Element row : table.select("tr")) {
                    	info = "";
                                    	
                    	//Grabbing the heights
                        Elements tds = row.select("td");
                        if(tds.size() > 1){
                        	for( int hour = 0; hour < tds.size(); hour++){
                        		
                        		String text = tds.get(hour).text();
                        		
                        		info += Integer.toString(hour); //Outputting the associated hour as well
                        		info += ": " + text;
                        		info += "  ,  ";
                        		
                        		//Currently Only Graphing Data for the first available date
                        		if(iteration == 0)
                        			tideData.add(new GraphViewData(hour, Float.parseFloat(text)));
                        		
                        	}
                        		Log.d("Scraped", info);
                        		info += "\n";
                        }
                        iteration++;
                    }
                }
            	
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(GraphViewSeries result) {
            mProgressDialog.dismiss();
            updateTideGraph();
        }
    }
       
}
