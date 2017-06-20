package shop.main.controller;

import java.util.Date;
import java.util.stream.Stream;

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

import shop.main.data.entity.Block;
import shop.main.data.entity.Category;
import shop.main.data.entity.MenuItem;
import shop.main.data.entity.Option;
import shop.main.data.entity.OptionGroup;
import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.entity.SitePropertiesWrapper;
import shop.main.data.entity.StaticPage;
import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;
import shop.main.data.service.BlockService;
import shop.main.data.service.CategoryService;
import shop.main.data.service.MenuItemService;
import shop.main.data.service.OptionGroupService;
import shop.main.data.service.OptionService;
import shop.main.data.service.ProductOptionService;
import shop.main.data.service.ProductService;
import shop.main.data.service.SitePropertyService;
import shop.main.data.service.StaticPageService;
import shop.main.utils.Constants;
import shop.main.utils.URLUtils;

@Controller
public class AdminController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MenuItemService menuService;

	@Autowired
	private BlockService blockService;

	@Autowired
	private OptionService optionService;

	@Autowired
	private OptionGroupService optionGroupService;

	@Autowired
	private ProductOptionService productOptionService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private SitePropertyService sitePropertyService;

	@Autowired
	private StaticPageService staticPageService;

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/admin/welcome")
	public String welcome(Model model) {
		// Product data = productService.fingProductById(0L);
		// System.out.println(data.toString());
		// model.addAttribute("product",data);
		return "../admin/welcome";
	}

	@RequestMapping(value = "/a/categories")
	public String categoriesList(Model model) {

		model.addAttribute("categoryList", categoryService.listAll());
		return "../admin/categories/categories";
	}

	@RequestMapping(value = "/a/category", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") @Valid Category category, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors() || !categoryService.checkUniqueURL(category)) {
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");
			model.addAttribute("errorSummary", "URL is not unique!");
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
			return "redirect:/a/categories";
		}

	}

	@RequestMapping(value = "/a/category/add", method = RequestMethod.GET)
	public String addCategory(Model model) {

		model.addAttribute("category", new Category());
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		return "../admin/categories/edit_category";
	}

	@RequestMapping(value = "/a/category/{id}/update", method = RequestMethod.GET)
	public String editCategory(@PathVariable("id") long id, Model model) {

		model.addAttribute("category", categoryService.findCategoryById(id));
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());

		model.addAttribute("images", URLUtils.getCategoryImages(context, id));

		return "../admin/categories/edit_category";
	}

	@RequestMapping(value = "/a/category/{id}/delete", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") long id, Model model,
			final RedirectAttributes redirectAttributes) {
		categoryService.deleteCategoryById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Category deleted successfully!");
		// TODO delete images
		return "redirect:/a/categories";
	}

	/******** Product pages ***/

	@RequestMapping(value = "/a/product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") @Valid Product product, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		String errorSummary = "";
		if (product.isNew() && !productService.checkUniqueURL(product)) {
			errorSummary += "URL is not unique! ";
			model.addAttribute("urlError", "has-error");
		}
		if (product.getPrice() == null) {
			errorSummary += "Price can not be empty! ";
			model.addAttribute("priceError", "has-error");
		}
		if (product.getSku() == null || product.getSku().isEmpty()) {
			errorSummary += "SKU can not be empty! ";
			model.addAttribute("skuError", "has-error");
		}
		if (!errorSummary.isEmpty()) {
			model.addAttribute("errorSummary", errorSummary);
			return "../admin/products/edit_product";
		}

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("flashMessage", "Errors occured!");
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
			return "redirect:/a/products";
		}

	}

	@RequestMapping(value = "/a/products")
	public String productsList(Model model) {

		model.addAttribute("productList", productService.listAll());
		return "../admin/products/products";
	}

	@RequestMapping(value = "/a/product/add", method = RequestMethod.GET)
	public String addProduct(Model model) {

		model.addAttribute("product", new Product());
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/products/edit_product";
	}

	@RequestMapping(value = "/a/product/{id}/update", method = RequestMethod.GET)
	public String editProduct(@PathVariable("id") long id, Model model) {

		model.addAttribute("product", productService.fingProductById(id));
		model.addAttribute("urlError", "");
		model.addAttribute("parentCategoryList", categoryService.listAll());
		model.addAttribute("images", URLUtils.getProductImages(context, id));
		model.addAttribute("mainImage", URLUtils.getProductImage(context, id));
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/products/edit_product";
	}

	@RequestMapping(value = "/a/product/{id}/delete", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		productService.deleteProductById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Product deleted successfully!");
		return "redirect:/a/products";
	}

	/********* Orders ***/

	@RequestMapping(value = "/a/orders")
	public String ordersList(Model model) {

		model.addAttribute("orders", orderRepository.findAll());
		return "../admin/orders/orders";
	}

	@RequestMapping(value = "/a/order/{id}/update", method = RequestMethod.GET)
	public String editOrder(@PathVariable("id") String id, Model model) {
		Order order = orderRepository.findOne(id);
		model.addAttribute("order", order);
		model.addAttribute("countryList", Constants.getCountryList());

		return "../admin/orders/edit_order";
	}

	@RequestMapping(value = "/a/order/{id}/delete", method = RequestMethod.GET)
	public String deleteOrder(@PathVariable("id") String id, Model model, final RedirectAttributes redirectAttributes) {
		orderRepository.delete(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Order deleted successfully!");
		return "redirect:/a/orders";
	}

	@RequestMapping(value = "/a/order", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("order") @Valid Order order, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		String errorSummary = "";

		if (!errorSummary.isEmpty()) {
			model.addAttribute("errorSummary", errorSummary);
			return "../admin/orders/edit_order";
		}

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("flashMessage", "Errors occured!");
			return "../admin/orders/edit_order";
		} else {
			redirectAttributes.addFlashAttribute("flashMessage", "Product updated successfully!");
			Order prevOrder = orderRepository.findOne(order.getOrderId());
			order.setDate(prevOrder.getDate());
			if (order.getDate() == null) {
				order.setDate(new Date());
			}
			order.setProduct_list(prevOrder.getProduct_list());
			orderRepository.save(order);

			return "redirect:/a/orders";
		}

	}

	/********* Menu Items pages ***/

	@RequestMapping(value = "/a/menu")
	public String menuList(Model model) {

		model.addAttribute("menuItemList", menuService.listAll());
		return "../admin/menu/menu_items";
	}

	@RequestMapping(value = "/a/menu", method = RequestMethod.POST)
	public String saveMenuItem(@ModelAttribute("menuItem") @Valid MenuItem item, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error");
			model.addAttribute("Error", "has-error");
			return "../admin/menu/edit_menu";
		} else {
			if (item.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Item added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Item updated successfully!");
			}

			menuService.save(item);
			return "redirect:/a/menu";
		}

	}

	@RequestMapping(value = "/a/menu/add", method = RequestMethod.GET)
	public String addMenuItem(Model model) {
		// TODO add menuTypeList
		model.addAttribute("menuItem", new MenuItem());
		model.addAttribute("menuTypeList", getMenuTypes());
		return "../admin/menu/edit_menu";
	}

	@RequestMapping(value = "/a/menu/{id}/update", method = RequestMethod.GET)
	public String editMenuIten(@PathVariable("id") long id, Model model) {

		model.addAttribute("menuItem", menuService.findById(id));
		model.addAttribute("menuTypeList", getMenuTypes());

		return "../admin/menu/edit_menu";
	}

	@RequestMapping(value = "/a/menu/{id}/delete", method = RequestMethod.GET)
	public String deleteMenuItem(@PathVariable("id") long id, Model model,
			final RedirectAttributes redirectAttributes) {
		menuService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");

		return "redirect:/a/menu";
	}

	private String[] getMenuTypes() {

		return Constants.menuTypes;
	}

	/************* Blocks ***/
	@RequestMapping(value = "/a/blocks")
	public String blockList(Model model) {

		model.addAttribute("blockList", blockService.listAll());
		return "../admin/blocks/blocks";
	}

	@RequestMapping(value = "/a/block", method = RequestMethod.POST)
	public String saveBlock(@ModelAttribute("block") @Valid Block block, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error");
			model.addAttribute("Error", "has-error");
			return "../admin/blocks/edit_block";
		} else {
			if (block.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Item added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Item updated successfully!");
			}

			blockService.save(block);
			return "redirect:/a/blocks";
		}

	}

	@RequestMapping(value = "/a/block/add", method = RequestMethod.GET)
	public String addBlock(Model model) {
		// TODO add menuTypeList
		model.addAttribute("block", new Block());
		model.addAttribute("blockTypeList", getBlockTypes());
		return "../admin/blocks/edit_block";
	}

	@RequestMapping(value = "/a/block/{id}/update", method = RequestMethod.GET)
	public String editBlock(@PathVariable("id") long id, Model model) {

		model.addAttribute("block", blockService.findById(id));
		model.addAttribute("blockTypeList", getBlockTypes());

		return "../admin/blocks/edit_block";
	}

	@RequestMapping(value = "/a/block/{id}/delete", method = RequestMethod.GET)
	public String deleteBlock(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		blockService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");

		return "redirect:/a/blocks";
	}

	private String[] getBlockTypes() {

		// return Constants.blockTypes;
		return Stream.of(Constants.BlockType.values()).map(Constants.BlockType::name).toArray(String[]::new);

	}

	/** Properties **/
	@RequestMapping(value = "/a/mainpage", method = RequestMethod.GET)
	public String editMainPage(Model model) {

		model.addAttribute("images", URLUtils.getMinPageImages(context));

		return "../admin/mainpageProperties";
	}

	/* Options */

	@RequestMapping(value = "/a/options")
	public String optionsList(Model model) {

		model.addAttribute("optionList", optionService.listAll());
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/options/options";
	}

	@RequestMapping(value = "/a/option", method = RequestMethod.POST)
	public String saveOption(@ModelAttribute("option") @Valid Option option, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
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
			return "redirect:/a/options";
		}

	}

	@RequestMapping(value = "/a/optiongroup", method = RequestMethod.POST)
	public String saveOptionGroup(@ModelAttribute("optiongroup") @Valid OptionGroup optiongroup, Model model,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "../admin/options/edit_optionGroup";
		} else {
			if (optiongroup.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Category added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Category updated successfully!");
			}

			optionGroupService.save(optiongroup);
			return "redirect:/a/options";
		}

	}

	@RequestMapping(value = "/a/option/add", method = RequestMethod.GET)
	public String addOption(Model model) {

		model.addAttribute("option", new Option());
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/options/edit_option";
	}

	@RequestMapping(value = "/a/optiongroup/add", method = RequestMethod.GET)
	public String addOptionGroup(Model model) {

		model.addAttribute("optiongroup", new OptionGroup());
		return "../admin/options/edit_optionGroup";
	}

	@RequestMapping(value = "/a/option/{id}/update", method = RequestMethod.GET)
	public String editOption(@PathVariable("id") long id, Model model) {

		model.addAttribute("option", optionService.fingOptionById(id));
		model.addAttribute("optiongroupList", optionGroupService.listAll());

		return "../admin/options/edit_option";
	}

	@RequestMapping(value = "/a/optiongroup/{id}/update", method = RequestMethod.GET)
	public String editOptionGroup(@PathVariable("id") long id, Model model) {

		model.addAttribute("optiongroup", optionGroupService.fingOptionById(id));

		return "../admin/options/edit_optionGroup";
	}

	@RequestMapping(value = "/a/option/{id}/delete", method = RequestMethod.GET)
	public String deleteOption(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		optionService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Option deleted successfully!");

		return "redirect:/a/options";
	}

	@RequestMapping(value = "/a/optiongroup/{id}/delete", method = RequestMethod.GET)
	public String deleteOptionGroup(@PathVariable("id") long id, Model model,
			final RedirectAttributes redirectAttributes) {

		optionGroupService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Option group deleted successfully!");

		return "redirect:/a/options";
	}

	@RequestMapping(value = "/a/properties", method = RequestMethod.GET)
	public String propertiesList(Model model) {

		model.addAttribute("propertyWrapper", new SitePropertiesWrapper(sitePropertyService.listAll()));
		return "../admin/properties";
	}

	@RequestMapping(value = "/a/properties", method = RequestMethod.POST)
	public String propertiesSave(@ModelAttribute("propertyWrapper") SitePropertiesWrapper propertyWrapper, Model model,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		System.out.println("*");
		System.out.println("*");
		System.out.println(propertyWrapper.getPropertyList().size());
		System.out.println("*");

		if (result.hasErrors()) {
			model.addAttribute("propertyList", sitePropertyService.listAll());
			return "../admin/properties";
		} else {
			redirectAttributes.addFlashAttribute("flashMessage", "Properties updated successfully!");
			propertyWrapper.getPropertyList().stream().forEach(p -> {
				System.out.println("*");
				System.out.println("*");
				System.out.println(p.getName() + " " + p.getContent() + " " + p.getId());
				System.out.println("*");
				sitePropertyService.save(p);
			});
			return "redirect:/a/properties";
		}
	}

	/* **********************Static pages ****** */
	@RequestMapping(value = "/a/pages")
	public String pagesList(Model model) {

		model.addAttribute("pagesList", staticPageService.listAll());
		return "../admin/staticPages/pages";
	}

	@RequestMapping(value = "/a/page", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("page") @Valid StaticPage page, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors() || !staticPageService.checkUniqueURL(page)) {
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");
			model.addAttribute("errorSummary", "URL is not unique!");
			model.addAttribute("urlError", "has-error");
			return "../admin/categories/edit_category";
		} else {
			if (page.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Category added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Category updated successfully!");
			}

			staticPageService.save(page);
			return "redirect:/a/pages";
		}

	}

	@RequestMapping(value = "/a/page/add", method = RequestMethod.GET)
	public String addPage(Model model) {

		model.addAttribute("page", new StaticPage());
		model.addAttribute("urlError", "");
		return "../admin/staticPages/edit_page";
	}

	@RequestMapping(value = "/a/page/{id}/update", method = RequestMethod.GET)
	public String editPage(@PathVariable("id") long id, Model model) {

		model.addAttribute("page", staticPageService.findById(id));
		model.addAttribute("urlError", "");

		return "../admin/staticPages/edit_page";
	}

	@RequestMapping(value = "/a/page/{id}/delete", method = RequestMethod.GET)
	public String deletePage(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		staticPageService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Category deleted successfully!");
		return "redirect:/a/pages";
	}

}
