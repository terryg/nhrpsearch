package net.rideside;

import java.util.LinkedList;

public class BinaryNrhpPropertyTree {
	private BSTNode _rootNode;
	
	public BinaryNrhpPropertyTree() {
		
	}
	
	public NrhpProperty get(String key) {
		BSTNode node = getOrCreate(key);
		return node.data;
	}
	
	public void put(String key, NrhpProperty data) {
		BSTNode node = getOrCreate(key);
		node.data = data;
	}
	
	public LinkedList<NrhpProperty> traverse() {
		return traverse(_rootNode);
	}
	
	private LinkedList<NrhpProperty> traverse(BSTNode node) {
		LinkedList<NrhpProperty> result = new LinkedList<NrhpProperty>();
		
		if (node == null) return result;
		
		result.addAll(traverse(node.right));
		result.addAll(traverse(node.left));
		return result;
	}
	
	private BSTNode getOrCreate(String key) {
		BSTNode curr = _rootNode;
		BSTNode parent = null;
		int comparison = 0;
		while (curr != null) {
			parent = curr;
			comparison = key.compareTo(curr.data.getNames()[0]);
			
			if (comparison > 0) {
				curr = curr.right;
			} else if (comparison < 0) {
				curr = curr.left;
			} else {
				return curr;
			}
		}
		
		BSTNode n = new BSTNode();
		if (parent == null) {
			_rootNode = n;
		} else if (comparison > 0) {
			parent.right = n;
		} else if (comparison < 0) {
			parent.left = n;
		}
		
		return n;
	}

	public class BSTNode {
		public BSTNode right;
		public BSTNode left;
		public NrhpProperty data;
		
		public BSTNode() {
			this.right = null;
			this.left = null;
			this.data = null;
		}
	}
}
