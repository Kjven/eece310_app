package com.example.tidegrab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

//Handles the storage of all data that needs to persist beyond the Application lifetime
//A list of all the data sets is stored into memory. 
//TODO: When app starts up, this data is retrieved. Whenever a scrape request is made, this data is updated then stored once more
//TODO: in superActivity must always load data into the static class
public class DataStorage {
	private String dataSets = "res/raw/dataSets.txt";
	
	//TO-DO Make this non-static, move it to TideApplication, update DataStorage class to reflect this
	private static ArrayList<tideDataSet> dataSetList = new ArrayList<tideDataSet>();
	
	//Given a tideDataSet, either updates dataSetList with the new value of the tideDataSet, or adds the new tideDataSet to the list
	public void writeDataSet(tideDataSet newDataSet) throws IOException{		
		for(int i = 0; i < dataSetList.size(); i++)	{
			//Check if the Date and Station for the Data is the same
			if((newDataSet.getDate().equals(dataSetList.get(i).getDate())) && (newDataSet.getTitle().equals(dataSetList.get(i).getTitle())) ){
				if(newDataSet.equals(dataSetList.get(i))){
					return;
				}else{
					dataSetList.set(i, new tideDataSet(newDataSet.getData(), newDataSet.getTitle(), newDataSet.getDate()));
					return;
				}
			}	
		}
		dataSetList.add(newDataSet);
		writeStorage();
	}
	
	//Retrieves the ArrayList data from memory
	@SuppressWarnings("unchecked")
	public void retrieveDataSet() throws StreamCorruptedException, IOException, ClassNotFoundException{
		File file = new File(dataSets);
		if(file.exists()){
			FileInputStream filein = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(filein);
			dataSetList = (ArrayList<tideDataSet>) in.readObject();
		}
	}
	
	//Write the dataSetList into memory
	public void writeStorage() throws IOException{
		File file = new File(dataSets);
		if(!file.exists()){
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		FileOutputStream fileout = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileout);
		out.writeObject(dataSetList);
		out.close();		
		}
	}
	
}
