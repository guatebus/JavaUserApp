package app.gus.servlet.resource;

import java.io.IOException;

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
 * Resource request endpoint
 */
@WebServlet(
        name = "ResourceServlet" ,
        urlPatterns = { "/resource" })
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = -7565940648825117787L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        System.out.println("ResourceServlet :: Requested resource: " + request.getRequestURI());

        response.sendRedirect("resources/page.jsp");
    }
}