package com.example.tidegrab;

import android.app.Activity;
import android.app.Application;

public class TideApplication extends Application {
	public Activity currentActivity;
		
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
}
