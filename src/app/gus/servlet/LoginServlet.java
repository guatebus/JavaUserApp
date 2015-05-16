package app.gus.servlet;
 
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
        description = "Login Servlet",
        urlPatterns = { "/LoginServlet" },
        initParams = {
                @WebInitParam(name = "user", value = "gus"),
                @WebInitParam(name = "password", value = "gus")
        })
public class LoginServlet extends HttpServlet {
        
	private static final long serialVersionUID = -6908417593825706709L;


	public void init() throws ServletException {
        //we can create DB connection resource here and set it to Servlet context
//        if(getServletContext().getInitParameter("dbURL").equals("jdbc:mysql://localhost/mysql_db") &&
//                getServletContext().getInitParameter("dbUser").equals("mysql_user") &&
//                getServletContext().getInitParameter("dbUserPwd").equals("mysql_pwd"))
//        getServletContext().setAttribute("DB_Success", "True");
//        else throw new ServletException("DB Connection error");
    }
 
     
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        //get request parameters for userID and password
        String usr = request.getParameter("usr");
        String pwd = request.getParameter("pwd");
         
        //get servlet config init params
        String user = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");
        //logging example
        log("Request params: usr="+usr+"::pwd="+pwd);
         
        if(user.equals(usr) && password.equals(pwd)){
            response.sendRedirect("LoginSuccess.jsp");
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>Incorrect username password combination</font>");
            rd.include(request, response);
             
        }
         
    }
 
}