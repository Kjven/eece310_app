/**
 * @author Gavin
 */
package com.android.tidegrab;

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
	synchronized public void updateTideGraph(final GraphViewSeries series){
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
        protected void onPreExecute(){
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
            	ArrayList<tideDataSet> tideDataSetList = extractTideHeight(doc);
            	Log.d("Gbug", "Tide Heights extracted");
            	
            	tideDataSet firstSet = tideDataSetList.get(0);
            	
            	//Must check internal memory for this data, and add/update it accordingly
            	try {
					tideApp.getStorage().writeDataSet(new tideDataSet(firstSet.getData(), firstSet.getTitle(), firstSet.getDate()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	Log.d("Gbug", "Successfully Updated/Added internal memory");
            	result = new GraphViewSeries(firstSet.getTitle(), null, firstSet.getData());
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
        
        //Extracts Height data from an HTML document, converts information into tideDataSet objects
		public ArrayList<tideDataSet> extractTideHeight(Document doc){
        	Log.d("Gbug", "Entered extractTideHeight");
        	int rownum = 0; //Debugging variable
        	
        	
        	ArrayList<tideDataSet> tideDataSetList = new ArrayList<tideDataSet>();
        	ArrayList<TideGraphView.GraphViewData> tideData = new ArrayList<TideGraphView.GraphViewData>();
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
                    		
                    		//For Debug Logging
                    		info += Integer.toString(hour);
                    		info += ": " + text;
                    		info += "  ,  ";
                    		
                    		tideData.add(new TideGraphView.GraphViewData(hour, Float.parseFloat(text)));
                    	}
                		Log.d("Scraped", "Td Size: " + Integer.toString(tds.size()));
                		Log.d("Scraped", "Date: " + dataDate);
                		Log.d("Scraped", info);
                		info += "\n";
                    	Log.d("Gbug", "Entering tideDataList element");
                    	
                    	String stationTitle = extractStationName(doc);
                    	tideDataSet currentSet = new tideDataSet(tideData.toArray(new TideGraphView.GraphViewData[tideData.size()]), stationTitle, dataDate);
                    	tideDataSetList.add(currentSet);
                    	Log.d("Gbug", "Entered tideDataSetList element");
                    	tideData.clear();
                    
                    }
                    rownum++;  
                }	
        	}
        	
        	Log.d("Gbug", "Returning tideDataSetList");
        	return tideDataSetList;
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
