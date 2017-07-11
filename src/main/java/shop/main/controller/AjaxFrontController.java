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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.main.data.entity.Product;
import shop.main.data.entity.Review;
import shop.main.data.entity.WishList;
import shop.main.data.service.ProductService;
import shop.main.data.service.ReviewService;
import shop.main.data.service.UserService;
import shop.main.data.service.WishListService;
import shop.main.utils.URLUtils;

@Controller
public class AjaxFrontController implements ResourceLoaderAware {
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
	private ReviewService reviewService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected WishListService wishListService;

	@RequestMapping(value = "/createReview", method = RequestMethod.POST/*
																		 * ,
																		 * consumes
																		 * =
																		 * {"application/json;charset=utf-8"}
																		 */)
	public @ResponseBody String createReview(Review review) {
		System.out.println("*");
		System.out.println("request is " + review.toString());
		System.out.println("*");
		String errorMessage = "";
		if (review.getUserName() == null || review.getUserName().equals("")) {
			errorMessage += "User name is required! ";
		}
		if (review.getUserEmail() == null || review.getUserEmail().equals("")
				|| review.getUserEmail().indexOf("@") < 0) {
			errorMessage += "Valid email is required!";
		}
		if (errorMessage.equals("")) {
			reviewService.save(review);
			productService.updateRating(review.getProduct().getId());
			return "SUCCESS";
		} else {
			return errorMessage;
		}
	}

	@RequestMapping(value = "/chooseFilter", method = RequestMethod.POST)
	public String chooseFilter(@RequestParam(value = "myArray[]") String[] filters, Model model) {
		List<Long> filterList = Arrays.asList(filters[0].split(",")).stream().map(Long::valueOf)
				.collect(Collectors.toList());

		List<Long> categoryList = Arrays.asList(filters[1].split(",")).stream().map(Long::valueOf)
				.collect(Collectors.toList());

		List<Product> products = productService.findFilteredProductsInCategory(filterList, categoryList);
		products.stream().forEach(p -> p.setImage(URLUtils.getProductImage(context, p.getId())));
		model.addAttribute("products", products);

		return "products";
	}

	@RequestMapping(value = "/addtowishlist", method = RequestMethod.POST)
	public @ResponseBody String addToWishList(@RequestParam String sku, HttpServletRequest request, Model model) {

		org.springframework.security.core.userdetails.User authorizeduser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		if (authorizeduser != null) {
			String username = authorizeduser.getUsername();
			shop.main.data.entity.User savedUser = userService.findByUsername(username);
			if (savedUser != null) {
				if (wishListService.findByProductSKUAndUsername(sku, username).isEmpty()) {
					Product product = productService.findProductBySKU(sku);
					wishListService.save(new WishList(product, savedUser));
				} else {
					return "Product is already in your wishlist!";
				}
			}
		}

		return "Product added to your wishlist!";
	}

}
