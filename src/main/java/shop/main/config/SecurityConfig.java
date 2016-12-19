package shop.main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationDetailsService")
	UserDetailsService authenticationDetailsService;
	
	@Autowired
	private DataSource dataSourceMysql;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/**
		 * in memory authentication by setting username and password
		 */

//		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");

//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		auth.userDetailsService(authenticationDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	MyAuthenticationSuccessHandler successHandler ;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/resources/**").permitAll()
		.antMatchers("/user/**").access("hasRole('ROLE_USER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.and()
			.formLogin().loginPage("/login")
			.defaultSuccessUrl("/user/cabinet")
//			.loginSuccessHandler(logoutSuccessHandler)
			.failureUrl("/accessDenied")
			.successHandler(successHandler)
			.usernameParameter("username").passwordParameter("password")
		.and()
            .exceptionHandling().accessDeniedPage("/")
		.and()
			.logout()
//			.logoutSuccessUrl("/login?logout")
			.logoutSuccessUrl("/"); 
		

	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/resources/**");

	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
