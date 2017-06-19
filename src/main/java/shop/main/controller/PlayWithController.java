package shop.main.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import shop.main.data.entity.Category;
import shop.main.data.entity.ProductOption;
import shop.main.data.service.CategoryService;
import shop.main.data.service.ProductOptionService;
import shop.main.data.service.ProductService;

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
		
			
				
		
				
		return "db_test/options_tree";
	}
	
	 
}
