package net.rideside;

import com.itasoftware.careers.nrhp.Property;

public class NrhpProperty implements Property {
	private static final long serialVersionUID = 1L;
	
	private int _nrhpId;
	private StringList _names;
	private String _address;
	private String _city;
	private String _state;
	private StringList _searchable;
	
	public NrhpProperty() {
		_names = new StringList();
		_searchable = new StringList();
	}
	
	public int getNrhpId() {
		return _nrhpId;
	}
	
	public void setNrhpId(int id) {
		_nrhpId = id;
	}

	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		_address = address;
	}
	
	public String getCity() {
		return _city;
	}
	
	public void setCity(String city) {
		_city = city;
	}
	
	public String[] getNames() {
		int count = 0;
		StringList.Node curr = _names._root;
		while (curr != null) {
			curr = curr.next;
			count++;
		}
		
		String[] names = new String[count];
		
		count = 0;
		curr = _names._root;
		while (curr != null) {
		    names[count++] = curr.data;
		    curr = curr.next;
		}
		return names;
	}

	public void add(String name) {
		_names.append(name);
	}	
	
	public String getState() {
		return _state;
	}

	public void setState(String state) {
		_state = state;
	}

	public boolean matches(String query) {
		StringList.Node curr = _searchable._root;
		while (curr != null) {
		    if (curr.data.contains(query)) {
				return true;
			}
		    curr = curr.next;
		}
		return false;
	}
	
	public int compareTo(Property o) {
		NrhpProperty property = (NrhpProperty) o;
		return _names._root.data.compareTo(property._names._root.data);
	}
	
	public void addToSearchable(String entry) {
		if (entry != null) {
		    _searchable.append(entry);
		}
	}

}
