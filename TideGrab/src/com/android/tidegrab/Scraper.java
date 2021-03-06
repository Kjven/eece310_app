/**
 * @author Gavin
 */
package com.android.tidegrab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

//Contains methods and classes required to scrape tide data, and update Graph Views
public class Scraper {
	
	private String url;
	private ProgressDialog mProgressDialog;
	private ProgressDialog mProgressDialog2;
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
    	String sid;

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
            
            	Log.d("Gbug", "GraphView doInBackground started");
            	url = "http://www.waterlevels.gc.ca/eng/station?sid=" + params[0];
            	sid = params[0];
            	Document doc = null;
            	try{
            		doc = Jsoup.connect(url).get();
            		try {
                    	Log.d("Gstorage", "Jsoup connected");            	
                    	ArrayList<tideDataSet> tideDataSetList = extractTideHeight(doc);
                    	Log.d("Gbug", "Tide Heights extracted");
                    	
                    	tideDataSet firstSet = tideDataSetList.get(0);
                    	
                    	for(tideDataSet Set : tideDataSetList){
                     		Log.d("Internal Call", "Calling writeDataSet on: " + Set.getTitle());
        	            	try {
         						tideApp.getStorage().writeDataSet(new tideDataSet(Set.getData(), Set.getTitle(), Set.getDate()));
         					}catch (ClassNotFoundException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					}
                    	}
                    	Log.d("Gbug", "Successfully Updated/Added internal memory");
                    	result = new GraphViewSeries(firstSet.getTitle(), null, firstSet.getData());
        	            }catch (IOException e) {
        	                e.printStackTrace();
        	           }
            	}catch(IOException e){
            		Log.d("Gstorage", "Caught connect exception");
            		
            		tideApp.getActivity().runOnUiThread(new Runnable() {
            		    public void run() {
            		    	Toast.makeText(tideApp.getApplicationContext(), "No Internet Connection Available. Attempting to use Data Cache",
                      			   Toast.LENGTH_LONG).show();	
            		    }
            		});
            		
            		Calendar currentDate = new GregorianCalendar();
            		currentDate = Calendar.getInstance();
            		tideDataSet firstSet = tideApp.getStorage().findData(sid, currentDate);
            		if(firstSet != null){
            			tideApp.getActivity().runOnUiThread(new Runnable() {
                		    public void run() {
                		    	Toast.makeText(tideApp.getApplicationContext(), "Displaying Cached Data",
                          			   Toast.LENGTH_LONG).show();	
                		    }
                		});
            			result = new GraphViewSeries(firstSet.getTitle(), null, firstSet.getData());
            		}
            		
            	}
            	
            return result;
        }

		@Override
        protected void onPostExecute(GraphViewSeries result) {
            mProgressDialog.dismiss();
            
            if(result != null){
            	updateTideGraph(result);
            }else{
            	//do something here
            	Toast.makeText(tideApp.getApplicationContext(), "No Cached Data Available",
            			   Toast.LENGTH_LONG).show();
            }
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
                    	
                    	//String stationTitle = extractStationName(doc);
                    	tideDataSet currentSet = new tideDataSet(tideData.toArray(new TideGraphView.GraphViewData[tideData.size()]), sid, dataDate);
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
