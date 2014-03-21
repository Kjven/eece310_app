package com.example.tidegrab;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.util.Log;

import com.jjoe64.graphview.GraphView.GraphViewData;

//Stores the set of tide heights, associated with a particular date and station
public class tideDataSet {
	private GraphViewData[] graphData;
	private String stationTitle;
	private Calendar dataDate; //
	
	public tideDataSet(GraphViewData[] graphViewData, String title, String date){
		Log.d("tideData", "Entered tideDataSet constructor");
		
		dataDate = new GregorianCalendar();
		
		setgraphData(graphViewData);
		setstationTitle(title);
		setDate(date);
	}
	
	public GraphViewData[] getData(){
		return graphData;
	}
	
	public String getTitle(){
		return stationTitle;
	}
	
	public Calendar getDate(){
		return dataDate;
	}
	
	//Parses a '/' delimited String, and converts it to a Calendar object
	public void setDate(String dateString){
		Log.d("tideData", "tideDataSet: setDate entered");
		
		String[] date = new String[3];
		date = dateString.split("/");
		dataDate.set(Integer.parseInt(date[0]), (Integer.parseInt(date[1])) - 1, Integer.parseInt(date[2]));
		
		Log.d("tideData", "tideDataSet: setDate exited");
	}
	
	public void setgraphData(GraphViewData[] graphViewData){
		Log.d("tideData", "tideDataSet: setGraphData entered");
		this.graphData = graphViewData.clone();
		Log.d("tideData", "tideDataSet: setGraphData exited");

	}
	
	public void setstationTitle(String title){
		Log.d("tideData", "tideDataSet: setstationTitle entered");
		this.stationTitle = title;
		Log.d("tideData", "tideDataSet: setGraphData exited");

	}
}
