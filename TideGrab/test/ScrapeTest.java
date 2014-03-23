/**
 * @author Gavin
 */
import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import android.app.Activity;
import android.util.Log;

import com.android.tidegrab.MainActivity;
import com.android.tidegrab.Scraper;
import com.android.tidegrab.TideApplication;
import com.android.tidegrab.superActivity;
import com.android.tidegrab.Scraper.TideInfo;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LineGraphView;


public class ScrapeTest {

	
	/**
	 * If the same URL is requested twice, the same data should be returned from extractHeight
	 * (We test a subset of the data)
	 
	@Test
	public void testExtractHeightConsistency() {
		Document doc = null;
		Document doc2 = null;
		String result1;
		String result2;
		
		Activity testActivity = new Activity();
		GraphView testGraph = new LineGraphView(testActivity, "Tide Heights");
		
		TideApplication tideApplicationTest = (TideApplication) testActivity.getApplication();
		tideApplicationTest.currentActivity = testActivity;
		tideApplicationTest.currentGraph = testGraph;
		Scraper testScrape = new Scraper(tideApplicationTest);
		TideInfo tideInfo = testScrape.get_tideInfo();
		
		try {
			doc = Jsoup.connect("http://www.waterlevels.gc.ca/eng/station?sid=7795").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc2 = Jsoup.connect("http://www.waterlevels.gc.ca/eng/station?sid=7795").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Extracting Height");
		result1 = tideInfo.extractHeight(doc);
		System.out.println(result1);
		result2 = tideInfo.extractHeight(doc2);
		
		assertTrue(result1.equals(result2));
	}
	*/
	
	/**
	 * If a query is made to the the Government of Canada's waterlevels, the resulting document should never be null
	 */
	@Test
	public void testDocumentNotNull(){
		Document doc = null;
		Document doc2 = null;
				
		try {
			doc = Jsoup.connect("http://www.waterlevels.gc.ca/eng/station?sid=7795").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc2 = Jsoup.connect("http://www.waterlevels.gc.ca/eng/station?sid=7795").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(doc != null);
		assertTrue(doc2 != null);
	}

}
