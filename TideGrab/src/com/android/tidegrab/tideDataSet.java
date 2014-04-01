package com.android.tidegrab;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.util.Log;

//import com.jjoe64.graphview.GraphView.GraphViewData;

//Stores the set of tide heights, associated with a particular date and station
public class tideDataSet implements Serializable {
	
	private static final long serialVersionUID = -8629703701445663529L;
	private TideGraphView.GraphViewData[] graphData;
	private String sid;
	private Calendar dataDate; //
	
	public tideDataSet(TideGraphView.GraphViewData[] graphViewData, String sid, String date){
		Log.d("tideData", "Entered tideDataSet constructor");
		
		dataDate = new GregorianCalendar();
		
		setgraphData(graphViewData);
		setsid(sid);
		setDate(date);
	}
	
	public tideDataSet(TideGraphView.GraphViewData[] graphViewData, String sid, Calendar date){
		Log.d("tideData", "Entered tideDataSet constructor");
		
		dataDate = new GregorianCalendar();
		
		setgraphData(graphViewData);
		setsid(sid);
		
		dataDate = (Calendar) date.clone();
		
	}
	
	public TideGraphView.GraphViewData[] getData(){
		return graphData.clone();
	}
	
	public String getTitle(){
		return new String(sid);
	}
	
	public Calendar getDate(){
		//return dataDate;
		return (Calendar) dataDate.clone();
	}
	
	//Parses a '/' delimited String, and converts it to a Calendar object
	public void setDate(String dateString){
		Log.d("tideData", "tideDataSet: setDate entered");
		
		String[] date = new String[3];
		date = dateString.split("/");
		dataDate.set(Integer.parseInt(date[0]), (Integer.parseInt(date[1])) - 1, Integer.parseInt(date[2]));
		dataDate.clear(Calendar.HOUR);
		dataDate.clear(Calendar.MINUTE);
		dataDate.clear(Calendar.SECOND);
		dataDate.clear(Calendar.MILLISECOND);
		Log.d("tideData", "tideDataSet: setDate exited");
	}
	
	public void setgraphData(TideGraphView.GraphViewData[] graphViewData){
		Log.d("tideData", "tideDataSet: setGraphData entered");
		this.graphData = graphViewData.clone();
		Log.d("tideData", "tideDataSet: setGraphData exited");

	}
	
	public void setsid(String sid){
		Log.d("tideData", "tideDataSet: setstationTitle entered");
		this.sid = sid;
		Log.d("tideData", "tideDataSet: setGraphData exited");

	}
}
