package shop.main.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.Category;
import shop.main.data.entity.ContactUsMessage;
import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.entity.Review;
import shop.main.data.entity.StaticPage;
import shop.main.utils.Constants;
import shop.main.utils.URLUtils;

@Controller
public class PagesController extends FrontController {

	private static final Logger LOGGER = Logger.getLogger(PagesController.class);
	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(value = "/")
	public String mainPage(Model model) {
		LOGGER.debug("***************************main page");
		List<Product> products = productService.findAllFeatured();
		for (Product p : products) {
			p.setImage(URLUtils.getProductImage(context, p.getId()));
		}
		model.addAttribute("products", products);
		model.addAttribute("images", URLUtils.getMinPageImages(context));
		LOGGER.debug("end***************************main page" + LocalDateTime.now());
		return "index";
	}

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

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String saveContactMessage(@ModelAttribute("item") @Valid ContactUsMessage item, BindingResult result,
			Model model, final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			model.addAttribute("item", item);
		} else {
			redirectAttributes.addFlashAttribute("flashMessage", "contactMessage updated successfully!");
			contactService.saveContactUsMessage(item);
			model.addAttribute("item", null);
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			SimpleMailMessage email = constructContactEmail(appUrl, request.getLocale());
			mailSender.send(email);

		}
		return "contactUs";

	}

	private SimpleMailMessage constructContactEmail(final String contextPath, final Locale locale) {

		final String message = "Contact request has been send. Please proceed to admin page to read the message.";
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Resend Registration Token");
		email.setText(message + " \r\n" + contextPath);
		email.setTo(sitePropertyService.findOneByName(Constants.SUPPORT_EMAIL).getContent());
		email.setFrom("JShop@email.support");
		return email;
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String editDiscount(Model model) {

		model.addAttribute("item", new ContactUsMessage());

		return "contactUs";
	}

	@RequestMapping(value = "/product")
	public String displayProduct(Model model) {
		Product product = productService.findProductById(0L);
		// LOGGER.debug(data.toString());

		addMenuItems(model);
		model.addAttribute("product", product);
		return "product";
	}

	@RequestMapping(value = "/products/{url}")
	public String displayProductByUrl(@PathVariable("url") String url, Model model) {
		LOGGER.debug("url is " + url);

		Product product = productService.findProductByUrl(url);
		// LOGGER.debug(data.toString());
		if (product.getCategory() != null) {
			LOGGER.debug("add breadcrumbs");
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
		boolean savedToWishList = false;
		try {
			org.springframework.security.core.userdetails.User authorizeduser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			if (authorizeduser != null) {
				String username = authorizeduser.getUsername();
				if (!wishListService.findByProductSKUAndUsername(product.getSku(), username).isEmpty()) {
					savedToWishList = true;
				}
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		model.addAttribute("savedToWishList", savedToWishList);
		return "product";
	}

	@RequestMapping(value = "/all")
	public String categoriesList(Model model) {

		loadFilterProductTableData(null, null, 1, PAGE_SIZE, model);
		model.addAttribute("menu", categoryService.findAllParentCategories());
		model.addAttribute("categoryURL", "all");
		return "category";
	}

	@RequestMapping(value = "/{url}")
	public String displayPageByUrl(@PathVariable("url") String url, Model model) throws Exception {

		LOGGER.debug("url is " + url);
		Category category = categoryService.findCategoryByUrl(url);
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

		List<Category> breadCrumbs = new ArrayList<Category>();
		addBreadCrumb(category, breadCrumbs);
		model.addAttribute("breadCrumbs", breadCrumbs);
		model.addAttribute("categoryURL", category.getCategoryURL());

		List<Long> childCategories = new ArrayList<Long>();
		createChildrenList(category, childCategories);
		LOGGER.debug("childCategories " + childCategories.toString());
		model.addAttribute("childCategories", StringUtils.join(childCategories, ","));

		// loadCategoryProductTableData(childCategories, 1, PAGE_SIZE, model);
		loadFilterProductTableData(null, childCategories, 1, PAGE_SIZE, model);
		model.addAttribute("menu", categoryService.findAllParentCategories());

		List<ProductOption> categoryOptions = productOptionService.findOptionsByCategoryList(childCategories);
		if (!categoryOptions.isEmpty()) {
			LOGGER.debug("*** categoryOptions " + categoryOptions.size());
			Map<String, List<ProductOption>> categoryOptionsMap = categoryOptions.stream()
					.collect(Collectors.groupingBy(c -> c.getOption().getOptionGroup().getOptionGroupName()));
			model.addAttribute("categoryOptions", categoryOptionsMap);
		}

	}

	@RequestMapping(value = "/{url}/filters={filters:.+}")
	public String displayCategoryByUrlWithFilters(@PathVariable("url") String url,
			@PathVariable("filters") String filters, Integer current, Integer pageSize, Model model) {
		LOGGER.debug("url is " + url + ", filters are " + filters);
		// check if category exists
		Category category = categoryService.findCategoryByUrl(url);
		if (category != null) {

			List<Category> breadCrumbs = new ArrayList<Category>();
			addBreadCrumb(category, breadCrumbs);
			model.addAttribute("breadCrumbs", breadCrumbs);
			model.addAttribute("categoryURL", category.getCategoryURL());

			List<Long> childCategories = new ArrayList<Long>();
			createChildrenList(category, childCategories);
			LOGGER.debug("childCategories " + childCategories.toString());
			model.addAttribute("childCategories", StringUtils.join(childCategories, ","));
			List<Long> filterList = null;
			if (filters.equals("")) {
				filterList = Arrays.asList(filters.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
			}
			loadFilterProductTableData(filterList, childCategories, current, pageSize, model);

			model.addAttribute("menu", categoryService.findAllParentCategories());

			List<ProductOption> categoryOptions = productOptionService.findOptionsByCategoryList(childCategories);
			if (!categoryOptions.isEmpty()) {
				LOGGER.debug("*** categoryOptions " + categoryOptions.size());
				Map<String, List<ProductOption>> categoryOptionsMap = categoryOptions.stream()
						.collect(Collectors.groupingBy(c -> c.getOption().getOptionGroup().getOptionGroupName()));
				model.addAttribute("categoryOptions", categoryOptionsMap);
			}

			return "category";
		} else {
			return "redirect:/";
		}

	}

}
