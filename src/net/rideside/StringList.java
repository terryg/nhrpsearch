package net.rideside;

public class StringList {
	public Node _root;
	
	StringList() {
	
	}
	
	public void add(String data) {
		if (_root == null) {
			_root = new Node(data, null);
		} else {
			_root = new Node(data, _root);
		}
	}
	
	public void append(String data) {
		if (_root == null) {
			_root = new Node(data, null);
		} else {
			Node curr = _root;
			while (curr.next != null) {
				curr = curr.next;
			}
			curr.next = new Node(data, null);
		}
	}
	
	public class Node {
		public String data;
		public Node next;
		
		public Node(String data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
}
