package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.User;
import shop.main.data.entity.UserRole;
import shop.main.data.service.UserRoleService;
import shop.main.data.service.UserService;
import shop.main.utils.Constants;
import shop.main.validation.FormValidationGroup;

@Controller
@RequestMapping(value = { ADMIN_PREFIX })
public class AdminUserController extends AdminController {

	@Autowired
	ServletContext context;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(value = "users")
	public String usersList(Model model) {
		loadTableData("", null, null, null, 1, PAGE_SIZE, model);
		model.addAttribute("roleList", getRoleTypes());
		return "../admin/users/users";
	}

	@RequestMapping(value = "/findUsers", method = RequestMethod.POST)
	public String findUsers(@RequestParam String name, @RequestParam String status, @RequestParam String email,
			@RequestParam String role, @RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {
		loadTableData(name, status, email, role, current, pageSize, model);
		return "../admin/users/_table";

	}

	private void loadTableData(String name, String status, String email, String role, Integer current, Integer pageSize,
			Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("userList", userService.findAll(name, status, email, role, pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, userService.countAll(name, status, email, role));
	}

	@RequestMapping(value = "user", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Validated(FormValidationGroup.class) User user, Model model,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()
				|| (user.getId() == null && (user.getPassword() == null || user.getPassword().isEmpty()))) {
			redirectAttributes.addFlashAttribute("errorMessage", "Password can not be empty!");
			model.addAttribute("errorSummary", "Password can not be empty!");
			return "../admin/users/edit_user";
		} else {
			if (user.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "User added successfully!");
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				System.out.println("*");
				for (UserRole role : user.getUserRole()) {
					role.setUser(user);
					System.out.println(role.getRole() + " is the role");
				}
				System.out.println("*");
				userService.save(user);

			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "User updated successfully!");
				for (UserRole role : user.getUserRole()) {
					role.setUser(user);
					System.out.println(role.getRole() + " is the role");
				}
				User originalUser = userService.fingUserById(user.getId());
				user.setPassword(originalUser.getPassword());

				userService.save(user);
			}

			return "redirect:" + ADMIN_PREFIX + "users";
		}

	}

	@RequestMapping(value = "user/add", method = RequestMethod.GET)
	public String addUser(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("urlError", "");
		model.addAttribute("roleList", getRoleTypes());
		return "../admin/users/edit_user";
	}

	@RequestMapping(value = "user/{id}/update", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") long id, Model model) {
		User user = userService.fingUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("urlError", "");
		model.addAttribute("roleList", getRoleTypes(user));

		return "../admin/users/edit_user";
	}

	@RequestMapping(value = "user/{id}/delete", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		userService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Category deleted successfully!");
		// TODO delete images
		return "redirect:" + ADMIN_PREFIX + "users";
	}

	private UserRole[] getRoleTypes() {
		UserRole[] roleTypes = new UserRole[Constants.RoleType.values().length];
		for (int i = 0; i < Constants.RoleType.values().length; i++) {
			roleTypes[i] = (new UserRole(Constants.RoleType.values()[i].name()));
		}
		return roleTypes;

	}

	private UserRole[] getRoleTypes(User user) {
		Set<UserRole> roleSet = new HashSet<UserRole>(user.getUserRole());
		Set<String> roleLabels = roleSet.stream().map(UserRole::getRole).collect(Collectors.toSet());
		String[] labels = Stream.of(Constants.RoleType.values()).map(Constants.RoleType::name).toArray(String[]::new);
		if (user.getUserRole().size() < labels.length) {
			for (int i = 0; i < labels.length; i++) {
				if (!roleLabels.contains(labels[i])) {
					roleSet.add(new UserRole(user, labels[i]));
				}
			}
		}

		return roleSet.toArray(new UserRole[roleSet.size()]);
	}
}
