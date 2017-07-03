package shop.main.controller.admin;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;
import shop.main.utils.Constants;

@Controller
@RequestMapping(value = { "/a", "/manager" })
public class AdminOrderController extends AdminController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/orders")
	public String ordersList(Model model) {

		model.addAttribute("orders", orderRepository.findAll());
		return "../admin/orders/orders";
	}

	@RequestMapping(value = "/order/{id}/update", method = RequestMethod.GET)
	public String editOrder(@PathVariable("id") String id, Model model) {
		Order order = orderRepository.findOne(id);
		model.addAttribute("order", order);
		model.addAttribute("countryList", Constants.getCountryList());

		return "../admin/orders/edit_order";
	}

	@RequestMapping(value = "/order/{id}/delete", method = RequestMethod.GET)
	public String deleteOrder(@PathVariable("id") String id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		orderRepository.delete(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Order deleted successfully!");
		return "redirect:" + getUrlPrefix(request) + "orders";
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("order") @Valid Order order, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/orders/edit_order";
		} else {
			redirectAttributes.addFlashAttribute("flashMessage", "Product updated successfully!");
			Order prevOrder = orderRepository.findOne(order.getOrderId());
			order.setDate(prevOrder.getDate());
			if (order.getDate() == null) {
				order.setDate(new Date());
			}
			order.setProduct_list(prevOrder.getProduct_list());
			orderRepository.save(order);

			return "redirect:" + getUrlPrefix(request) + "orders";
		}

	}
}
