package shop.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequestMapping(value = "/awelcome")
	public String welcome(Model model) {
//		Product data = productService.fingProductById(0L);
//		System.out.println(data.toString());
//		model.addAttribute("product",data);
		return "admin/welcome";
	}
	
	@RequestMapping(value = "/acategories")
	public String categories(Model model) {
//		Product data = productService.fingProductById(0L);
//		System.out.println(data.toString());
//		model.addAttribute("product",data);
		return "admin/categories";
	}
	
	@RequestMapping(value = "/aeditcategory")
	public String editCategory(Model model) {
//		Product data = productService.fingProductById(0L);
//		System.out.println(data.toString());
//		model.addAttribute("product",data);
		return "admin/edit_category";
	}
	
}
