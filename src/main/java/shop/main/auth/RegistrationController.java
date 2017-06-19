package shop.main.auth;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import shop.main.controller.FrontController;
import shop.main.data.entity.User;
import shop.main.data.service.UserRoleService;
import shop.main.validation.EmailExistsException;
import shop.main.validation.FormValidationGroup;

@Controller
public class RegistrationController extends FrontController {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;

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
			//TODO Generate the VerificationToken for the User and persist it
			//TODO Send out the email message for account confirmation – which includes a confirmation link with the VerificationToken’s value
			
			try {
		        String appUrl = request.getContextPath();
		        eventPublisher.publishEvent(new OnRegistrationCompleteEvent
		          (registered, request.getLocale(), appUrl));
		    } catch (Exception me) {
		    	model.addAttribute("errorSummary", "Validation token error!"+me);
				model.addAttribute("user", user);
				return "registration";		    }
			
			model.addAttribute("flashMessage", "User registered successfully!");
		}

		return "registration";
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
}
