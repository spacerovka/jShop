package shop.main.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
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

import shop.main.data.objects.Category;
import shop.main.data.objects.Product;
import shop.main.data.service.CategoryService;
import shop.main.data.service.ProductService;
import shop.main.utils.URLUtils;

@Controller
public class AdminController {
	
	@Autowired
	 private ProductService productService;
	
	@Autowired
	 private CategoryService categoryService;
			
	@Autowired
    ServletContext context;
	
	@RequestMapping(value = "/admin/welcome")
	public String welcome(Model model) {
//		Product data = productService.fingProductById(0L);
//		System.out.println(data.toString());
//		model.addAttribute("product",data);
		return "admin/welcome";
	}
		
	
	@RequestMapping(value = "/a/categories")
	public String categoriesList(Model model) {

		model.addAttribute("categoryList",categoryService.listAll());
		return "admin/categories";
	}
	
	@RequestMapping(value = "/a/category", method=RequestMethod.POST) 
	public String saveCategory(
			@ModelAttribute("category") @Valid Category category,
			Model model,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors() || !categoryService.checkUniqueURL(category)) {
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");
			model.addAttribute("urlError", "has-error");
			return "admin/edit_category";
		} else {			
			if(category.isNew()){
			  redirectAttributes.addFlashAttribute("flashMessage", "Category added successfully!");
			}else{
			  redirectAttributes.addFlashAttribute("flashMessage", "Category updated successfully!");
			}	
			if(category.getParentCategory().getId() == -1){
				category.setParentCategory(null);
			}	
			categoryService.saveCategory(category);
			return "redirect:/a/categories";
		}
		
	}
	
	@RequestMapping(value = "/a/category/add", method=RequestMethod.GET)
	public String addCategory(Model model) {

		model.addAttribute("category",new Category());
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		return "admin/edit_category";
	}
	
	@RequestMapping(value = "/a/category/{id}/update", method=RequestMethod.GET)
	public String editCategory(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("category",categoryService.findCategoryById(id));
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());				
		
	    model.addAttribute("images", URLUtils.getCategoryImages(context, id));
		
		return "admin/edit_category";
	}
	
	@RequestMapping(value = "/a/category/{id}/delete", method=RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		categoryService.deleteCategoryById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Category deleted successfully!");
		//TODO delete images
		return "redirect:/a/categories";
	}
	
	/******** Product pages ***/
	
	@RequestMapping(value = "/a/product", method=RequestMethod.POST) 
	public String saveProduct(
			@ModelAttribute("product") @Valid Product product,
			Model model,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors() || !productService.checkUniqueURL(product)) {
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");
			model.addAttribute("urlError", "has-error");
			return "admin/edit_product";
		} else {			
			if(product.isNew()){
			  redirectAttributes.addFlashAttribute("flashMessage", "Product added successfully!");
			}else{
			  redirectAttributes.addFlashAttribute("flashMessage", "Product updated successfully!");
			}
			if(product.getCategory().getId() == -1){
				product.setCategory(null);
			}			
			productService.saveProduct(product);
			return "redirect:/a/products";
		}
		
	}
	
	@RequestMapping(value = "/a/products")
	public String productsList(Model model) {

		model.addAttribute("productList",productService.listAll());
		return "admin/products";
	}
	
	@RequestMapping(value = "/a/product/add", method=RequestMethod.GET)
	public String addProduct(Model model) {

		model.addAttribute("product",new Product());
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		return "admin/edit_product";
	}
	
	@RequestMapping(value = "/a/product/{id}/update", method=RequestMethod.GET)
	public String editProduct(@PathVariable("id") long id, Model model) {

		model.addAttribute("product",productService.fingProductById(id));
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		model.addAttribute("images", URLUtils.getProductImages(context, id));
		return "admin/edit_product";
	}
	
	@RequestMapping(value = "/a/product/{id}/delete", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		productService.deleteProductById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Product deleted successfully!");
		return "redirect:/a/products";
	}
	
		
}
