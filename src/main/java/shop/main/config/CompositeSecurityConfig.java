/*package shop.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class CompositeSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		*//**
		 * in memory authentication by setting username and password
		 *//*
				
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");	
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
	}
	
	
	@Configuration
    @Order(1)
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
		
					
			@Override
			protected void configure(HttpSecurity http) throws Exception {
				
				http
				.requestMatcher(new AntPathRequestMatcher("/admin/**"))
			      .csrf().disable().authorizeRequests()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.and()
					.formLogin().loginPage("/loginadmin")
					.defaultSuccessUrl("/admin/welcome")
					.failureUrl("/accessDenied")
					.usernameParameter("username").passwordParameter("password")				
				.and()
					.logout().logoutSuccessUrl("/"); 
				
		
			}
		
			@Override
			public void configure(WebSecurity web) throws Exception {
		
				web.ignoring().antMatchers("/resources/**");
		
			}
		}
	
	@Configuration    
	public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {
				
			@Override
			protected void configure(HttpSecurity http) throws Exception {
				
				http.requestMatcher(new AntPathRequestMatcher("/user/**"))
			      .csrf().disable().authorizeRequests()
				.antMatchers("/user/**").access("hasRole('ROLE_USER')")
				.and()
					.formLogin().loginPage("/login")
					.defaultSuccessUrl("/user/cabinet")
					.failureUrl("/accessDenied")
					.usernameParameter("username").passwordParameter("password")				
				.and()
					.logout().logoutSuccessUrl("/"); 
				
		
			}
		
			@Override
			public void configure(WebSecurity web) throws Exception {
		
				web.ignoring().antMatchers("/resources/**");
		
			}
		}
	
	
	
}
*/