package app.gus.servlet.session;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Login endpoint
 */
@WebServlet(
        name = "login-servlet" ,
        urlPatterns = { "/login-servlet" },
        initParams = {
                @WebInitParam(name = "sessionMinutes", value = "5")
        })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -6908417593825706709L;

	private static final String salt = "d4a273c8";
	
	/**
	 * THIS SHOULD BE HANDLED BY A HASH SECURITY CLASS
	 * 
	 * Abstraction where password+salt hash would be used - hash dependency should be injected
	 * @param input
	 * @return
	 */
	private static final String getHash(String input) {
		// Hashing algorithm would be applied to input + salt and returned here...
		return input+salt;
	}

    /**
	 * THIS DATA SHOULD BE STORED BY A PERSISTENCE COMPONENT
	 * 
     * Maps user ids to (salted) passwords
     * ** In production this data would be in the persistence layer (DB) **
     * ** In production passwords would be hashed **
     */
    private static final Map<String, String> userHash;
    static
    {
    	userHash = new HashMap<String, String>();
    	userHash.put("1", getHash("1"));
    	userHash.put("2", getHash("2"));
    	userHash.put("3", getHash("3"));
    }

    protected int sessionLength = 0;

    public int getSessionLength() {
		return sessionLength;
	}

	public void setSessionLength(int minutes) {
		this.sessionLength = minutes * 60;
	}

	protected ServletContext context;
	
	public void init(ServletConfig config) throws ServletException{
	    this.setSessionLength(Integer.parseInt(config.getInitParameter("sessionMinutes")));
	    if (this.sessionLength <= 0) {
	    	throw new ServletException("sessionLength config error");
	    }
  		this.context = config.getServletContext();
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        String user = request.getParameter("u");
        String pwd = request.getParameter("p");
        
        //fetch user data from persistence
        String record = this.fetchPassword(user);
        if (record != null && record.equals(getHash(pwd))){
        	HttpSession session = request.getSession();
            session.setAttribute("user", user);
            //session expiration
            session.setMaxInactiveInterval(this.sessionLength);
            Cookie cookie = new Cookie("user", user);
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            response.sendRedirect("welcome.jsp");
            this.context.log("Login :: logging in user: "+user);
        } else {
            response.getWriter().println("<font color=red>Wrong username password combination</font>");
            request.getRequestDispatcher("/login.html").include(request, response);
        }
 
    }
    
    /**
	 * THIS SHOULD BE HANDLED BY A PERSISTENCE ACCESSOR CLASS
	 * 
     * Abstraction to fetch user record from persistence layer (db) - persistence access should be injected
     * @param userID
     * @return
     */
    protected String fetchPassword(String userID){
        return userHash.get(userID);
    }
}