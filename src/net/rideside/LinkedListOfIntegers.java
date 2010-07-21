package net.rideside;

public class LinkedListOfIntegers {
	private LLNodeInt _rootNode;
	private int _count;
	
	public LinkedListOfIntegers() {
		_count = 0;
	}
	
	public LLNodeInt getRootNode() {
		return _rootNode;
	}
	
	public void add(int data) {
		if (_rootNode == null) {
			_rootNode = new LLNodeInt(data, null);
		} else {
			LLNodeInt next = new LLNodeInt(data, _rootNode);
			_rootNode = next;
		}
		_count++;
	}
	
	public int count() {
		return _count;
	}
	
	public boolean contains(int data) {
		if (_rootNode == null) return false;
		return contains(data, _rootNode);
	}

	private boolean contains(int data, LLNodeInt node) {
		if (node.data == data) return true;
		return node.next == null ? false : contains(data, node.next);
	}
	
	public void clear() {
		if (_rootNode != null) {
			_rootNode = null;
		}
		_count = 0;
	}
	
	public void merge(LinkedListOfIntegers more) {
		LLNodeInt last = more.getRootNode();
		while (last.next != null) {
			last = last.next;
		}
		
		last.next = this._rootNode;
		this._rootNode = more.getRootNode();
		
		_count += more.count();
	}
	
	public class LLNodeInt {
		public LLNodeInt next;
		public int data;
		
		public LLNodeInt(int data, LLNodeInt next) {
			this.data = data;
			this.next = next;
		}
	}
}
