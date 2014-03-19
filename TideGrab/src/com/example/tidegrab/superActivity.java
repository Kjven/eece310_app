package com.example.tidegrab;

import com.jjoe64.graphview.GraphView;

import android.app.Activity;

public abstract class superActivity extends Activity{
	TideApplication tideApp;
	
	void setActivity(Activity current){
		tideApp = (TideApplication)getApplication();
		tideApp.currentActivity = current;
	}
	
	void setGraph(GraphView currentGraph){
		tideApp = (TideApplication)getApplication();
		tideApp.currentGraph = currentGraph;
	}
	
	
	//Return the activities from application class
	
}
