package app.gus.servlet.resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * 'Resource' (page) request endpoint
 */
@WebServlet(
            name = "ResourceServlet" ,
            urlPatterns = { "/resources" }
        )
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = -7565940648825117787L;

    /**
     * Lists available 'resources' (pages)
     * ** In production, this data would be in the persistence layer **
     */
	private static final List<Integer> availableResources;
	static {
		availableResources = Arrays.asList(1, 2, 3);
	}

	protected ServletContext context;

	public void init(ServletConfig config) throws ServletException{
  		this.context = config.getServletContext();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	this.context.log("ResourceServlet :: requested resource: " + request.getRequestURI());
    	String requestedResourceID = request.getParameter("p");
    	if (requestedResourceID != null) {
    		this.context.log("ResourceServlet :: requested param: " + requestedResourceID);
		    if (availableResources.contains(Integer.parseInt(requestedResourceID))) {
	            request.getRequestDispatcher("page.jsp").forward(request, response);
	                
	            return;
	        }
    	}
    	response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
