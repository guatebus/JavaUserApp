package app.gus.servlet.resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Resource request endpoint
 */
@WebServlet(
        name = "ResourceServlet" ,
        urlPatterns = { "/resources" })
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = -7565940648825117787L;
	
	private static final List<Integer> availableResources;
	static {
		availableResources = Arrays.asList(1, 2, 3);
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	System.out.println("ResourceServlet :: Requested resource: " + request.getRequestURI());
		String requestedResourceID = request.getParameter("p");
    	if (requestedResourceID != null) {
            System.out.println("ResourceServlet :: Requested param: " + requestedResourceID);
        	if (availableResources.contains(Integer.parseInt(requestedResourceID))) {
            	request.getRequestDispatcher("page.jsp").include(request, response);
                return;
        	}
    	}
    	response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}