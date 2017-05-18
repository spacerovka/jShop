package shop.main.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderProduct;
import shop.main.data.mongo.OrderRepository;
import shop.main.data.objects.Product;
import shop.main.data.objects.Review;
import shop.main.data.service.ProductService;
import shop.main.data.service.ReviewService;
import shop.main.utils.URLUtils;

@Controller
public class CartController implements ResourceLoaderAware
{
	private ResourceLoader resourceLoader;
	
	 @Autowired
	    ServletContext context;

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource getResource(String location){
		return resourceLoader.getResource(location);
	}	
	
	@Autowired
	 private ProductService productService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@RequestMapping(value = "/cart")
	public String cartPage(Model model, HttpServletRequest request) {
		
		model.addAttribute("order", getOrCreateOrder(request));
		return "cart";
	}
	
	/**
	 * order processing
	 */
	
	@RequestMapping(value="/addtocart", method=RequestMethod.POST)	
	public String addToCart(@RequestParam String sku, HttpServletRequest request, Model model)
	{
		Order order = getOrCreateOrder(request);
		Product product = productService.findProductBySKU(sku);
		System.out.println(product.toString());
		product.setImage(URLUtils.getProductImage(context, product.getId()));
		order.addItem(product);
		product.setImage(URLUtils.getProductImage(context, product.getId()));
		model.addAttribute("product",product);
		return "product_added_success";
	}

	
	@RequestMapping(value="/updateCartItemCount", method=RequestMethod.GET,produces = "application/json")	
	public @ResponseBody String getCartItemCount(HttpServletRequest request, Model model)
	{
		Order order = getOrCreateOrder(request);
		int itemCount = order.getItemCount();
		System.out.println("* * * "+itemCount);
		return String.valueOf(itemCount);
		
	}
	
	@RequestMapping(value="/addQuantity", method=RequestMethod.POST)	
	public String addQuantity(@RequestParam String sku, HttpServletRequest request, Model model)
	{
		Order order = getOrCreateOrder(request);		
		order.addQuantity(sku);
		model.addAttribute("order", order);
		return "template_parts/cart";
	}
	
	@RequestMapping(value="/removeQuantity", method=RequestMethod.POST)	
	public String removeQuantity(@RequestParam String sku, HttpServletRequest request, Model model)
	{
		Order order = getOrCreateOrder(request);		
		order.removeQuantity(sku);
		model.addAttribute("order", order);
		return "template_parts/cart";
	}
	
	protected Order getOrCreateOrder(HttpServletRequest request)
	{
		System.out.println("* * * getOrCreateOrder");
		Order order = null;
		order = (Order) request.getSession().getAttribute("CURRENT_ORDER");
		if(order == null){
			order = new Order();
			request.getSession().setAttribute("CURRENT_ORDER", order);
		}
		return order;
	}
	
	@RequestMapping(value = "/orderPlaced")//, method=RequestMethod.POST
	public ModelAndView orderPlaced(HttpServletRequest request, Model model) {
		//create order from session
		//save order
		//add order in model
		//if order!=null return success page
		//if order is null redirect to main
		
		Order order = new Order();	
		
		order.setSum(new BigDecimal(335+395));
		String orderCount = String.valueOf(orderRepository.findAll().size());
		if(orderCount.length()<8){
			orderCount="0"+orderCount;
		}
		order.setNumber(Calendar.getInstance().get(Calendar.YEAR)+orderCount+order.getSum().intValue());
		
		OrderProduct prod1= new OrderProduct("Lovely thing", new BigDecimal(33.5), 10, "987654L");
		prod1.setProductId(987654L);		
		OrderProduct prod2= new OrderProduct("Pink thing", new BigDecimal(39.5), 10,"8765490L");
		prod2.setProductId(8765490L);

		Map<String, OrderProduct> products = new HashMap<String, OrderProduct>();
		products.put(prod1.getProduct_SKU(), prod1);
		products.put(prod2.getProduct_SKU(), prod2);
		order.setProduct_list(products);		
		
        orderRepository.save(order);
        List<Order> data = orderRepository.findAll();        
				
		return new ModelAndView("order_placed", "orders", data);
	}
			
	 
}
