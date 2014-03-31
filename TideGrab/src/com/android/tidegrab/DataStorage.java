package com.android.tidegrab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;


import android.content.Context;
import android.util.Log;

//Handles the storage of all data that needs to persist beyond the Application lifetime
//A list of all the data sets is stored into memory. 
public class DataStorage{
	
	public TideApplication tideApplication;
	private String dataSets = "dataSets.ser";
	private static ArrayList<tideDataSet> dataSetList = new ArrayList<tideDataSet>();
	
	public DataStorage(TideApplication tideApp) throws StreamCorruptedException, ClassNotFoundException, IOException{
		Log.d("Gbug", "Retrieving Internal Memory");
		tideApplication = tideApp;
		retrieveDataSet();		
		Log.d("Gbug", "Internal Memory Retrieved");
	}
	
	//Given a tideDataSet, either updates dataSetList with the new value of the tideDataSet, or adds the new tideDataSet to the list
	public void writeDataSet(tideDataSet newDataSet) throws IOException, ClassNotFoundException{
		Log.d("DataStorage", "writeDataSet entered");
		for(int i = 0; i < dataSetList.size(); i++)	{
			Log.d("DataStorage", "Loop iteration: " + Integer.toString(i));
			//Check if the Date and Station for the Data is the same
			if((newDataSet.getDate().equals(dataSetList.get(i).getDate())) && (newDataSet.getTitle().equals(dataSetList.get(i).getTitle())) ){
				Log.d("DataStorage", "Date and Title are equal");
				if(Arrays.deepEquals(newDataSet.getData(),dataSetList.get(i).getData())){
					Log.d("DataStorage", "DataSets equivalent");
					return;
				}else{
					Log.d("DataStorage", "Updating DataSet");
					dataSetList.set(i, new tideDataSet(newDataSet.getData(), newDataSet.getTitle(), newDataSet.getDate()));
					writeStorage(new tideDataSet(newDataSet.getData(), newDataSet.getTitle(), newDataSet.getDate()));
					return;
				}
			}	
		}
		Log.d("DataStorage", "After For Loop");
		dataSetList.add(newDataSet);
		writeStorage(newDataSet);
		Log.d("DataStorage", "Storage written, exiting writeDataSet");
		retrieveDataSet();
	}
	
	//Retrieves the ArrayList data from memory
	@SuppressWarnings("unchecked")
	synchronized public void retrieveDataSet() throws StreamCorruptedException, IOException, ClassNotFoundException{
		
		try{
		FileInputStream filein = tideApplication.getApplicationContext().openFileInput(dataSets);
		ObjectInputStream in = new ObjectInputStream(filein);
		dataSetList = (ArrayList<tideDataSet>) in.readObject();
		in.close();
		filein.close();
		}catch(FileNotFoundException e){
		Log.d("Gbug", "File not found on disc2");
		dataSetList = new ArrayList<tideDataSet>();
		}
		
	}
	
	//Write the dataSetList into memory
	synchronized public void writeStorage(tideDataSet test) throws IOException{		
		Log.d("DataStorage", "writeStorage: Creating output stream");
		FileOutputStream fileout = tideApplication.getApplicationContext().openFileOutput(dataSets, Context.MODE_PRIVATE);
		ObjectOutputStream out = new ObjectOutputStream(fileout);
		Log.d("DataStorage", "writeStorage: writing to memory: " + test.getTitle());
		
		out.writeObject(dataSetList);
		Log.d("DataStorage", "Object written to disk");
		out.close();
		fileout.close();
		Log.d("DataStorage", "Exited writeStorage");
	}
	
}
