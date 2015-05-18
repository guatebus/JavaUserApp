package app.gus.servlet.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import app.gus.servlet.filter.AuthorizationFilter;

@WebListener
public class ServletListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.addFilter("AuthorizationFilter", new AuthorizationFilter()).addMappingForUrlPatterns(null, false, "/*");;
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
