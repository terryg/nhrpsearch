package net.rideside;

import com.itasoftware.careers.nrhp.Property;
import com.itasoftware.careers.nrhp.SearchResult;

public class NrhpSearchResult implements SearchResult {
    private Property[] _properties;
    private int _totalPropertyCount;
    
    NrhpSearchResult() {
    	_properties = new Property[0];
    }
 
    public Property[] getProperties() {
		return _properties;
	}

	public int getTotalPropertyCount() {
		return _totalPropertyCount;
	}

	public void setTotalPropertyCount(int total) {
		_totalPropertyCount = total;
	}
	
	public boolean addAll(NrhpSearchResult c) {
		Property[] properties = c.getProperties();
		for (Property property : properties) {
			this.add((NrhpProperty) property);
		}
		return true;
	}
	
	public void add(NrhpProperty property) {
		Property[] properties = new Property[_properties.length + 1];
		
		int indx = 0;
		for (Property prop : _properties) {
			properties[indx++] = prop;
		}
		
		properties[indx] = property;
		
		_properties = properties;
	}
	
}
