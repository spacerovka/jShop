package shop.main.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.main.data.entity.Discount;
import shop.main.data.entity.OrderUserWrapper;
import shop.main.data.entity.Product;
import shop.main.data.entity.User;
import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;
import shop.main.data.service.DiscountService;
import shop.main.data.service.ProductService;
import shop.main.utils.Constants;
import shop.main.utils.URLUtils;
import shop.main.validation.EmailExistsException;

@Controller
public class CartController extends FrontController implements ResourceLoaderAware {
	private ResourceLoader resourceLoader;

	@Autowired
	ServletContext context;

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DiscountService discountService;

	@RequestMapping(value = "/cart")
	public String cartPage(Model model, HttpServletRequest request) {
		Order currentOrder = getOrCreateOrder(request);
		if (currentOrder.getSum().intValue() != 0) {
			model.addAttribute("order", getOrCreateOrder(request));
		}
		return "cart";
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkoutPage(Model model, HttpServletRequest request) {
		Order currentOrder = getOrCreateOrder(request);
		if (currentOrder.getSum().intValue() != 0) {
			OrderUserWrapper wrapper = new OrderUserWrapper(currentOrder, new User());
			User authorizeduser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (authorizeduser != null) {
				String username = authorizeduser.getUsername();
				shop.main.data.entity.User savedUser = userService.findByUsername(username);
				if (savedUser != null) {
					wrapper.setUser(savedUser);
				}
			}
			model.addAttribute("orderUserWrapper", wrapper);
			model.addAttribute("countryList", Constants.getCountryList());
			return "checkout";
		} else {
			return "redirect:/cart";
		}

	}

	/**
	 * order processing
	 */

	@RequestMapping(value = "/addtocart", method = RequestMethod.POST)
	public String addToCart(@RequestParam String sku, HttpServletRequest request, Model model) {
		Order order = getOrCreateOrder(request);
		Product product = productService.findProductBySKU(sku);
		System.out.println(product.toString());
		product.setImage(URLUtils.getProductImage(context, product.getId()));
		order.addItem(product);
		product.setImage(URLUtils.getProductImage(context, product.getId()));
		model.addAttribute("product", product);
		return "product_added_success";
	}

	@RequestMapping(value = "/updateCartItemCount", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCartItemCount(HttpServletRequest request, Model model) {
		Order order = getOrCreateOrder(request);
		int itemCount = order.getItemCount();
		System.out.println("* * * " + itemCount);
		return String.valueOf(itemCount);

	}

	@RequestMapping(value = "/addQuantity", method = RequestMethod.POST)
	public String addQuantity(@RequestParam String sku, HttpServletRequest request, Model model) {
		Order order = getOrCreateOrder(request);
		order.addQuantity(sku);
		model.addAttribute("order", order);
		return "template_parts/cart";
	}

	@RequestMapping(value = "/removeQuantity", method = RequestMethod.POST)
	public String removeQuantity(@RequestParam String sku, HttpServletRequest request, Model model) {
		Order order = getOrCreateOrder(request);
		order.removeQuantity(sku);
		model.addAttribute("order", order);
		return "template_parts/cart";
	}

	@RequestMapping(value = "/removeProduct", method = RequestMethod.POST)
	public String removeProduct(@RequestParam String sku, HttpServletRequest request, Model model) {
		Order order = getOrCreateOrder(request);
		order.removeProduct(sku);
		model.addAttribute("order", order);
		return "template_parts/cart";
	}

	@RequestMapping(value = "/addCoupon", method = RequestMethod.POST)
	public String addCoupon(@RequestParam String code, HttpServletRequest request, Model model) {
		Order order = getOrCreateOrder(request);
		if (order.getDiscount() <= 0) {
			Discount discount = discountService.findByCoupon(code);
			if (discount != null) {
				order.addCoupon(discount.getDiscount(), discount.getSalename());
			} else {
				model.addAttribute("couponError", "Discount not found - " + order.getDiscountName());
			}
		} else {
			model.addAttribute("couponError", "This order already has a discount - " + order.getDiscountName());
		}
		model.addAttribute("order", order);
		return "template_parts/cart";
	}

	protected Order getOrCreateOrder(HttpServletRequest request) {
		System.out.println("* * * getOrCreateOrder");
		Order order = null;
		order = (Order) request.getSession().getAttribute("CURRENT_ORDER");
		if (order == null) {
			order = new Order();
			request.getSession().setAttribute("CURRENT_ORDER", order);
		}
		return order;
	}

	@RequestMapping(value = "/orderPlaced", method = RequestMethod.POST)
	public String orderPlaced(OrderUserWrapper orderUserWrapper, HttpServletRequest request, Model model) {

		if (orderUserWrapper.isCreateUser()) {
			orderUserWrapper.getUser().setEnabled(true);
			ArrayList<String> errors = new ArrayList<String>();
			// validate username and email
			Set<ConstraintViolation<User>> results = Validation.buildDefaultValidatorFactory().getValidator()
					.validate(orderUserWrapper.getUser(), Default.class);
			for (ConstraintViolation<User> error : results) {
				System.out.println("+ + +" + error.getMessage() + " " + error.getInvalidValue());
				errors.add(error.getMessage());
			}
			User registered = null;
			if (errors.isEmpty()) {
				registered = createUserAccount(orderUserWrapper.getUser());
			}
			if (registered == null) {
				model.addAttribute("orderUserWrapper", orderUserWrapper);
				model.addAttribute("countryList", Constants.getCountryList());
				if (errors.isEmpty()) {
					errors.add("User with this email is already registered!");
				}
				model.addAttribute("errorSummary", errors);
				return "checkout";
			}
			// TODO Generate the VerificationToken for the User and persist it
			// TODO Send out the email message for account confirmation – which
			// includes a confirmation link with the VerificationToken’s value

		} else {
			User authorizeduser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (authorizeduser != null) {
				orderUserWrapper.setUsername(authorizeduser.getUsername());
			}
		}

		Order currentOrder = getOrCreateOrder(request);
		if (currentOrder.getSum().intValue() != 0) {
			Order.getDataFromWrapper(currentOrder, orderUserWrapper);
			String orderCount = String.valueOf(orderRepository.findAll().size());
			if (orderCount.length() < 8) {
				orderCount = "0" + orderCount;
			}
			currentOrder.setNumber(
					Calendar.getInstance().get(Calendar.YEAR) + orderCount + currentOrder.getSum().intValue());
			currentOrder.setDate(new Date());
			orderRepository.save(currentOrder);
			request.getSession().setAttribute("CURRENT_ORDER", null);
			model.addAttribute("order", currentOrder);

			return "order_placed";
		} else {
			return "redirect:/cart";
		}
	}

	private User createUserAccount(User accountDto) {
		User registered = null;
		try {
			registered = userService.registerNewUserAccount(accountDto);
		} catch (EmailExistsException e) {
			return null;
		}
		return registered;
	}

	@RequestMapping(value = "/orderPlaced", method = RequestMethod.GET)
	public String orderPlacedRedirect() {
		return "redirect:/cart";

	}

}
