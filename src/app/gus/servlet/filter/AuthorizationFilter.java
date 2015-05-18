package app.gus.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
 
@WebFilter("/AuthorizationFilter")
public class AuthorizationFilter implements Filter {
 
    public void init(FilterConfig fConfig) throws ServletException {
    }
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    }
 
    public void destroy() {
        //close any resources here
    }
 
}
