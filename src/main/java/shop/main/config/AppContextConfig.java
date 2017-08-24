package shop.main.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/*
 * this configuration class replaces application-context.xml
 * and dispatcher-servlet.xml
 */

@EnableWebMvc
@Configuration
@ComponentScan({ "shop.main.*" })
@Import({ SecurityConfig.class })
@PropertySource("classpath:application.properties")
public class AppContextConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

	}

	@Bean
	public ThemedResourceViewResolver internalResourceViewResolver() {
		ThemedResourceViewResolver resolver = new ThemedResourceViewResolver();

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
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("lang");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localResolver = new CookieLocaleResolver();
		localResolver.setDefaultLocale(Locale.ENGLISH);
		return localResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(localeChangeInterceptor());
	}

	/*
	 * for files upload
	 */

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	@Bean
	public ResourceLoader resourceLoader() {
		return new DefaultResourceLoader();
	}

	@Bean
	public RequestMappingHandlerMapping useTrailingSlash() {
		return new RequestMappingHandlerMapping() {
			{
				setUseTrailingSlashMatch(true);
			}
		};
	}

}
