package shop.main.controller;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.User;
import shop.main.data.mongo.OrderRepository;
import shop.main.validation.CabinetValidationGroup;
import shop.main.validation.FormValidationGroup;

@Controller
public class UserCabinetController extends FrontController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/user/cabinet")
	public String cabinet(Model model) {
		org.springframework.security.core.userdetails.User authorizeduser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String username = authorizeduser.getUsername();
		User user = userService.findByUsername(username);
		user.setPassword(null);
		model.addAttribute("user", user);
		model.addAttribute("orders", orderRepository.findByUsername(username));
		return "user/cabinet";
	}

	@RequestMapping(value = "/accessdenied")
	public String accessDenied(Model model) {

		return "accessDenied";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") @Validated(CabinetValidationGroup.class) User user, Model model,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "redirect:/user/cabinet";
		} else {
			User originalUser = userService.findByUsername(user.getUsername());
			ArrayList<String> errors = new ArrayList<String>();

			if (user.getPassword() != null || user.getPassword().equals("")) {
				Set<ConstraintViolation<User>> results = Validation.buildDefaultValidatorFactory().getValidator()
						.validate(user, FormValidationGroup.class);
				if (!results.isEmpty()) {
					for (ConstraintViolation<User> error : results) {
						System.out.println("+ + +" + error.getMessage() + " " + error.getInvalidValue());
						errors.add(error.getMessage());
					}
					redirectAttributes.addFlashAttribute("errorSummary", errors);
					return "redirect:/user/cabinet";
				} else {
					user.setPassword(passwordEncoder.encode(user.getPassword()));
				}

			} else {
				user.setPassword(originalUser.getPassword());
			}
			user.setEmailVerified(originalUser.isEmailVerified());
			user.setRegisterDate(originalUser.getRegisterDate());
			userService.save(user);
			redirectAttributes.addFlashAttribute("flashMessage", "User updated successfully!");
		}

		return "redirect:/user/cabinet";
	}

}
