package shop.main.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		setDefaultFailureUrl("/login");

		super.onAuthenticationFailure(request, response, exception);

		// Locale locale = localeResolver.resolveLocale(request);
		String errorMessage = "BadCredentials";
		// String errorMessage = messages.getMessage("message.badCredentials",
		// null, locale);

		if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
			errorMessage = "User is disabled";
		} else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
			errorMessage = "User account has expired";
		}

		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
	}
}
