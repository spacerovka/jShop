package shop.main.config;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import shop.main.utils.Properties;

/*
 * this configuration class replaces application-context.xml
 * and dispatcher-servlet.xml
 */

@EnableWebMvc
@Configuration
@ComponentScan({ "shop.main.*" })
@Import({SecurityConfig.class})
@PropertySource("classpath:application.properties")
public class AppContextConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/").setViewName("index");
		 registry.addViewController("/user/cabinet").setViewName("user/cabinet");
		 registry.addViewController("/accessDenied").setViewName("accessDenied");
		 registry.addViewController("/admin/welcome").setViewName("admin/welcome");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

	}

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/pages/"+Properties.THEME_NAME+"/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {

		return new PropertySourcesPlaceholderConfigurer();

	}

/*
 * language selection
 */
//	@Bean
//	public MessageSource messageSource() {
//		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//		messageSource.setBasename("lang");
//		messageSource.setDefaultEncoding("UTF-8");
//		return messageSource;
//	}
//
//	@Bean
//	public LocaleChangeInterceptor localeChangeInterceptor() {
//		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//		localeChangeInterceptor.setParamName("language");
//		return localeChangeInterceptor;
//	}
//
//	@Bean
//	public CookieLocaleResolver localeResolver() {
//		CookieLocaleResolver localResolver = new CookieLocaleResolver();
//		localResolver.setDefaultLocale(Locale.ENGLISH);
//		return localResolver;
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//
//		registry.addInterceptor(localeChangeInterceptor());
//	}

	/*
	 * for files upload
	 */
//	@Bean
//	public MultipartResolver multipartResolver() throws IOException {
//		return new StandardServletMultipartResolver();
//	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    return resolver;
	}

	@Bean
	public ResourceLoader resourceLoader() {
		return new DefaultResourceLoader();
	}
	
	@Bean
	public RequestMappingHandlerMapping useTrailingSlash() {
	    return new RequestMappingHandlerMapping() {{ setUseTrailingSlashMatch(true); }};
	}

}
