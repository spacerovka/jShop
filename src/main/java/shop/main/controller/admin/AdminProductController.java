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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.service.CategoryService;
import shop.main.data.service.OptionGroupService;
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
	private OptionGroupService optionGroupService;

	@Autowired
	private ProductOptionService productOptionService;

	@Autowired
	ServletContext context;

	/******** Product pages ***/

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") @Valid Product product, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");
			ArrayList<String> errors = (ArrayList<String>) result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList());

			if (product.isNew() && !productService.checkUniqueURL(product)) {
				model.addAttribute("urlError", "has-error");
				errors.add("URL is not unique!");
			}
			if (product.getPrice() == null) {
				model.addAttribute("priceError", "has-error");
			}
			if (product.getSku() == null || product.getSku().isEmpty()) {
				model.addAttribute("skuError", "has-error");
			} else if (productService.notUniqueSKU(product)) {
				model.addAttribute("skuError", "has-error");
				errors.add("SKU must be unique!");
			}
			model.addAttribute("errorSummary", errors);
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

		loadTableData(null, null, null, 1, PAGE_SIZE, model);
		return "../admin/products/products";
	}

	@RequestMapping(value = "/findProducts", method = RequestMethod.POST)
	public String findProducts(@RequestParam String name, @RequestParam String url, @RequestParam String searchSKU,
			Integer current, Integer pageSize, Model model) {
		loadTableData(name, url, searchSKU, current, pageSize, model);

		return "../admin/products/_table";

	}

	@RequestMapping(value = "/findProductsForOrder", method = RequestMethod.POST)
	public String findProductsForOrder(@RequestParam String name, @RequestParam String sku, Integer current,
			Integer pageSize, Model model) {
		loadTableData(name, null, sku, current, pageSize, model);
		return "../admin/orders/_add_product_table";
	}

	private void loadTableData(String name, String url, String searchSKU, Integer current, Integer pageSize,
			Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("productList", productService.findPageable(name, url, searchSKU, pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, productService.countPageable(name, url, searchSKU));
	}

	@RequestMapping(value = "/addToFeatured", method = RequestMethod.POST)
	public String addToFeatured(@RequestParam long id, @RequestParam String name, @RequestParam String url,
			@RequestParam String searchSKU, Integer current, Integer pageSize, Model model) {
		Product product = productService.fingProductById(id);
		product.setFeatured(true);
		productService.saveProduct(product);
		loadTableData(name, url, searchSKU, current, pageSize, model);
		return "../admin/products/_table";

	}

	@RequestMapping(value = "/removeFromFeatured", method = RequestMethod.POST)
	public String removeFromFeatured(@RequestParam long id, @RequestParam String name, @RequestParam String url,
			@RequestParam String searchSKU, Integer current, Integer pageSize, Model model) {
		Product product = productService.fingProductById(id);
		product.setFeatured(false);
		productService.saveProduct(product);
		loadTableData(name, url, searchSKU, current, pageSize, model);
		return "../admin/products/_table";

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

}
