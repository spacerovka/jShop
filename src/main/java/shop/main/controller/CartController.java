package shop.main.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.main.data.entity.OrderUserWrapper;
import shop.main.data.entity.Product;
import shop.main.data.entity.User;
import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;
import shop.main.data.service.ProductService;
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
			model.addAttribute("orderUserWrapper", new OrderUserWrapper(currentOrder, new User()));
			model.addAttribute("countryList", getCountryList());
			return "checkout";
		} else {
			return "redirect:/cart";
		}

	}

	private List<String> getCountryList() {

		return new ArrayList<String>(Arrays.asList("Spain", "USA", "Australia"));
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

	@Autowired
	private Validator validator;

	@RequestMapping(value = "/orderPlaced", method = RequestMethod.POST)
	public String orderPlaced(OrderUserWrapper orderUserWrapper, HttpServletRequest request, Model model) {
		// create order from session
		// save order
		// add order in model
		// if order!=null return success page
		// if order is null redirect to cart page
		Order currentOrder = getOrCreateOrder(request);
		if (currentOrder.getSum().intValue() != 0) {
			currentOrder.setUserName(orderUserWrapper.getOrder().getUserName());
			currentOrder.setCountry(orderUserWrapper.getOrder().getCountry());
			currentOrder.setState(orderUserWrapper.getOrder().getState());
			currentOrder.setCity(orderUserWrapper.getOrder().getCity());
			currentOrder.setShipAddress(orderUserWrapper.getOrder().getShipAddress());
			currentOrder.setZip(orderUserWrapper.getOrder().getZip());
			currentOrder.setPhone(orderUserWrapper.getOrder().getPhone());
			currentOrder.setEmail(orderUserWrapper.getOrder().getEmail());
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
					model.addAttribute("countryList", getCountryList());
					if (errors.isEmpty()) {
						errors.add("User with this email is already registered!");
					}
					model.addAttribute("errorSummary", errors);
					return "checkout";
				}
			}

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
