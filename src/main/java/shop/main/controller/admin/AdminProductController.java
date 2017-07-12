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

import shop.main.data.entity.Option;
import shop.main.data.entity.OptionGroup;
import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.service.CategoryService;
import shop.main.data.service.OptionGroupService;
import shop.main.data.service.OptionService;
import shop.main.data.service.ProductOptionService;
import shop.main.data.service.ProductService;
import shop.main.utils.URLUtils;

@Controller
@RequestMapping(value = { ADMIN_PREFIX, MANAGER_PREFIX })
public class AdminProductController extends AdminController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private OptionService optionService;

	@Autowired
	private OptionGroupService optionGroupService;

	@Autowired
	private ProductOptionService productOptionService;

	@Autowired
	ServletContext context;

	/******** Product pages ***/

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") @Valid Product product, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		// String errorSummary = "";
		// if (product.isNew() && !productService.checkUniqueURL(product)) {
		// errorSummary += "URL is not unique! ";
		// model.addAttribute("urlError", "has-error");
		// }
		// if (product.getPrice() == null) {
		// errorSummary += "Price can not be empty! ";
		// model.addAttribute("priceError", "has-error");
		// }
		// if (product.getSku() == null || product.getSku().isEmpty()) {
		// errorSummary += "SKU can not be empty! ";
		// model.addAttribute("skuError", "has-error");
		// }
		// if (!errorSummary.isEmpty()) {
		// model.addAttribute("errorSummary", errorSummary);
		// return "../admin/products/edit_product";
		// }

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");

			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));

			if (product.isNew() && !productService.checkUniqueURL(product)) {
				model.addAttribute("urlError", "has-error");
			}
			if (product.getPrice() == null) {
				model.addAttribute("priceError", "has-error");
			}
			if (product.getSku() == null || product.getSku().isEmpty()) {
				model.addAttribute("skuError", "has-error");
			}

			return "../admin/products/edit_product";
		} else if (product.isNew() && !productService.checkUniqueURL(product)) {
			model.addAttribute("errorSummary", new ArrayList<String>(Arrays.asList("URL is not unique!")));
			model.addAttribute("urlError", "has-error");
			return "../admin/products/edit_product";
		} else {
			if (product.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Product added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Product updated successfully!");
			}
			if (product.getCategory().getId() == -1) {
				product.setCategory(null);
			}
			productService.saveProduct(product);
			for (ProductOption po : product.getProductOptions()) {
				if (po.getProduct() == null) {
					po.setProduct(product);
				}
				if (po.getOption().getId() == -1) {
					po.setOption(null);
				}
				if (po.getOptionGroup().getId() == -1) {
					po.setOptionGroup(null);
				} else {
					po.setOptionGroup(optionGroupService.fingOptionById(po.getOption().getId()));
				}
				if (po.getOptionGroup() != null && po.getOption() != null) {
					productOptionService.save(po);
				}
			}
			return "redirect:" + getUrlPrefix(request) + "products";
		}

	}

	@RequestMapping(value = "/products")
	public String productsList(Model model) {

		model.addAttribute("productList", productService.listAll());
		return "../admin/products/products";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public String addProduct(Model model) {

		model.addAttribute("product", new Product());
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/products/edit_product";
	}

	@RequestMapping(value = "/product/{id}/update", method = RequestMethod.GET)
	public String editProduct(@PathVariable("id") long id, Model model) {

		model.addAttribute("product", productService.fingProductById(id));
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		model.addAttribute("images", URLUtils.getProductImages(context, id));
		model.addAttribute("mainImage", URLUtils.getProductImage(context, id));
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/products/edit_product";
	}

	@RequestMapping(value = "/product/{id}/delete", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		productService.deleteProductById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Product deleted successfully!");
		return "redirect:" + getUrlPrefix(request) + "products";
	}
	/* Options */

	@RequestMapping(value = "/options")
	public String optionsList(Model model) {

		model.addAttribute("optionList", optionService.listAll());
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/options/options";
	}

	@RequestMapping(value = "/option", method = RequestMethod.POST)
	public String saveOption(@ModelAttribute("option") @Valid Option option, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/options/edit_option";
		} else {
			if (option.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Category added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Category updated successfully!");
			}
			if (option.getOptionGroup().getId() == -1) {
				option.setOptionGroup(null);
			}
			optionService.save(option);
			return "redirect:" + getUrlPrefix(request) + "options";
		}

	}

	@RequestMapping(value = "/optiongroup", method = RequestMethod.POST)
	public String saveOptionGroup(@ModelAttribute("optiongroup") @Valid OptionGroup optiongroup, BindingResult result,
			Model model, final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/options/edit_optionGroup";
		} else {
			if (optiongroup.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Category added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Category updated successfully!");
			}

			optionGroupService.save(optiongroup);
			return "redirect:" + getUrlPrefix(request) + "options";
		}

	}

	@RequestMapping(value = "/option/add", method = RequestMethod.GET)
	public String addOption(Model model) {

		model.addAttribute("option", new Option());
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/options/edit_option";
	}

	@RequestMapping(value = "/optiongroup/add", method = RequestMethod.GET)
	public String addOptionGroup(Model model) {

		model.addAttribute("optiongroup", new OptionGroup());
		return "../admin/options/edit_optionGroup";
	}

	@RequestMapping(value = "/option/{id}/update", method = RequestMethod.GET)
	public String editOption(@PathVariable("id") long id, Model model) {

		model.addAttribute("option", optionService.fingOptionById(id));
		model.addAttribute("optiongroupList", optionGroupService.listAll());

		return "../admin/options/edit_option";
	}

	@RequestMapping(value = "/optiongroup/{id}/update", method = RequestMethod.GET)
	public String editOptionGroup(@PathVariable("id") long id, Model model) {

		model.addAttribute("optiongroup", optionGroupService.fingOptionById(id));

		return "../admin/options/edit_optionGroup";
	}

	@RequestMapping(value = "/option/{id}/delete", method = RequestMethod.GET)
	public String deleteOption(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		optionService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Option deleted successfully!");

		return "redirect:" + getUrlPrefix(request) + "options";
	}

	@RequestMapping(value = "/optiongroup/{id}/delete", method = RequestMethod.GET)
	public String deleteOptionGroup(@PathVariable("id") long id, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		optionGroupService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Option group deleted successfully!");

		return "redirect:" + getUrlPrefix(request) + "options";
	}
}
