package app.gus.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.gus.security.voter.ResourceVoterInterface;

/**
 * System Firewall
 * 
 * Allows authentication requests, then filters all other requests by checking:
 *  - User authentication
 *  - Resource security
 *  - User-Resource Permission (via the voter pattern which enforces user-role relationships)
 */
@WebFilter("/AuthorizationFilter")
public class AuthorizationFilter implements Filter {
	
	protected ServletContext context;
	protected ResourceVoterInterface voter;
	
	public AuthorizationFilter setVoter(ResourceVoterInterface voter) {
		this.voter = voter;
		
		return this;
	}

    public void init(FilterConfig config) throws ServletException {
    	this.context = config.getServletContext();
    }
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        
        //allow login / logout requests
        if (this.isAuthRequest(req)) {
    		chain.doFilter(request, response);
    		
    		return;
        }
        
        HttpServletResponse res = (HttpServletResponse) response;
        
        //apply filters to all other resources
        if (this.isUserAuthenticated(req, res) &&
    		this.isPublicResource(req, res) &&
    		this.isUserAuthorized(req, res)) {
        		chain.doFilter(request, response);
        }
    }
    
    /*
     * Let requests for authentication reach their endpoints
     */
    protected boolean isAuthRequest(HttpServletRequest req) throws IOException {
    	String uri = req.getRequestURI();
    	if (uri.endsWith("login.html") ||
        	uri.endsWith("login-servlet") ||
        	uri.endsWith("logout-servlet") ||
        	(uri.endsWith("welcome.jsp") && req.getSession(false) != null)) {
    		
    		return true;
    	}
    	
    	return false;
    }
    
    protected boolean isUserAuthenticated(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	if(req.getSession(false) == null){
    		this.context.log("Filter :: unauthenticated request for resource: "+req.getRequestURI());
            res.sendRedirect("/login.html");
            
            return false;
        }
        
        return true;
    }

    /*
     * 
     */
    protected boolean isPublicResource(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	String uri = req.getRequestURI();
    	//.jsp may not be requested directly
    	if (uri.contains("page.jsp")){
    		this.context.log("Filter :: direct access attempted to: "+uri);
        	res.sendError(HttpServletResponse.SC_FORBIDDEN);
        	
            return false;
        }
        
        return true;
    }

    protected boolean isUserAuthorized(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	String requestedResourceID = req.getParameter("p");
    	HttpSession session = req.getSession(false);
    	String userID = (String) session.getAttribute("user");
    	this.context.log("Filter :: request for p=" + requestedResourceID + " by user "+userID);
        
        //delegate user-role check to voter
        if (this.voter.doVote(userID, requestedResourceID)) {
        	return true;
        }
    	
    	res.sendError(HttpServletResponse.SC_FORBIDDEN);
    	
    	return false;
    }
 
    public void destroy() {
    }
 
}
