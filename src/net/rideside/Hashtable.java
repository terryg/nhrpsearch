package net.rideside;


public class Hashtable {
	private static final int INITIAL = 11;
	private static final float LOAD_FACTOR = 0.75f;

	private int _size;
	private int _modCount;
	private int _threshold;
	private Entry[] _buckets;
	
	public Hashtable() {
		_size = 0;
		_modCount = 0;
		_buckets = new Entry[INITIAL];
		_threshold = (int) (INITIAL * LOAD_FACTOR);
	}

	public void put(int key, NrhpSearchResult data) {
		 int index = hash(key);
		 Entry e = _buckets[index];

		 while (e != null) {
			 if (key == e.key) {
				 e.data = data;
				 return;
			 } else {
				 e = e.next;
			 }
		 }
			 
		 _modCount++;
         if (++_size > _threshold) {
        	 rehash();
        	 index = hash(key);
         }
		
         e = new Entry(key, data);
		 e.next = _buckets[index];
		 _buckets[index] = e;
		 return;
	}
	
	public NrhpSearchResult get(int key) {
		int index = hash(key);
		Entry e = _buckets[index];
		while (e != null) {
			if (e.key == key) {
				return e.data;
			}
			e = e.next;
		}
		return null;
	}
	
	private int hash(int key) {
		int index = key % _buckets.length;
		return (index < 0) ? -index : index;
	}
	
	private void rehash() {
		Entry[] oldBuckets = _buckets;
		
		int newcapacity = (_buckets.length * 2) + 1;
		_threshold = (int) (newcapacity * LOAD_FACTOR);
		_buckets = new Entry[newcapacity];
		
		System.out.println("newcapacity: " + newcapacity);
		
		for (int ii = oldBuckets.length - 1; ii >= 0; ii--) {
			Entry e = oldBuckets[ii];
		    while (e != null) {
		    	int index = hash(e.key);
		    	Entry dest = _buckets[index];
		
		    	if (dest != null) {
		    		while (dest.next != null)
		    			dest = dest.next;
		    		dest.next = e;
			    } else {
			    	_buckets[index] = e;
			    }
			
		    	Entry next = e.next;
		    	e.next = null;
		    	e = next;
		    }
		}		
	}
}
