package com.example.tidegrab;

import java.io.IOException;

import org.jsoup.Jsoup;

import android.os.AsyncTask;
import android.os.Bundle;
import org.jsoup.nodes.Document;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends Activity {
	
	// URL Address
    String url = "http://www.androidbegin.com";
    ProgressDialog mProgressDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button titlebutton = (Button)findViewById(R.id.titlebutton);
		
		// Capture button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
                new Title().execute();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Get the html document title
                title = document.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            TextView txttitle = (TextView) findViewById(R.id.titletext);
            txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }
}
