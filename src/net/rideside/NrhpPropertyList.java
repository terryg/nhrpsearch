package net.rideside;

public class NrhpPropertyList {
	private Node _rootNode;
	private int _count;
	
	public NrhpPropertyList() {
		_count = 0;
	}
	
	public Node getRootNode() {
		return _rootNode;
	}
	
	public void add(NrhpProperty data) {
		_rootNode = new Node(data, _rootNode);
		
		/*
		if (_rootNode == null) {
			_rootNode = new Node(data, null);
		} else if (data.compareTo(_rootNode.data) < 0) {
			_rootNode = new Node(data, _rootNode);
		} else {
			Node curr = _rootNode;
			while (curr.next != null) {
			    if (data.compareTo(curr.next.data) < 0) {
					curr.next = new Node(data, curr.next);
					break;
				}
				curr = curr.next;
			}
			if (curr.next == null) {
				curr.next = new Node(data, null);
			}
		}
		*/
		
		_count++;
		
	}
	
	public int size() {
		return _count;
	}
	
	public boolean contains(NrhpProperty data) {
		if (_rootNode == null) return false;
		return contains(data, _rootNode);
	}

	private boolean contains(NrhpProperty data, Node node) {
		if (node.data == data) return true;
		return node.next == null ? false : contains(data, node.next);
	}
	
	public void clear() {
		if (_rootNode != null) {
			_rootNode = null;
		}
		_count = 0;
	}

	public void sort() {
		Node prev = null;
	    Node curr = _rootNode;
	    Node next = _rootNode.next;
		while (next != null) {
			if (curr.data.compareTo(next.data) > 0) {
				if (prev != null) {
					prev.next = curr.next;
				}
				
							
				curr = next;	
			}

			prev = curr;
			curr = next;
			next = curr.next;
		}
	}
	
	public boolean addAll(NrhpPropertyList list) {
		NrhpPropertyList.Node last = list.getRootNode();
		while (last.next != null) {
			last = last.next;
		}
		
		last.next = this._rootNode;
		this._rootNode = list.getRootNode();
		
		_count += list.size();
		
		return true;
	}
	
	public class Node {
		public Node next;
		public NrhpProperty data;
		
		public Node(NrhpProperty data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
}
