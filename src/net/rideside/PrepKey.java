package net.rideside;

public class PrepKey {
    
    /**
     * The string used to create record ids should only contain 
     * (lowercase) letters and numbers.
     * 
     * @param key
     * @return the prepared string
     */
   public static String strip(String key) {
    	if (key == null) return null;
    	
    	String result = new String();
    	
		for (int ii = 0; ii < key.length(); ii++) {
			char c = key.charAt(ii);
			if (64 < c && 91 > c) {
			  //add A-Z, but lowercase
			  result += Character.toLowerCase(c);	
			} else if (96 < c && 123 > c) {
			  //add a-z
			  result += c;
			} else if (47 < c && 58 > c) {
			  //add 0-9
			  result += c;
		    }
	    }// ii
		
		return result;
    }
}
