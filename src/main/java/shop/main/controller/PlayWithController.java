package shop.main.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.main.data.entity.Category;
import shop.main.data.entity.CategoryOption;
import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.service.CategoryOptionService;
import shop.main.data.service.CategoryService;
import shop.main.data.service.ProductOptionService;
import shop.main.data.service.ProductService;
import shop.main.utils.URLUtils;

@Controller
public class PlayWithController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayWithController.class);
	
	@Autowired
	 private ProductService productService;
	
	@Autowired
	 private CategoryService categoryService;
	
	@Autowired
	 private ProductOptionService productOptionService;
	
	@Autowired
	 private CategoryOptionService categoryOptionService;
	
	@Autowired
   ServletContext context;
	
	@RequestMapping(value = "/tree")
	public String displayCategoryTree(Model model) {
		
		List<Category> data = categoryService.findAllParentCategories();
		
		model.addAttribute("categories",data);
		return "db_test/category_tree";
	}
	
	@RequestMapping(value = "/options")
	public String displayOptions(Model model) {
		
		List<ProductOption> allOptions = productOptionService.listAll();		
		model.addAttribute("options",allOptions);
		
		//Find product options by simple options id
		List<Long> listOfFilters = new ArrayList<Long>();
		listOfFilters.add(1L);
		listOfFilters.add(6L);
		List<ProductOption> optionsByType = productOptionService.findProductOptionByOption(listOfFilters);
		model.addAttribute("optionsByType",optionsByType);
		
				
		//get all unique options for category range
		List<Long> listOfCategories = new ArrayList<Long>();
		listOfCategories.add(4L);
		listOfCategories.add(6L);
		List<CategoryOption> categoryOptions = categoryOptionService.findOptionsByCategoryList(listOfCategories);
		model.addAttribute("categoryOptions",categoryOptions);
		
		//get all active products for current filter options list and category
				List<Product> products = productService.findFilteredProductsInCategory(listOfFilters, listOfCategories);
				model.addAttribute("products",products);
				
		return "db_test/options_tree";
	}
	
	 
}
