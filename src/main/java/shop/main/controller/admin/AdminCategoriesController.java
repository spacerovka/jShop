package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;
import static shop.main.controller.admin.AdminController.MANAGER_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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

import shop.main.data.entity.Category;
import shop.main.data.service.CategoryService;
import shop.main.utils.URLUtils;

@Controller
@RequestMapping(value = { ADMIN_PREFIX, MANAGER_PREFIX })
public class AdminCategoriesController extends AdminController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/categories")
	public String categoriesList(Model model) {

		model.addAttribute("categoryList", categoryService.listAll());
		return "../admin/categories/categories";
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") @Valid Category category, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");

			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/categories/edit_category";
		} else if (category.isNew() && !categoryService.checkUniqueURL(category)) {
			model.addAttribute("errorSummary", new ArrayList<String>(Arrays.asList("URL is not unique!")));
			model.addAttribute("urlError", "has-error");
			return "../admin/categories/edit_category";
		} else {

			if (category.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Category added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Category updated successfully!");
			}
			if (category.getParentCategory().getId() == -1) {
				category.setParentCategory(null);
			}
			categoryService.saveCategory(category);
			return "redirect:" + getUrlPrefix(request) + "categories";
		}

	}

	@RequestMapping(value = "/category/add", method = RequestMethod.GET)
	public String addCategory(Model model) {

		model.addAttribute("category", new Category());
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		return "../admin/categories/edit_category";
	}

	@RequestMapping(value = "/category/{id}/update", method = RequestMethod.GET)
	public String editCategory(@PathVariable("id") long id, Model model) {

		model.addAttribute("category", categoryService.findCategoryById(id));
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());

		model.addAttribute("images", URLUtils.getCategoryImages(context, id));

		return "../admin/categories/edit_category";
	}

	@RequestMapping(value = "/category/{id}/delete", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		categoryService.deleteCategoryById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Category deleted successfully!");
		// TODO delete images
		return "redirect:" + getUrlPrefix(request) + "categories";
	}
}
