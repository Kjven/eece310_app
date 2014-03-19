package com.example.tidegrab;

import com.jjoe64.graphview.GraphView;

import android.app.Activity;
import android.app.Application;

public class TideApplication extends Application {
	public Activity currentActivity;
	public GraphView currentGraph;
		
		public TideApplication(){
			super();
		}
		@Override
		public void onCreate(){
			super.onCreate();
		}
		
		Activity getActivity(){
			return currentActivity;
		}
		
		GraphView getGraph(){
			return currentGraph;
		}
}
