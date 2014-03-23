package com.android.tidegrab;

import android.content.Context;

import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class TideGraphView extends LineGraphView {
	
	public TideGraphView(Context context, String title) {
	    super(context, title);
	    // TODO Auto-generated constructor stub
    }
	
	
	public void UpdateGraph(final GraphViewSeries series)
	{
		removeAllSeries();
        addSeries(series);
        
        //Set windowing
        setViewPort(25, 25);
        setScrollable(true);
        setScalable(true);
	}
}
