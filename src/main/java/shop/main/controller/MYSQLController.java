package shop.main.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import shop.main.data.objects.Category;
import shop.main.data.objects.Product;
import shop.main.data.service.CategoryService;
import shop.main.data.service.ProductService;

@Controller
public class MYSQLController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MYSQLController.class);

	@Autowired
	@Qualifier("dataSourceMysql")
	private DataSource dataSourceMysql;

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	 private ProductService productService;
	
	@Autowired
	 private CategoryService categoryService;

	@RequestMapping(value = "/displayusersmysql")
	public ModelAndView displayUsers(Principal principal) {
		// mysql database
		jdbcTemplate = new JdbcTemplate(dataSourceMysql);

		List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT*FROM USER");

		List<String> data = new ArrayList<String>();
		for(Map<String, Object> user: users) {
			data.add("username: "+ user.get("username"));
			LOGGER.debug("username: "+ user.get("username"));
		}
		return new ModelAndView("db_test/embeded_db_test", "users", data);
	}
	
	@RequestMapping(value = "/category")
	public ModelAndView displayCategory(Principal principal) {
		List<Product> data = productService.listAll();
		return new ModelAndView("category", "products", data);
	}
	
	@RequestMapping(value = "/product")
	public String displayProduct(Model model) {
		Product data = productService.fingProductById(0L);
		System.out.println(data.toString());
		model.addAttribute("product",data);
		return "product";
	}
	
	@RequestMapping(value = "/tree")
	public String displayCategoryTree(Model model) {
		try{
		Category main = new Category();
		main.setCategoryName("main");
		main.setCategoryURL("main");
		categoryService.saveCategory(main);
		
		Category child = new Category();
		child.setCategoryName("child");
		child.setCategoryURL("child");
		child.setParent(main);
		categoryService.saveCategory(child);
		}catch(Exception e){
			LOGGER.error(e.toString());
		}
		
		List<Category> data = categoryService.findAllParentCategories();
		
		model.addAttribute("categories",data);
		return "db_test/category_tree";
	}
}
