package com.example.tidegrab;

import java.util.Calendar;

import com.jjoe64.graphview.GraphView.GraphViewData;

//Stores the set of tide heights, associated with a particular date and station
public class tideDataSet {
	private GraphViewData[][] graphData;
	private String stationTitle;
	private Calendar dataDate; //
	
	public tideDataSet(GraphViewData[][] graphViewData, String title, String date){
		setgraphData(graphViewData);
		setstationTitle(title);
		setDate(date);
	}
	
	public GraphViewData[][] getData(){
		return graphData;
	}
	
	public String getTitle(){
		return stationTitle;
	}
	
	public Calendar getDate(){
		return dataDate;
	}
	
	//Parses a '/' delimited String, and converts it to a date object
	public void setDate(String dateString){
		String[] date = new String[3];
		date = dateString.split("/");
		dataDate.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
	}
	
	public void setgraphData(GraphViewData[][] graphViewData){
		this.graphData = graphViewData;
	}
	
	public void setstationTitle(String title){
		this.stationTitle = title;
	}
}
