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

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

public class Scraper {
	private String url;
	private ProgressDialog mProgressDialog;
	private TideApplication tideApp;
	
	
	//List for scraped data storage
    ArrayList<String> tideTuples;
    ArrayList<GraphViewData> tideData;
	
	 TideInfo tideInfo;
	
	 public Scraper(TideApplication tideApplication){
		 tideInfo = new TideInfo();
		 tideTuples = new ArrayList<String>();
		 tideData = new ArrayList<GraphViewData>();
		 this.tideApp = tideApplication;
	 }
	
    class TideInfo extends AsyncTask<String, Void, GraphViewSeries> {
        String title;
    	Elements rows;

        @Override
        protected void onPreExecute() {
        	Log.d("Gbug", "AsyncTask called");
        	Log.d("Gbug", "Got applicationContext");
        	tideTuples.clear();
        	Log.d("Gbug", "Cleared tide tuples");
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
            try {
            	Log.d("Gbug", "GraphView doInBackground started");
                // Connect to the web site
            	url = "http://www.waterlevels.gc.ca/eng/station?sid=" + params[0];
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
                        		Log.d("Scraped", title);
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
        protected void onPostExecute(GraphViewSeries result) {
            mProgressDialog.dismiss();
        }
    }
}
