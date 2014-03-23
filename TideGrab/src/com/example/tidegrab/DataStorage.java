package com.example.tidegrab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;

import com.jjoe64.graphview.GraphView.GraphViewData;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

//Handles the storage of all data that needs to persist beyond the Application lifetime
//A list of all the data sets is stored into memory. 
//TODO: When app starts up, this data is retrieved. Whenever a scrape request is made, this data is updated then stored once more
//TODO: in superActivity must always load data into the static class
public class DataStorage implements Serializable{
	/**
	 * 
	 */
	public TideApplication tideApplication;
	private static final long serialVersionUID = 8968710302023087373L;
	//private String dataSets = "res/values/dataSets.txt";
	private String dataSets = "dataSets.ser";
	//TO-DO Make this non-static, move it to TideApplication, update DataStorage class to reflect this
	private static ArrayList<tideDataSet> dataSetList = new ArrayList<tideDataSet>();
	
	
	public DataStorage(TideApplication tideApp) throws StreamCorruptedException, ClassNotFoundException, IOException{
		Log.d("Gbug", "Retrieving Internal Memory");
		retrieveDataSet();
		tideApplication = tideApp;
		Log.d("Gbug", "Internal Memory Retrieved");
	}
	
	//Given a tideDataSet, either updates dataSetList with the new value of the tideDataSet, or adds the new tideDataSet to the list
	public void writeDataSet(tideDataSet newDataSet) throws IOException{
		Log.d("DataStorage", "writeDataSet entered");
		for(int i = 0; i < dataSetList.size(); i++)	{
			Log.d("DataStorage", "Loop iteration: " + Integer.toString(i));
			//Check if the Date and Station for the Data is the same
			if((newDataSet.getDate().equals(dataSetList.get(i).getDate())) && (newDataSet.getTitle().equals(dataSetList.get(i).getTitle())) ){
				Log.d("DataStorage", "Date and Title are equal");
				//if(newDataSet.getData().deepEquals(dataSetList.get(i).getData())){
				if(Arrays.deepEquals(newDataSet.getData(),dataSetList.get(i).getData())){
				//if(newDataSet.getData()[0].getX() == dataSetList.get(i).getData()[0].getX()){

					Log.d("DataStorage", "DataSets equivalent");
					return;
				}else{
					Log.d("DataStorage", "Updating DataSet");
					dataSetList.set(i, new tideDataSet(newDataSet.getData(), newDataSet.getTitle(), newDataSet.getDate()));
					//writeStorage(newDataSet);
					writeStorage(new tideDataSet(newDataSet.getData(), newDataSet.getTitle(), newDataSet.getDate()));
					return;
				}
			}	
		}
		Log.d("DataStorage", "After For Loop");
		dataSetList.add(newDataSet);
		Log.d("DataStorage", "dataSet added to list");
		writeStorage(newDataSet);
		Log.d("DataStorage", "Storage written, exiting writeDataSet");
	}
	
	//Retrieves the ArrayList data from memory
	@SuppressWarnings("unchecked")
	synchronized public void retrieveDataSet() throws StreamCorruptedException, IOException, ClassNotFoundException{
		File file = new File(dataSets);
		if(file.exists()){
			FileInputStream filein = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(filein);
			dataSetList = (ArrayList<tideDataSet>) in.readObject();
		}else{
			Log.d("Gbug", "File not found on disc");
			dataSetList = new ArrayList<tideDataSet>();
		}
	}
	
	//Write the dataSetList into memory
	synchronized public void writeStorage(tideDataSet test) throws IOException{
		Log.d("DataStorage", "Entered writeStorage");
		//File file = new File(dataSets);
		Log.d("DataStorage", "writeStorage: Above if statement");
		
		//File outFile = new File(Environment.getExternalStorageDirectory(), dataSets);
		Log.d("DataStorage", "writeStorage: Creating output stream");
		//FileOutputStream fileout = new FileOutputStream(file);
		FileOutputStream fileout = tideApplication.getApplicationContext().openFileOutput(dataSets, Context.MODE_PRIVATE);
		ObjectOutputStream out = new ObjectOutputStream(fileout);
		//ObjectOutput out = new ObjectOutputStream(new FileOutputStream(outFile));
		Log.d("DataStorage", "writeStorage: writing to memory");
		//out.writeObject((Object)dataSetList);
		Log.d("DataStorage", "OBJECT TITLE: " + test.getTitle());
		if(test.getData() == null){
			Log.d("DataStorage", "WTF THIS IS NULL");
		}
		out.writeObject(dataSetList);
		//out.writeObject(test.getData());
		//out.writeObject(new TideGraphView.GraphViewData(5, 10));
		Log.d("DataStorage", "Object written to disk");
		out.close();
		fileout.close();
		Log.d("DataStorage", "Exited writeStorage");
		//}
	}
	
}
