package net.rideside;

public class Entry {
	public int key;
	public NrhpSearchResult data;
	public Entry next;
	
	public Entry(int key, NrhpSearchResult data) {
		this.key = key;
		this.data = data;
	}

}
