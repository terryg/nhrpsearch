package net.rideside;

import java.util.LinkedList;

public class BinarySearchTree {
	private Node _rootNode;
	
	public BinarySearchTree() {
		
	}
	
	public Object get(String key) {
		Node node = getOrCreate(key);
		return node.data;
	}
	
	public void put(String key, Object data) {
		Node node = getOrCreate(key);
		node.data = data;
	}
	
	public LinkedList<Object> traverse() {
		return traverse(_rootNode);
	}
	
	private LinkedList<Object> traverse(Node node) {
		LinkedList<Object> result = new LinkedList<Object>();
		
		if (node == null) return result;
		
		result.addAll(traverse(node.right));
		result.addAll(traverse(node.left));
		return result;
	}
	
	private Node getOrCreate(String key) {
		Node curr = _rootNode;
		Node parent = null;
		int comparison = 0;
		while (curr != null) {
			parent = curr;
			comparison = key.compareTo(curr.key);
			
			if (comparison > 0) {
				curr = curr.right;
			} else if (comparison < 0) {
				curr = curr.left;
			} else {
				return curr;
			}
		}

		Node n = new Node(key);
		if (parent == null) {
			_rootNode = n;
		} else if (comparison > 0) {
			parent.right = n;
		} else if (comparison < 0) {
			parent.left = n;
		}
		
		return n;
	}

	public class Node {
		public String key;
		public Node right;
		public Node left;
		public Object data;
		
		public Node(String key) {
			this.key = key;
			this.right = null;
			this.left = null;
			this.data = null;
		}
	}
}
