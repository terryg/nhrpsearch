package net.rideside;

import java.io.File;

import com.itasoftware.careers.nrhp.Property;
import com.itasoftware.careers.nrhp.SearchResult;

public class TestNrhpSearch {
	  /** Usage: java TestNrhpSearch [xml.gz file] */
	  public static void main(String[] args) throws Exception {
		  NrhpPropertyDatabase5 npd = new NrhpPropertyDatabase5();
		  
		  File file = new File(args[0]);
		  
		  npd.setMaxSearchResults(10);
		  npd.setOffset(0);
		  
		  long t0 = System.nanoTime();
		  
		  npd.initialize(file);

		  long t1 = System.nanoTime();
		                              
		  SearchResult result = npd.search("Milwaukee Road Depot");		  

		  long t2 = System.nanoTime();
		  
		  Property[] properties = result.getProperties();
		  for (int ii = 0; ii < properties.length; ii++) {
			  for (int jj = 0; jj < properties[ii].getNames().length; jj++) {
			    System.out.println(properties[ii].getNames()[jj]);
			  }
			  System.out.println(properties[ii].getAddress());			  
			  System.out.println(properties[ii].getCity() + ", " + properties[ii].getState());	
			  System.out.println("");
		  }
	  
		  System.out.println("Time to initialize: " + ((t1 - t0) / (60 * 1e9)) + "min");
		  System.out.println("Time to search: " + ((t2 - t1) / 1e6) + "ms");
		  System.out.println("Found properties: " + result.getTotalPropertyCount());
		  System.out.println("FINISHED");
	  }

}
