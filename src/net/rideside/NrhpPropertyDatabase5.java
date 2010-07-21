package net.rideside;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.itasoftware.careers.nrhp.Property;
import com.itasoftware.careers.nrhp.PropertyDatabase;
import com.itasoftware.careers.nrhp.SearchResult;

public class NrhpPropertyDatabase5 implements PropertyDatabase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MAX_KEY_LENGTH = 9;
	private int _maxSearchResults = 10;	
	private int _offset = 0;
	private Dictionary _dict = new Dictionary();
    private Stack<String> _ids = new Stack<String>();
    
	
	public int getMaxSearchResults() {
		return _maxSearchResults;
	}
	
	public void setOffset(int offset) {
		_offset = offset;
	}
	
	public int getOffset() {
		return _offset;
	}
	
	public void initialize(File file) throws Exception {
   	 try {
		 // Open the gzip file
		 BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file))));

		 XMLInputFactory factory = XMLInputFactory.newInstance();
	     XMLStreamReader parser = factory.createXMLStreamReader(in);
	      
	     int count = 0;
	     
	     while (true) {
	    	 if (count % 1000 == 0) {
	    		 System.out.println("Parser count: " + Integer.toString(count));
	    	 }
	    	 
	         int event = parser.next();  
	         if (event == XMLStreamConstants.START_ELEMENT) {
	             if (parser.getLocalName().equals("property")) processProperty(parser);
	         } else if (event == XMLStreamConstants.END_DOCUMENT) {
	        	 break;
	         }
	         count++;
	     }
	      
		 System.out.println("Parser count: " + Integer.toString(count));
	     
		 parser.close();
	     
	  } catch (IOException e) {
	     System.out.println(e.getMessage());
	  }	
		
	}

	public SearchResult search(String input) {
		String key = PrepKey.strip(input);

		boolean toolong = false;
		if (key.length() > MAX_KEY_LENGTH) {
			key = key.substring(0, MAX_KEY_LENGTH);
			toolong = true;
		}

 		String fullkey = PrepKey.strip(input);
		
 		long t0 = System.nanoTime();
 		
		NrhpPropertyList all = _dict.get(key);
				
		int max = _offset + _maxSearchResults;
		int total = 0;		
		NrhpSearchResult result = new NrhpSearchResult();

		long t1 = System.nanoTime();
 		
		if (all != null) {
			NrhpPropertyList.Node curr = all.getRootNode();
			
			while (curr != null) {
				NrhpProperty nprop = curr.data;
				if ((!toolong) || (toolong && nprop.matches(fullkey))) {
					if ((total < max) && (total >= _offset)) {
						result.add(nprop);						
					}
					
					total++;
				}
				
				curr = curr.next;			
			}
		}

		result.setTotalPropertyCount(total);
		
		long t2 = System.nanoTime();
 		
		System.out.println("Time to get: " + ((t1 - t0) / 1e6));
		System.out.println("Time to count & select: " + ((t2 - t1) / 1e6));
		
		return result;
	}

	public void setMaxSearchResults(int maxSearchResults) {
		_maxSearchResults = maxSearchResults;
	}

    private void processProperty(XMLStreamReader parser) {
    	NrhpProperty property = new NrhpProperty();
    	property.setNrhpId(Integer.valueOf(parser.getAttributeValue(0)));
    	try {
    	while (true) {
    		int event = parser.next();
	         if (event == XMLStreamConstants.START_ELEMENT) {
	             if (parser.getLocalName().equals("name")) {
                     property.add(parser.getElementText());
                     
	             } else if (parser.getLocalName().equals("address")) {
                     property.setAddress(parser.getElementText());
                     
	             } else if (parser.getLocalName().equals("city")) {
                     property.setCity(parser.getElementText());
                     
	             } else if (parser.getLocalName().equals("state")) {
                     property.setState(parser.getElementText());
                     
	             } 
	         } else if (event == XMLStreamConstants.END_ELEMENT) {
	        	 if (parser.getLocalName().equals("property")) break;
	         }  
    	}
    	    	
    	// now add to the database
    	
    	_ids.clear();
    	
    	String entry;
     	String[] names = property.getNames();
     	for (int ii = 0; ii < names.length; ii++) {
     		entry = PrepKey.strip(names[ii]);
     		makeRecordIdsRecursion(entry);
     		property.addToSearchable(entry);
     	}
     	
     	entry = PrepKey.strip(property.getAddress());
     	makeRecordIdsRecursion(entry);
     	property.addToSearchable(entry);
     	
     	entry = PrepKey.strip(property.getCity() + property.getState());
     	makeRecordIdsRecursion(entry);
     	property.addToSearchable(entry);
     	
     	for (String id : _ids) {
     		_dict.put(id, property);
		}
		
	    } catch (XMLStreamException e) {
	    	System.out.println(e.getMessage());
	    }
    }
  
    /**
     * Add searchable strings to _ids.  Searchable strings are limited in length
     * to MAX_KEY_LENGTH. So, if the MAX_KEY_LENGTH is 10, and the source key is 
     * "itasoftware", then _ids will contain the following
     * substrings:
     * 
     * i
     * it
     * ita
     * itas
     * itaso
     * itasof
     * itasoft
     * itasoftw
     * itasoftwa
     * itasoftwar
     * t
     * ta
     * tas
     * taso
     * tasof
     * tasoft
     * tasoftw
     * tasoftwa
     * tasoftwar
     * tasoftware
     * a
     * as
     * aso
     * asof
     * asoft
     * .
     * .
     * .
     * e
     * 
     * @param key
     */
    private void makeRecordIdsRecursion(String key) { 
    	if (key == null) {
    		return;
    	}
    	
    	int maxlen = key.length() > MAX_KEY_LENGTH ? MAX_KEY_LENGTH : key.length();
    	if (maxlen == 0) {
    		return;
    	}
    	
    	for (int ii = 1; ii <= maxlen; ii++) {
    		String s = key.substring(0, ii);
    		if (!_ids.contains(s)) {
      	        _ids.add(s);
    		}
    	}
    	
    	makeRecordIdsRecursion(key.substring(1));
    }
    
}
