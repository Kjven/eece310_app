package com.example.tidegrab;

import java.io.Serializable;

import android.content.Context;

import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class TideGraphView extends LineGraphView {

	public TideGraphView(Context context, String title){
	    super(context, title);
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
		
		@Override
		public boolean equals(Object other){
			if((this.getX() == ((GraphViewDataInterface) other).getX()) && this.getY() == (((GraphViewDataInterface) other).getY() )){
				return true;
			}else{
				return false;
			}	
		}
		
	}
}
