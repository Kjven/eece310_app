package com.example.tidegrab;

import android.app.Activity;

public abstract class superActivity extends Activity{
	TideApplication tideApp;
	void setActivity(Activity current){
		tideApp = (TideApplication)getApplication();
		tideApp.currentActivity = this;
	}
	
	
	
	//Return the activities from application class
	
}
