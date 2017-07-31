package shop.main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import shop.main.data.entity.Product;
import shop.main.data.entity.User;
import shop.main.data.entity.WishList;
import shop.main.data.mongo.OrderRepository;
import shop.main.data.service.WishListService;
import shop.main.utils.URLUtils;
import shop.main.validation.CabinetValidationGroup;

@Controller
public class UserCabinetController extends FrontController {

	private static final Integer ORDERS_PAGE_SIZE = 2;
	private static final Integer WISHLIST_PAGE_SIZE = 2;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	protected WishListService wishListService;

	@RequestMapping(value = "/user/cabinet")
	public String cabinet(Model model) {
		org.springframework.security.core.userdetails.User authorizeduser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String username = authorizeduser.getUsername();
		User user = userService.findByUsername(username);
		user.setPassword(null);
		model.addAttribute("user", user);
		loadOrderTableData(username, 1, ORDERS_PAGE_SIZE, model);
		loadProductTableData(username, 1, WISHLIST_PAGE_SIZE, model);
		return "user/cabinet";
	}

	@RequestMapping(value = "/findOrder", method = RequestMethod.POST)
	public String findOrder(Integer current, Integer pageSize, Model model) {
		org.springframework.security.core.userdetails.User authorizeduser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String username = authorizeduser.getUsername();
		loadOrderTableData(username, current, pageSize, model);
		return "user/cabinet/_orders_tab";

	}

	@RequestMapping(value = "/findProduct", method = RequestMethod.POST)
	public String findProduct(Integer current, Integer pageSize, Model model) {
		org.springframework.security.core.userdetails.User authorizeduser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String username = authorizeduser.getUsername();
		loadProductTableData(username, current, pageSize, model);
		return "user/cabinet/_wishlist_tab";

	}

	private void loadProductTableData(String username, Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		List<WishList> wishlist = wishListService.findByUsername(username, pageable);
		if (wishlist != null && !wishlist.isEmpty()) {
			List<Product> products = wishlist.stream().map(WishList::getProduct).collect(Collectors.toList());
			products.stream().forEach(p -> p.setImage(URLUtils.getProductImage(context, p.getId())));
			model.addAttribute("products", products);
			addProductPaginator(model, current, pageSize, wishListService.countByUsername(username));
		}

	}

	private void loadOrderTableData(String username, Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);

		model.addAttribute("orders", orderRepository.findByUsername(username, pageable).getContent());
		model.addAttribute("current_order", current);
		model.addAttribute("pageSize_order", pageSize);
		addOrderPaginator(model, current, pageSize, orderRepository.countByUsername(username));
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
