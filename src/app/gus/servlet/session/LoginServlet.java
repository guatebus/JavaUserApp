package app.gus.servlet.session;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
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

    /**
     * Maps user ids to passwords
     * ** In production, this data would be in the persistence layer (DB) **
     * ** In production, password data should be hashed **
     */
    private static final Map<String, String> userHash;
    static
    {
    	userHash = new HashMap<String, String>();
    	userHash.put("1", "1");
    	userHash.put("2", "2");
    	userHash.put("3", "3");
    }

    protected int sessionLength = 0;

    public int getSessionLength() {
		return sessionLength;
	}

	public void setSessionLength(int minutes) {
		this.sessionLength = minutes * 60;
	}

	public void init(ServletConfig conf) throws ServletException{
      this.setSessionLength(Integer.parseInt(conf.getInitParameter("sessionMinutes")));
      if (this.sessionLength <= 0) {
    	  throw new ServletException("sessionLength config error");
      }
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        String user = request.getParameter("u");
        String pwd = request.getParameter("p");
        
        //fetch user data from persistence
        String record = this.fetchPassword(user);
        if(record != null && record.equals(pwd)){
        	//create a session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            //set session expiration
            session.setMaxInactiveInterval(this.sessionLength);
            Cookie cookie = new Cookie("user", user);
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            response.sendRedirect("welcome.jsp");
        }else{
            response.getWriter().println("<font color=red>Wrong username password combination</font>");
            request.getRequestDispatcher("/login.html").include(request, response);
        }
 
    }
    
    /**
     * Abstraction to fetch user record from persistence layer (db)
     * @param userID
     * @return
     */
    protected String fetchPassword(String userID){
        return userHash.get(userID);
    }
}