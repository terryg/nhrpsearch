package com.itasoftware.careers.nrhp;

/**
 * The results of a property database search.
 */
public interface SearchResult {

    /**
     * @return selected property matches.
     * @see PropertyDatabase#setMaxSearchResults(int)
     */
    public Property[] getProperties();

    /**
     * @return total number of matches in the database.
     */
    public int getTotalPropertyCount();
        
}
