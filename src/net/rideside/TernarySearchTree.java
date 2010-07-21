package net.rideside;

public class TernarySearchTree {
	public TSTNode _rootNode;
	
	public NrhpPropertyList get(String key) {
		TSTNode node = getOrCreate(key);
		return node.data;
	}
	
	public NrhpPropertyList getByPrefix(String key) {
		NrhpPropertyList nodes = new NrhpPropertyList();
		
		TSTNode current = getNode(key);
		
		if (current != null) {
			getChildren(current, key, nodes);
		}
		
		return nodes;
	}
	
	public void put(String key, NrhpProperty data) {
		TSTNode node = getOrCreate(key);
		if (node.data == null) {
			node.data = new NrhpPropertyList();
		}
		node.data.add(data);
	}
	
	private TSTNode getNode(String key) {
		int indx = 0;

		TSTNode node = _rootNode;
		while (node != null) {
		    if (key.charAt(indx) < node.splitchar) {
		    	node = node.lo; 
		    } else if (key.charAt(indx) == node.splitchar) { 
		    	indx++;
		    	if (indx == key.length()) {
		    		return node;
		    	}
		    	node = node.eq; 
		    } else {
		    	node = node.hi; 
		    } 
		}
		return node;
	}
	
	private TSTNode getOrCreate(String key) {
		int indx = 0;
		if (_rootNode == null) _rootNode = new TSTNode(key.charAt(indx));
		
		TSTNode node = _rootNode;
		while (true) {
			if (node == null) {
				node = new TSTNode(key.charAt(indx));
				return node;
			}
			
		    if (key.charAt(indx) < node.splitchar) {
		    	if (node.lo == null) {
		    		node.lo = new TSTNode(key.charAt(indx));
		    	}
		    	node = node.lo; 
		    } else if (key.charAt(indx) == node.splitchar) { 
		    	indx++;
		    	if (indx == key.length()) {
		    		return node;
		    	}
		    	if (node.eq == null) {
		    		node.eq = new TSTNode(key.charAt(indx));
		    	}
		    	node = node.eq; 
		    } else {
		    	if (node.hi == null) {
		    		node.hi = new TSTNode(key.charAt(indx));
		    	}
		    	node = node.hi; 
		    } 
		}
	}

	private void getChildren(TSTNode current, String currentKey, NrhpPropertyList nodes) {
		if (current == null) return;
		
		if (current.data != null) {
			nodes.addAll((NrhpPropertyList) current.data);
		}
		
		if (current.lo != null) {
		  getChildren(current.lo, currentKey + current.lo.splitchar, nodes);
		}
		
		if (current.eq != null) {
			getChildren(current.eq, currentKey + current.eq.splitchar, nodes);
		}
		
		if (current.hi != null) {
			getChildren(current.hi, currentKey + current.hi.splitchar, nodes);		
		}
	}
	
	public class TSTNode {
		public char splitchar;
		public TSTNode lo;
		public TSTNode eq;
		public TSTNode hi;
		public NrhpPropertyList data;
		
		public TSTNode(char s) {
			this.splitchar = s;
		}
	}
}
