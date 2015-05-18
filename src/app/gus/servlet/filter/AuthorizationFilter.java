package app.gus.servlet.filter;

import java.io.IOException;

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

import app.gus.security.voter.ResourceVoter;
 
@WebFilter("/AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
    }
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
    	String uri = req.getRequestURI();
        
        //allow login / logout servlets and resources
        if (this.isAuthenticationResource(uri)) {
    		chain.doFilter(request, response);
    		
    		return;
        }
        
        HttpServletResponse res = (HttpServletResponse) response;
        
        //apply filters to all other resources
        if (this.isAuthenticated(req, res) &&
    		this.isResourceAllowed(req, res, uri) &&
    		this.isUserAuthorized(req, res)) {
        		chain.doFilter(request, response);
        }
    }
    
    protected boolean isAuthenticationResource(String uri) throws IOException {
    	if (uri.endsWith("login.html") ||
        	uri.endsWith("login-servlet") ||
        	uri.endsWith("login-success.jsp") ||
        	uri.endsWith("logout-servlet")) {
    		
    		return true;
    	}
    	
    	return false;
    }
    
    protected boolean isAuthenticated(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	if(req.getSession(false) == null){
        	System.out.println("AuthenticationFilter :: unauthenticated request for resource: "+req.getRequestURI());
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
        
        //ask voter if resource is allowed for user
        ResourceVoter voter = new ResourceVoter();
        if (voter.doVote(userID, requestedResourceID)) {
        	return true;
        }
    	
    	res.sendError(HttpServletResponse.SC_FORBIDDEN);
    	
    	return false;
    }
 
    public void destroy() {
    }
 
}
