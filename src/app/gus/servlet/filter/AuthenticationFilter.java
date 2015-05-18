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
 
@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {
 
    public void init(FilterConfig fConfig) throws ServletException {
    }
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
         
        String uri = req.getRequestURI();
         
        HttpSession session = req.getSession(false);
         
        if(session == null && !(uri.endsWith("login.html") || uri.endsWith("login-servlet"))){
        	System.out.println("AuthenticationFilter :: unauthenticated request for resource: "+uri);
            res.sendRedirect("/login.html");
        }else if (uri.contains("page.jsp")){ //block direct access to page.jsp
        	System.out.println("AuthenticationFilter :: attempted direct access to resource: "+uri);
        	res.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            chain.doFilter(request, response);
        }
    }
 
    public void destroy() {
    }
 
}
