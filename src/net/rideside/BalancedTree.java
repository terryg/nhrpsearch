package net.rideside;

import java.util.LinkedList;
import java.util.Vector;

public class BalancedTree {
	private Node _root;
	private Integer _max;
	private Integer _min;
	
	public BalancedTree(Integer min, Integer max) {
		this._max = max;
		this._min = min;
	}
	
	public NrhpPropertyList get(String key) {
		Integer location = 0;
		Node node = this.retrieve(key, location);
		
		return (NrhpPropertyList) node.data;
	}

	public void put(String id, NrhpProperty property) {
		this.insert(id);
		
		Integer location = 0;
		Node node = this.retrieve(id, location);
		
		NrhpPropertyList list = (NrhpPropertyList) node.data;
		list.add(property);
	}

	private Boolean insert(String entry) {
		Boolean moveup = false;
	    String newEntry = new String("");
		Node right = null;
	    
	    moveup = push_down(entry, this._root, right, newEntry);
	    
	    if (moveup) {
	      this._root = new Node(newEntry, this._root, right);
	    }
	    
	    return true;
	}
	  
	 private Node retrieve(String search, Integer location) {
	    Node current = this._root;
	    String key = null;
	    Node entry = null;
	    
	    while (null != current && null == entry) {
	      location = current.search(search);  
	      if (location != -1) {
	        entry = current.branches.get(location);
	      } else {
	        current = current.branches.get(location + 1);
	      }
	    }
	    
	    return entry;  
	 }

	 
	 private Boolean push_down(String entry, Node node, Node newRight, String newEntry) {
	    Node current = node;
	    Boolean moveup = false;
	    Integer location = 0;
	    
	    if (null == node) {
	      moveup = true;
	      newEntry = entry;
	      newRight = null;
	    } else {
 	        location = node.search(entry);
	      if (-1 == location) {
	        // not good
	      } else {  
	        moveup = push_down(entry, node.branches.get(location + 1), newRight, newEntry);
	      }
	      
	      if (moveup) {
	        if (node.count < this._max) {
	          moveup = false;
	          node.add(newEntry, newRight, location + 1);
	        } else {
	          moveup = true;
	          newEntry = split(newEntry, newRight, node, location, newRight);
	        }
	      }    
	    }
	    
	    return moveup;
	 }
	  
	 private String split(String entry, Node right, Node node, Integer location, Node newRight) {
	    
		newRight = new Node(entry, null, null);	    
	    Integer median = this._min + 1;
	    if (location < this._min) {
	      median = this._min;
	    }
	    
	    Integer num = median;
	    while (num < this._max) {
	      newRight.keys.set(num - median, node.keys.get(num));
	      newRight.branches.set(num - median + 1, node.branches.get(num + 1));
	      num = num + 1;
	    }

	   newRight.count = this._max - median;  
	   node.count = median;

	   if (location < this._min) {
	      node.add(entry, right, location + 1);
	   } else {
	      newRight.add(entry, right, location - median + 1);
	   }
	   
	   newRight.branches.set(0, node.branches.get(node.count));
	   node.count = node.count - 1;
	   
	   return node.keys.get(node.count);
	 }

	
	public class Node {
		public NrhpPropertyList data;
		public Integer count;
		public Vector<String> keys;
		public Vector<Node> branches;
		
		public Node(String key, Node left, Node right) {
			this.count = 1;
			this.keys = new Vector<String>();
			this.keys.add(key);
			
			this.branches = new Vector<Node>();
			this.branches.add(left);
			this.branches.add(right);

		}
		
		public void add(String key, Node right, Integer location) {
			Integer num = this.count;
			while (num > location) {
				this.keys.set(num, this.keys.get(num - 1));
				this.branches.set(num, this.branches.get(num - 1));
				num = num - 1;
			}
			
			this.keys.add(key);
			this.branches.add(right);
			
			this.count = this.count + 1;
		}
		
		public Integer search(String key) {
		  Boolean found = false;
		  Integer location = 0;
		    
		   if (key.compareTo(this.keys.get(0)) < 0) {
		      location = -1;
		   } else {
		      location = this.count - 1;
		      while (key.compareTo(this.keys.get(location)) > 0 && location > 0) {
		         location = location - 1;
		      }
		      
		      if (key == keys.get(location)) {
		        found = true;
		      }
		   }

		   if (found) {
			   return location;
		   } else {
			   return -1;
		   }
		}

	}
}
