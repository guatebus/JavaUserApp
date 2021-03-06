package app.gus.servlet.session;
 
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout endpoint
 */
@WebServlet("/logout-servlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 3912794184499010591L;

	protected ServletContext context;
	public void init(ServletConfig config) throws ServletException{
    	this.context = config.getServletContext();
    }
     
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        
		Cookie[] cookies = request.getCookies();
        if(cookies != null){
	        for(Cookie cookie : cookies){
	            if(cookie.getName().equals("JSESSIONID")){
	            	this.context.log("Logout :: invalidating session: "+cookie.getValue());
	                break;
	            }
	        }
        }
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        this.context.log("Logout :: invalidating user: "+session.getAttribute("user"));
        if(session != null){
            session.invalidate();
        }
        response.sendRedirect("login.html");
    }
 
}