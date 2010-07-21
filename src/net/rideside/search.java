package net.rideside;

import javax.servlet.*;
import javax.servlet.http.*;

import com.itasoftware.careers.nrhp.Property;
import com.itasoftware.careers.nrhp.SearchResult;

import java.io.*;

public class search extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private NrhpPropertyDatabase _npd;
	
	public void init(ServletConfig config) 
		throws ServletException {

		super.init(config);
		
		_npd = new NrhpPropertyDatabase();

		String filepath = config.getServletContext().getInitParameter("filepath");
		
		File file = new File(filepath);
		
		System.out.println(file.getAbsoluteFile());
		
		_npd.setMaxSearchResults(10);
		
		try {
			_npd.initialize(file);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
    	throws ServletException, IOException {
		String query = request.getParameter("query");
		
		String offsetStr = request.getParameter("offset");
		
		int offset = 0;
		if (offsetStr != null) {
			offset = Integer.valueOf(offsetStr);
		}

		_npd.setOffset(offset);
		
		long t1 = System.nanoTime();
		
		SearchResult result = _npd.search(query);		  

		long t2 = System.nanoTime();
		
		PrintWriter out = response.getWriter();
		
		out.println("<style type=\"text/css\">");
		out.println("div.addresses {font-family: sans-serif;}");
		out.println("div.results {font-family: sans-serif;}");
		out.println("div.first-name {font-weight: bold;}");
	    out.println("div.search-results {height:500px;}");
		out.println("</style>");

		out.println("<div class=\"content\" style=\"font-family: sans-serif;\">");
		out.println("<div class=\"addresses\">");
		
		int totalCount = result.getTotalPropertyCount();
		
		Property[] properties = result.getProperties();
		for (Property property : properties) {
	    	out.println("<div class=\"address\">");

	    	//out.println("<div class=\"nrhpid\">");
	    	//out.println(((NrhpProperty) properties[ii]).getNrhpId());
	    	//out.println("</div>");
	    	
	    	String names[] = property.getNames();
	    	int ncount = names.length;
	    	
	    	for (int jj = 0; jj < ncount; jj++) {		    	
	    		if (0 == jj) {
	    			out.println("<div class=\"first-name\">");
	    		} else if (1 == jj) {
	    			out.println("<div class=\"names\">");
	    		} else {
	    			out.println("; ");
	    		}
	    		
	    		out.println(names[jj]);
	    		
	    		if (0 == jj) {
	    			out.println("</div>");
	    		} 
	    	}
	    	
	    	if (ncount > 1) {
    			out.println("</div>");	    		
	    	}
	    	
			out.println("<div class=\"addy-city-state\">");
			out.println(property.getAddress() + ", " + property.getCity() + ", " + property.getState());	
			out.println("</div>");
		    out.println("</div>");
		    out.println("<p/>");
    	}
		

		if (totalCount == 0) {
	    	out.println("<i>No results.</i>");
	    }
	    
		out.println("</div>");

		out.println("<p/>");
		
		System.out.println("totalCount: " + totalCount);
		System.out.println("offset: " + offset);
		System.out.println("max. search results: " + _npd.getMaxSearchResults());
		
	    out.println("<div class=\"results\">");
	    if (totalCount - offset > _npd.getMaxSearchResults()) {
	    	out.println("... and <div style=\"display: inline;\" id=\"more-results\"><a href=\"#\">");
	    	out.println(totalCount - _npd.getMaxSearchResults() - offset);
	    	out.println(" others</a></div>. ");
	    }

		long t3 = System.nanoTime();
		
	    out.println("Property search took ");
	    out.println((t2 - t1) / 1e6);
	    out.println(" ms.");
	    out.println("<i>(Concatenate results to HTML took ");
	    out.println((t3 - t2) / 1e6);
	    out.println(" ms.)</i>");
	    out.println("</div>");
	    
	    out.println("<p/><p/>");
	    
	    out.println("<div style=\"background-color:#dddddd; border: 1px black solid;\">");
	    out.println("Done");
	    out.println("</div>");
	    
	    out.println("<script type=\"text/javascript\">");
	    out.println("//<![CDATA[");
	    out.println("  Event.observe('more-results', 'click', function() {");
	    out.println("    var q = document.getElementById(\"query\");");
	    out.println("    if (q.value.length > 0) {");
	    out.println("      Element.hide('search-results');");
	    out.println("      Element.show('spinner');");
	    out.println("      new Ajax.Updater('search-results',");
	    out.println("                       'servlet/net.rideside.search', {");
	    out.println("                       method:'get',");
	    out.println("                       asynchronous:true,");
	    out.println("                       evalScripts:true,");
        out.println("                       onSuccess: function(request) {");
        out.println("                         Element.hide('spinner');");
        out.println("                         Element.show('search-results')},");
        out.println("                       parameters:'query=' + q.value + '&offset=" + (offset + _npd.getMaxSearchResults()) + "'}");
        out.println("                       )");
        out.println("    }");
        out.println(" });");
	    out.println("//]]>");
	    out.println("</script>");
	}
}
