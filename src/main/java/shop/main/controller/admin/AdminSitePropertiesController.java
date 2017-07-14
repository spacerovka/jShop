package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;

import java.util.stream.Collectors;

import javax.servlet.ServletContext;
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

import shop.main.data.entity.MenuItem;
import shop.main.data.entity.SiteProperty;
import shop.main.data.service.MenuItemService;
import shop.main.data.service.SitePropertyService;
import shop.main.data.wrapper.SitePropertiesWrapper;
import shop.main.utils.Constants;
import shop.main.utils.Constants.GeneralProperties;
import shop.main.utils.Constants.MainProperties;
import shop.main.utils.URLUtils;

@Controller
@RequestMapping(value = { ADMIN_PREFIX })
public class AdminSitePropertiesController extends AdminController {

	@Autowired
	private MenuItemService menuService;

	@Autowired
	private SitePropertyService sitePropertyService;

	@Autowired
	ServletContext context;

	/********* Menu Items pages ***/

	@RequestMapping(value = "menu")
	public String menuList(Model model) {

		loadMenuTableData(1, PAGE_SIZE, model);
		return "../admin/menu/menu_items";
	}

	@RequestMapping(value = "ajax/menu", method = RequestMethod.POST)
	public String menuListPageable(@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {

		loadMenuTableData(current, pageSize, model);
		return "../admin/menu/_table";
	}

	private void loadMenuTableData(Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("menuItemList", menuService.listAll(pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, menuService.getAllCount());
	}

	@RequestMapping(value = "menu", method = RequestMethod.POST)
	public String saveMenuItem(@ModelAttribute("menuItem") @Valid MenuItem item, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/menu/edit_menu";
		} else {
			if (item.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Item added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Item updated successfully!");
			}

			menuService.save(item);
			return "redirect:" + "menu";
		}

	}

	@RequestMapping(value = "menu/add", method = RequestMethod.GET)
	public String addMenuItem(Model model) {
		// TODO add menuTypeList
		model.addAttribute("menuItem", new MenuItem());
		model.addAttribute("menuTypeList", getMenuTypes());
		return "../admin/menu/edit_menu";
	}

	@RequestMapping(value = "menu/{id}/update", method = RequestMethod.GET)
	public String editMenuIten(@PathVariable("id") long id, Model model) {

		model.addAttribute("menuItem", menuService.findById(id));
		model.addAttribute("menuTypeList", getMenuTypes());

		return "../admin/menu/edit_menu";
	}

	@RequestMapping(value = "menu/{id}/delete", method = RequestMethod.GET)
	public String deleteMenuItem(@PathVariable("id") long id, Model model,
			final RedirectAttributes redirectAttributes) {
		menuService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");

		return "redirect:" + ADMIN_PREFIX + "menu";
	}

	private String[] getMenuTypes() {

		return Constants.menuTypes;
	}

	/** Properties **/
	@RequestMapping(value = "mainpage", method = RequestMethod.GET)
	public String editMainPage(Model model) {
		SitePropertiesWrapper properties = new SitePropertiesWrapper();
		for (MainProperties propName : Constants.MainProperties.values()) {
			SiteProperty prop = sitePropertyService.findOneByName(propName.name());
			if (prop == null) {
				prop = new SiteProperty(propName.name());
			}
			properties.add(prop);
		}
		model.addAttribute("propertyWrapper", properties);
		model.addAttribute("images", URLUtils.getMinPageImages(context));

		return "../admin/mainpageProperties";
	}

	@RequestMapping(value = "properties", method = RequestMethod.GET)
	public String propertiesList(Model model) {

		SitePropertiesWrapper properties = new SitePropertiesWrapper();
		for (GeneralProperties propName : Constants.GeneralProperties.values()) {
			SiteProperty prop = sitePropertyService.findOneByName(propName.name());
			if (prop == null) {
				prop = new SiteProperty(propName.name());
			}
			properties.add(prop);
		}
		model.addAttribute("propertyWrapper", properties);
		return "../admin/properties";
	}

	@RequestMapping(value = "properties", method = RequestMethod.POST)
	public String propertiesSave(@ModelAttribute("propertyWrapper") SitePropertiesWrapper propertyWrapper, Model model,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		System.out.println("*");
		System.out.println("*");
		System.out.println(propertyWrapper.getPropertyList().size());
		System.out.println("*");

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			model.addAttribute("propertyList", sitePropertyService.listAll());
			return "../admin/properties";
		} else {
			redirectAttributes.addFlashAttribute("flashMessage", "Properties updated successfully!");
			propertyWrapper.getPropertyList().stream().forEach(p -> {
				sitePropertyService.save(p);
			});
			return "redirect:" + ADMIN_PREFIX + "properties";
		}
	}

	@RequestMapping(value = "mainproperties", method = RequestMethod.POST)
	public String mainPropertiesSave(@ModelAttribute("propertyWrapper") SitePropertiesWrapper propertyWrapper,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			model.addAttribute("propertyList", sitePropertyService.listAll());
			return "../admin/mainpageProperties";
		} else {
			redirectAttributes.addFlashAttribute("flashMessage", "Properties updated successfully!");
			propertyWrapper.getPropertyList().stream().forEach(p -> {
				sitePropertyService.save(p);
			});
			return "redirect:" + ADMIN_PREFIX + "mainpage";
		}
	}

}
