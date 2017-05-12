package shop.main.controller;

import java.util.Arrays;
import java.util.List;
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

import shop.main.data.mongo.Order;
import shop.main.data.objects.Product;
import shop.main.data.objects.Review;
import shop.main.data.service.ProductService;
import shop.main.data.service.ReviewService;
import shop.main.utils.URLUtils;

@Controller
public class AjaxFrontController implements ResourceLoaderAware
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
	 private ReviewService reviewService;
	
	@RequestMapping(value = "/createReview",method = RequestMethod.POST/*, consumes = {"application/json;charset=utf-8"}*/)
	public @ResponseBody String createReview(Review review) {
		System.out.println("*");
		System.out.println("request is "+review.toString());
		System.out.println("*");	
		String errorMessage="";
		if(review.getUserName()==null || review.getUserName().equals("")){
			errorMessage+="User name is required! ";
		}
		if(review.getUserEmail()==null || review.getUserEmail().equals("")
				|| review.getUserEmail().indexOf("@")<0){
			errorMessage+="Valid email is required!";
		}
		if(errorMessage.equals("")){
			reviewService.save(review);
			productService.updateRating(review.getProduct().getId());
			return "SUCCESS";
		}else{
			return errorMessage;
		}
	}
	
	@RequestMapping(value="/chooseFilter",method=RequestMethod.POST)
    public String chooseFilter(@RequestParam(value="myArray[]") String[] filters, Model model){
		 	List<Long> filterList = Arrays.asList(filters[0].split(",")).stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
		 	
		 	List<Long> categoryList = Arrays.asList(filters[1].split(",")).stream()
	                 .map(Long::valueOf)
	                 .collect(Collectors.toList());
			
			List<Product> products = productService.findFilteredProductsInCategory(filterList, categoryList);
			products.stream().forEach(p -> p.setImage(URLUtils.getProductImage(context, p.getId())));			
			model.addAttribute("products",products);
			
         return "products"; 
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
		
		return "template_parts/cart";
	}
	
	@RequestMapping(value="/removeQuantity", method=RequestMethod.POST)	
	public String removeQuantity(@RequestBody String sku, HttpServletRequest request, Model model)
	{
		Order order = getOrCreateOrder(request);		
		order.removeQuantity(sku);
		
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
			
	 
}
