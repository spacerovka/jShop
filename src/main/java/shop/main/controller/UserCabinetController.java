package shop.main.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.User;
import shop.main.data.mongo.OrderRepository;
import shop.main.validation.CabinetValidationGroup;

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

	@RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") @Validated(CabinetValidationGroup.class) User user, Model model,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "redirect:/user/cabinet";
		} else {
			User originalUser = userService.findByUsername(user.getUsername());
			ArrayList<String> errors = new ArrayList<String>();

			user.setPassword(originalUser.getPassword());
			user.setEmailVerified(originalUser.isEmailVerified());
			user.setRegisterDate(originalUser.getRegisterDate());
			userService.save(user);
			redirectAttributes.addFlashAttribute("flashMessage", "User updated successfully!");
		}

		return "redirect:/user/cabinet";
	}

	@RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public String updatePassword(@RequestParam("password") String password,
			@RequestParam("oldpassword") String oldPassword, HttpServletRequest request, Model model) {
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (!userService.checkIfValidOldPassword(user, oldPassword)) {
			return "<div class=\"alert alert-danger\">" + "<strong>Warning!</strong> Invalid old password" + "</div>";
		}
		userService.changeUserPassword(user, password);
		return "<div class=\"alert alert-success\">" + "<strong>Request success!</strong> Password updated." + "</div>";
	}

}
