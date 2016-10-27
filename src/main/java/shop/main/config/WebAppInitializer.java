package shop.main.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException{
		
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(AppContextConfig.class);
		
		container.addListener(new ContextLoaderListener(rootContext));
		
//		DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
		ServletRegistration.Dynamic registration = container.addServlet("dispatcherServlet", dispatcherServlet);
//		container.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");
		
		/*FilterRegistration.Dynamic securityFilter = container.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
	    securityFilter.addMappingForUrlPatterns(null, false, "/*");*/
		
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
//		registration.setMultipartConfig(new MultipartConfigElement("/home/work/tmp"));
		
	}
}
