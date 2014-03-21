/**
 * @author Gavin
 */
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

//Contains methods and classes required to scrape tide data, and update Graph Views
public class Scraper {
	
	private String url;
	private ProgressDialog mProgressDialog;
	private TideApplication tideApp;
	private GraphView graph;
	
	TideInfo tideInfo;
	
	//Default constructor, useful for JUnit
	public Scraper(){
		
	}
	
	public Scraper(TideApplication tideApplication){
		 tideInfo = new TideInfo();
		 this.tideApp = tideApplication;
	}
	
	public TideInfo get_tideInfo(){
		return tideInfo;
	}
	
	//Updates the GraphView of the Activity currently running
	public void updateTideGraph(final GraphViewSeries series){
		this.graph = tideApp.getGraph();

		tideApp.getActivity().runOnUiThread(new Runnable() {
		    public void run() {
		    	((TideGraphView) graph).UpdateGraph(series);
		    	
		    }
		});		
	}
	
	
	//Handles the extraction of data from the internet. Invokes methods to update the Graph View.
    public class TideInfo extends AsyncTask<String, Void, GraphViewSeries>{
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
        	GraphViewSeries result = null;
            try {
            	Log.d("Gbug", "GraphView doInBackground started");
            	url = "http://www.waterlevels.gc.ca/eng/station?sid=" + params[0];
            	Document doc = Jsoup.connect(url).get();
            	Log.d("Gbug", "Going to extract heights");
            	GraphViewData[] tideHeights = extractTideHeight(doc)[0];   
            	Log.d("Gbug", "Tide Heights extracted");
            	String graphTitle = extractStationName(doc);
            	result = new GraphViewSeries(graphTitle, null, tideHeights);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

		@Override
        protected void onPostExecute(GraphViewSeries result) {
            mProgressDialog.dismiss();
            updateTideGraph(result);
        }
        
        //Extracts Height data from an HTML document. Returns a graph view series.
        public GraphViewData[][] extractTideHeight(Document doc){
        	Log.d("Gbug", "Entered extractTideHeight");
        	int rownum = 0; //Debugging variable
        	
        	ArrayList<GraphViewData[]> tideDataList = new ArrayList<GraphViewData[]>();
        	ArrayList<GraphViewData> tideData = new ArrayList<GraphViewData>();
        	String dataDate = null;
        	
        	Elements tds = null;
        	
        	for (Element table : doc.select("table[title=Predicted Hourly Heights (m)]")) {
                for (Element row : table.select("tr")) {
                	info = "";
                    Log.d("Scraped", "Row #: " + Integer.toString(rownum)); 
                    
                	//Grabbing the heights
                    tds = null;
                    tds = row.select("td");
                    Log.d("Scraped", "Row size: " + Integer.toString(tds.size()));
                    if(tds.size() > 1){
                    	for (Element th: row.select("th")){
                    		dataDate = th.text();
                    	}
                    	Log.d("Scraped", "tds.size meets threshold");
                    	for( int hour = 0; hour < tds.size(); hour++){	
                    		String text = tds.get(hour).text();
                    		
                    		info += Integer.toString(hour); //Outputting the associated hour as well
                    		info += ": " + text;
                    		info += "  ,  ";
                    		
                    		//Currently Only Graphing Data for the first available date
                    			tideData.add(new GraphViewData(hour, Float.parseFloat(text)));
                    	}
                    		Log.d("Scraped", "Td Size: " + Integer.toString(tds.size()));
                    		Log.d("Scraped", "Date: " + dataDate);
                    		Log.d("Scraped", info);
                    		info += "\n";
                    	Log.d("Gbug", "Entering tideDataList element");
                    	
                    	tideDataList.add(tideData.toArray(new GraphViewData[tideData.size()]));
                    	tideData.clear();
                    }
                    rownum++;  
                }	
        	}
        	
        	Log.d("Gbug", "Returning tideDataList array");
        	return tideDataList.toArray(new GraphViewData[tideDataList.size()][]);
        }
        
        //Extracts the Station Name from the Document
        private String extractStationName(Document doc) {
        	String stationTitle = null;
        	for (Element h1 : doc.select("h1[class=background-accent font-xlarge")) {
        		stationTitle = h1.text();
        	}
        	Log.d("Gbug", "Station Title: " + stationTitle);
	        return stationTitle;
        }
    } 
}
