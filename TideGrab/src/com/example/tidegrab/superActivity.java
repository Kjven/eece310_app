package com.example.tidegrab;

import com.jjoe64.graphview.GraphView;

import android.app.Activity;

//Ensures that all Activities in the Application have the necessary methods to update TideApplication
public abstract class superActivity extends Activity{
	TideApplication tideApp;
	
	//Sets the global currentActivity to this current Activity
	void setActivity(Activity current){
		tideApp = (TideApplication)getApplication();
		tideApp.currentActivity = current;
	}
	
	//Sets the global currentGraph to this current Graph
	void setGraph(GraphView currentGraph){
		tideApp = (TideApplication)getApplication();
		tideApp.currentGraph = currentGraph;
	}
}
