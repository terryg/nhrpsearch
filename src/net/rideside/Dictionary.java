package net.rideside;

public class Dictionary {
	private Node _rootNode;
	
	public Dictionary() {

	}
	
	public NrhpPropertyList get(String key) {
		Node node = getOrCreate(key);
		return node.data;
		
	}
	
	private int makeIndex(char c) {
		int result = -1;
		
		if (64 < c && 91 > c) {
		    //add A-Z, but lowercase
			result = c - 55;
	    } else if (96 < c && 123 > c) {
		    //add a-z
			result = c - 87;
		} else if (47 < c && 58 > c) {
		    //add 0-9
			result = c - 48;
		}
		
		return result;
		
	}
	
	
	private Node getOrCreate(String key) {
		int index = 0;
		if (_rootNode == null) _rootNode = new Node();
		
		Node node = _rootNode.nodes[makeIndex(key.charAt(index))];
		Node[] nodes = _rootNode.nodes;
		
		while (true) {
			if (node == null) {
				node = new Node();
				nodes[makeIndex(key.charAt(index))] = node;
				return node;
			}
			
			index++;
			
			if (index == key.length()) {
			    return node;
			} else {
				nodes = node.nodes;
				node = nodes[makeIndex(key.charAt(index))];
			}
		}
	}
	
	public void put(String id, NrhpProperty property) {
		
	}
	
	public class Node {
		public Node nodes[];
		public NrhpPropertyList data;
		
		public Node() {
			nodes = new Node[36];
			
		}
	}
	
}
