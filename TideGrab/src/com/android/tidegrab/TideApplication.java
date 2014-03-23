package com.android.tidegrab;

import java.io.IOException;
import java.io.StreamCorruptedException;

import com.jjoe64.graphview.GraphView;

import android.app.Activity;
import android.app.Application;

//This Application class is used to store global information that must be accessed across multiple Activities
public class TideApplication extends Application {
	public Activity currentActivity;
	public GraphView currentGraph;
	public DataStorage internalData;
	
		public TideApplication(){
			super();
		}
		@Override
		public void onCreate(){
			super.onCreate();
			try {
				internalData = new DataStorage(this);
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public Activity getActivity(){
			return currentActivity;
		}
		
		public GraphView getGraph(){
			return currentGraph;
		}
		
		public DataStorage getStorage(){
			return internalData;
		}
}
