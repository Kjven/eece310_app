package com.example.tidegrab;

import java.io.Serializable;

import android.content.Context;

import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class TideGraphView extends LineGraphView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1096377759357548033L;


	public TideGraphView(Context context, String title){
	    super(context, title);
	    // TODO Auto-generated constructor stub
    }
	
	
	public void UpdateGraph(final GraphViewSeries series){
		removeAllSeries();
        addSeries(series);
        
        //Set windowing
        setViewPort(25, 25);
        setScrollable(true);
        setScalable(true);
	}
	
	static public class GraphViewData implements GraphViewDataInterface, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4831840753410431851L;
		public final double valueX;
		public final double valueY;
		public GraphViewData(double valueX, double valueY) {
			super();
			this.valueX = valueX;
			this.valueY = valueY;
		}
		@Override
		public double getX() {
			return valueX;
		}
		@Override
		public double getY() {
			return valueY;
		}
	}
}
