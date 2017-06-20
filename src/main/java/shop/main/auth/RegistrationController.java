package shop.main.auth;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.controller.FrontController;
import shop.main.data.entity.User;
import shop.main.data.entity.VerificationToken;
import shop.main.utils.URLUtils;
import shop.main.validation.EmailExistsException;
import shop.main.validation.FormValidationGroup;

@Controller
public class RegistrationController extends FrontController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment environment;

	@ModelAttribute("CAPTCHA_SITE")
	public String getCaptchaSite(@Value("${google.recaptcha.key.site}") String recaptchaSiteKey) {
		return recaptchaSiteKey;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") @Validated(FormValidationGroup.class) User user,
			BindingResult result, Model model, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			model.addAttribute("user", user);

		} else {
			String response = request.getParameter("g-recaptcha-response");
			System.out.println("response " + response);
			// try {
			// reCaptchaService.processResponse(response);
			// } catch (Exception e) {
			// model.addAttribute("errorSummary", "Captcha exception!");
			// model.addAttribute("user", user);
			// return "registration";
			// }
			User registered = null;
			registered = createUserAccount(user, result);

			if (registered == null) {
				model.addAttribute("errorSummary", "User with this email already registered!");
				model.addAttribute("user", user);
				return "registration";
			}
			try {
				String appUrl = request.getContextPath();
				eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
			} catch (Exception me) {
				model.addAttribute("errorSummary", "Validation token error!" + me);
				model.addAttribute("user", user);
				return "registration";
			}

			model.addAttribute("flashMessage", "User registered successfully!");
		}

		return "registration";
	}

	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

		Locale locale = request.getLocale();

		VerificationToken verificationToken = userService.getVerificationToken(token);
		if (verificationToken == null) {
			String message = "Invalid Token";
			// = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			return "badUser";
		}

		User user = verificationToken.getUser();
		LocalDateTime tooday = LocalDateTime.now();
		if (Duration.between(verificationToken.getExpiryDate(), tooday).toMillis() <= 0) {
			String messageValue = "Token expired!";
			// messages.getMessage("auth.message.expired", null, locale)
			model.addAttribute("token", token);
			model.addAttribute("message", messageValue);
			return "badUser";
		}

		user.setEnabled(true);
		userService.save(user);
		return "redirect:/login";
	}

	@RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
	@ResponseBody
	public String resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
		VerificationToken newToken = userService.generateNewVerificationToken(existingToken);

		User user = userService.getUserByToken(newToken.getToken());
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SimpleMailMessage email = constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
		mailSender.send(email);

		return "Verification details was send to your email.";
	}

	private User createUserAccount(User accountDto, BindingResult result) {
		User registered = null;
		try {
			registered = userService.registerNewUserAccount(accountDto);
		} catch (EmailExistsException e) {
			return null;
		}
		return registered;
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotpassword(Model model) {

		return "forgotPassword";
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public String resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		User user = userService.findUserByEmail(userEmail);
		if (user == null) {
			return "<div class=\"alert alert-danger\">" + "<strong>Warning!</strong> User with email '" + userEmail
					+ "' not found!" + "</div>";
		}
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		mailSender.send(constructResetTokenEmail(URLUtils.getAppUrl(request), request.getLocale(), token, user));
		return "<div class=\"alert alert-success\">"
				+ "<strong>Request success!</strong> Information about password reset has been send to your email."
				+ "</div>";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String showChangePasswordPage(Model model, @RequestParam("id") long id,
			@RequestParam("token") String token) {
		String errors = userService.validatePasswordResetToken(id, token);
		if (errors != null) {
			model.addAttribute("message", errors);
			return "redirect:/login";
		}
		return "resetPassword";
	}

	@RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
	@ResponseBody
	public String savePassword(final Locale locale, @RequestParam("password") String password,
			final RedirectAttributes redirectAttributes) {
		final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userService.changeUserPassword(user, password);
		return "Password was successfuly updated. You can login now.";
	}

	private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale,
			final VerificationToken newToken, final User user) {
		final String confirmationUrl = contextPath + "/registrationonfirm?token=" + newToken.getToken();
		final String message = "To confirm your registration proceed the following link";
		return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
	}

	private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale,
			final String token, final User user) {
		final String url = contextPath + "/changePassword?id=" + user.getId() + "&token=" + token;
		final String message = "Ignore this letter if you don't requested for password reset. To reset password proceed following link.";
		return constructEmail("Reset Password", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, User user) {
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(environment.getProperty("support.email"));
		return email;
	}

}
