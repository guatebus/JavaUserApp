package app.gus.servlet.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import app.gus.security.voter.ResourceVoter;
import app.gus.servlet.filter.AuthorizationFilter;

@WebListener
public class ServletListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		AuthorizationFilter authFilter = new AuthorizationFilter();
		//DependencyInjection has to be by setter injection as constructor injection causes exception...
		authFilter.setVoter(new ResourceVoter());
		context.addFilter("AuthorizationFilter", authFilter).addMappingForUrlPatterns(null, false, "/*");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
