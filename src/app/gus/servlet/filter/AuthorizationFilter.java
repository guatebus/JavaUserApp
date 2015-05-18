package app.gus.servlet.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@WebFilter("/AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    /**
     * Maps user ids to roles
     * ** In production, this data would be in the persistence layer **
     */
    private static final Map<String, String> userRole;
    static
    {
    	userRole = new HashMap<String, String>();
    	userRole.put("1", "PAG_1");
    	userRole.put("2", "PAG_2");
    	userRole.put("3", "PAG_3");
    }

    /**
     * Maps resource (page) ids to roles required to view it
     * ** In production, this data would be in the persistence layer **
     */
    private static final Map<String, String> resourceRole;
    static
    {
    	resourceRole = new HashMap<String, String>();
    	resourceRole.put("1", "PAG_1");
    	resourceRole.put("2", "PAG_2");
    	resourceRole.put("3", "PAG_3");
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        
        //allow login / logout servlets and resources
        if (
        	uri.endsWith("login.html") ||
        	uri.endsWith("login-servlet") ||
        	uri.endsWith("login-success.jsp") ||
        	uri.endsWith("logout-servlet")) {
    		chain.doFilter(request, response);
    		
    		return;
        }

        //apply filters to all other resources
        if (this.isAuthenticated(req, res, uri) &&
    		this.isResourceAllowed(req, res, uri) &&
    		this.isUserAuthorized(req, res)) {
        		chain.doFilter(request, response);
        }
    }
    
    protected boolean isAuthenticated(HttpServletRequest req, HttpServletResponse res, String uri) throws IOException {
    	if(req.getSession(false) == null){
        	System.out.println("AuthenticationFilter :: unauthenticated request for resource: "+uri);
            res.sendRedirect("/login.html");
            
            return false;
        }
        
        return true;
    }

    protected boolean isResourceAllowed(HttpServletRequest req, HttpServletResponse res, String uri) throws IOException {
    	//block direct access to page.jsp resource
    	if (uri.contains("page.jsp")){
        	System.out.println("AuthenticationFilter :: attempted direct access to resource: "+uri);
        	res.sendError(HttpServletResponse.SC_FORBIDDEN);
        	
            return false;
        }
        
        return true;
    }

    protected boolean isUserAuthorized(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	String requestedResourceID = req.getParameter("p");
    	System.out.println("AuthorizationFilter :: Requested param: " + requestedResourceID);
    	
    	HttpSession session = req.getSession(false);
    	String userID = (String) session.getAttribute("user");
        System.out.println("AuthorizationFilter:: current user: "+userID);
        
        String roleRequesting = userRole.get(userID);
        
    	if (roleRequesting != null) {
    		String roleRequiredForResource = resourceRole.get(requestedResourceID);
        	if (roleRequesting.equals(roleRequiredForResource)) {
        		return true;
        	}
    	} 
    	
    	res.sendError(HttpServletResponse.SC_FORBIDDEN);
    	
    	return false;
    }
 
    public void destroy() {
    }
 
}
