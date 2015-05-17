package app.gus.servlet.session;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
/**
 * Login endpoint
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -6908417593825706709L;

    private static final Map<String, String> userHash;
    static
    {
    	userHash = new HashMap<String, String>();
    	userHash.put("1", "1");
    	userHash.put("2", "2");
    	userHash.put("3", "3");
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        
        //fetch user data from persistence
        String record = this.fetchUser(user);
        if(record != null && record.equals(pwd)){
        	//create a session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            //set session expiration
            session.setMaxInactiveInterval(5*60);
            Cookie userName = new Cookie("user", user);
            userName.setMaxAge(5*60);
            response.addCookie(userName);
            response.sendRedirect("LoginSuccess.jsp");
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>Wrong username password combination</font>");
            rd.include(request, response);
        }
 
    }
    
    /**
     * Abstraction method to fetch user records from persistence layer (db)
     * @param username
     * @return
     */
    protected String fetchUser(String username){
        return userHash.get(username);
    }
 
}