package shop.main.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import shop.main.data.entity.Category;
import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.entity.Review;
import shop.main.data.entity.StaticPage;
import shop.main.utils.URLUtils;

@Controller
public class PagesController extends FrontController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PagesController.class);

	@RequestMapping(value = "/displayusersmysql")
	public ModelAndView displayUsers(Principal principal) {
		// mysql database
		jdbcTemplate = new JdbcTemplate(dataSourceMysql);

		List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT*FROM USER");

		List<String> data = new ArrayList<String>();
		for (Map<String, Object> user : users) {
			data.add("username: " + user.get("username"));
			LOGGER.debug("username: " + user.get("username"));
		}
		return new ModelAndView("db_test/embeded_db_test", "users", data);
	}

	@RequestMapping(value = "/")
	public String mainPage(Model model) {
		System.out.println("***************************main page");
		List<Product> products = productService.findAllFeatured();
		for (Product p : products) {
			p.setImage(URLUtils.getProductImage(context, p.getId()));
		}
		model.addAttribute("products", products);
		model.addAttribute("images", URLUtils.getMinPageImages(context));
		System.out.println("end***************************main page" + LocalDateTime.now());
		return "index";
	}

	@RequestMapping(value = "/product")
	public String displayProduct(Model model) {
		Product product = productService.fingProductById(0L);
		// System.out.println(data.toString());

		addMenuItems(model);
		model.addAttribute("product", product);
		return "product";
	}

	@RequestMapping(value = "/products/{url}")
	public String displayProductByUrl(@PathVariable("url") String url, Model model) {
		System.out.println("url is " + url);

		Product product = productService.fingProductByUrl(url);
		// System.out.println(data.toString());
		if (product.getCategory() != null) {
			System.out.println("add breadcrumbs");
			List<Category> breadCrumbs = new ArrayList<Category>();
			addBreadCrumb(product.getCategory(), breadCrumbs);
			model.addAttribute("breadCrumbs", breadCrumbs);
		}

		addMenuItems(model);
		model.addAttribute("product", product);
		Review newReview = new Review();
		newReview.setProduct(product);
		newReview.setRating(5);
		model.addAttribute("review", newReview);
		if (product.getMetaTitle() != null && !product.getMetaTitle().equals("")) {
			model.addAttribute("metaTitle", product.getMetaTitle());
		} else {
			model.addAttribute("metaTitle",
					"JShop - " + product.getName() + " - " + product.getCategory().getCategoryName());
		}
		if (product.getMetaDescription() != null && !product.getMetaDescription().equals("")) {
			model.addAttribute("metaDescription", product.getMetaDescription());
		} else {
			model.addAttribute("metaDescription", "JShop - " + product.getName() + " - " + product.getShortDesc());
		}
		model.addAttribute("images", URLUtils.getProductImages(context, product.getId()));
		model.addAttribute("mainImage", URLUtils.getProductImage(context, product.getId()));

		org.springframework.security.core.userdetails.User authorizeduser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		boolean savedToWishList = false;
		if (authorizeduser != null) {
			String username = authorizeduser.getUsername();
			if (!wishListService.findByProductSKUAndUsername(product.getSku(), username).isEmpty()) {
				savedToWishList = true;
			}
		}
		model.addAttribute("savedToWishList", savedToWishList);
		return "product";
	}

	@RequestMapping(value = "/category")
	public String categoriesList(Model model) {
		List<Product> products = productService.findAllActiveWithinActiveCategory();
		products.stream().forEach(p -> p.setImage(URLUtils.getProductImage(context, p.getId())));
		model.addAttribute("products", products);
		model.addAttribute("menu", categoryService.findAllParentCategories());
		return "category";
	}

	@RequestMapping(value = "/{url}")
	public String displayPageByUrl(@PathVariable("url") String url, Model model) throws Exception {

		System.out.println("url is " + url);
		Category category = categoryService.fingCategoryByUrl(url);
		StaticPage page = staticPageService.findOneByURL(url);
		if (category != null) {
			displayCategoryByUrl(category, model);
			return "category";
		} else if (page != null) {
			model.addAttribute("page", page);
			return "staticPage";
		} else {
			// return "redirect:/";
			throw new Exception("Ты дурак!");
		}

	}

	public void displayCategoryByUrl(Category category, Model model) {

		// Category category = categoryService.fingCategoryByUrl(url);
		// if(category!=null){

		List<Category> breadCrumbs = new ArrayList<Category>();
		addBreadCrumb(category, breadCrumbs);
		model.addAttribute("breadCrumbs", breadCrumbs);
		model.addAttribute("categoryURL", category.getCategoryURL());

		List<Long> childCategories = new ArrayList<Long>();
		createChildrenList(category, childCategories);
		System.out.println("childCategories " + childCategories.toString());
		model.addAttribute("childCategories", StringUtils.join(childCategories, ","));

		List<Product> products = productService.findProductsInCategory(childCategories);
		products.stream().forEach(p -> p.setImage(URLUtils.getProductImage(context, p.getId())));
		model.addAttribute("products", products);

		model.addAttribute("menu", categoryService.findAllParentCategories());

		List<ProductOption> categoryOptions = productOptionService.findOptionsByCategoryList(childCategories);
		if (!categoryOptions.isEmpty()) {
			System.out.println("*** categoryOptions " + categoryOptions.size());
			Map<String, List<ProductOption>> categoryOptionsMap = categoryOptions.stream()
					.collect(Collectors.groupingBy(c -> c.getOption().getOptionGroup().getOptionGroupName()));
			model.addAttribute("categoryOptions", categoryOptionsMap);
		}

		// }

	}

	@RequestMapping(value = "/{url}/filters={filters:.+}")
	public String displayCategoryByUrlWithFilters(@PathVariable("url") String url,
			@PathVariable("filters") String filters, Model model) {
		System.out.println("url is " + url + ", filters are " + filters);
		// check if category exists
		Category category = categoryService.fingCategoryByUrl(url);
		if (category != null) {

			List<Category> breadCrumbs = new ArrayList<Category>();
			addBreadCrumb(category, breadCrumbs);
			model.addAttribute("breadCrumbs", breadCrumbs);
			model.addAttribute("categoryURL", category.getCategoryURL());

			List<Long> childCategories = new ArrayList<Long>();
			createChildrenList(category, childCategories);
			System.out.println("childCategories " + childCategories.toString());
			model.addAttribute("childCategories", StringUtils.join(childCategories, ","));

			List<Long> filterList = Arrays.asList(filters.split(",")).stream().map(Long::valueOf)
					.collect(Collectors.toList());

			List<Product> products = productService.findFilteredProductsInCategory(filterList, childCategories);
			products.stream().forEach(p -> p.setImage(URLUtils.getProductImage(context, p.getId())));
			model.addAttribute("products", products);

			model.addAttribute("menu", categoryService.findAllParentCategories());

			List<ProductOption> categoryOptions = productOptionService.findOptionsByCategoryList(childCategories);
			if (!categoryOptions.isEmpty()) {
				System.out.println("*** categoryOptions " + categoryOptions.size());
				Map<String, List<ProductOption>> categoryOptionsMap = categoryOptions.stream()
						.collect(Collectors.groupingBy(c -> c.getOption().getOptionGroup().getOptionGroupName()));
				model.addAttribute("categoryOptions", categoryOptionsMap);
			}

			return "category";
		} else {
			return "redirect:/";
		}

	}

	private void addMenuItems(Model model) {
		List<Category> data = categoryService.findAllParentCategories();
		model.addAttribute("menu", data);
	}

	private void addBreadCrumb(Category category, List<Category> breadcrumbList) {
		breadcrumbList.add(0, category);
		System.out.println("added category " + category.getCategoryName());
		if (category.getParentCategory() != null) {
			addBreadCrumb(category.getParentCategory(), breadcrumbList);
		}
	}

	private void createChildrenList(Category category, List<Long> childrenList) {
		childrenList.add(category.getId());
		System.out.println("added category " + category.getCategoryName());
		if (!category.getChildren().isEmpty()) {
			category.getChildren().stream().forEach(c -> createChildrenList(c, childrenList));
		}
	}
}
