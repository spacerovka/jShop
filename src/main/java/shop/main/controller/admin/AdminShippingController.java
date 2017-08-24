package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;
import static shop.main.controller.admin.AdminController.MANAGER_PREFIX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import shop.main.data.entity.Country;
import shop.main.data.entity.ParcelCost;
import shop.main.data.entity.ParcelSize;
import shop.main.data.service.ShippingCostService;

@Controller
@RequestMapping(value = { ADMIN_PREFIX, MANAGER_PREFIX })
public class AdminShippingController extends AdminController {
	@Autowired
	ShippingCostService service;

	@RequestMapping(value = "/sizes")
	public String sizeList(Model model) {
		loadSizeTableData(1, PAGE_SIZE, model);

		return "../admin/shipping/size_list";
	}

	@RequestMapping(value = "findSize", method = RequestMethod.POST)
	public String findSize(@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {

		loadSizeTableData(current, pageSize, model);
		return "../admin/shipping/_size_table";
	}

	private void loadSizeTableData(Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("list", service.listAllSizez(pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, service.countSizes());
	}

	@RequestMapping(value = "/size", method = RequestMethod.POST)
	public String saveSize(@ModelAttribute("category") @Valid ParcelSize size, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/shipping/_edit_size";
		} else {
			if (size.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Item added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Item updated successfully!");
			}
			service.saveSize(size);
			return "redirect:" + getUrlPrefix(request) + "sizes";
		}

	}

	@RequestMapping(value = "/size/add", method = RequestMethod.GET)
	public String addSize(Model model) {

		model.addAttribute("size", new ParcelSize());
		return "../admin/shipping/_edit_size";
	}

	@RequestMapping(value = "/size/{id}/update", method = RequestMethod.GET)
	public String editSize(@PathVariable("id") long id, Model model) {

		model.addAttribute("size", service.findSizeById(id));
		return "../admin/shipping/_edit_size";
	}

	@RequestMapping(value = "/size/{id}/delete", method = RequestMethod.GET)
	public String deleteSize(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		service.deleteSizeById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");
		return "redirect:" + getUrlPrefix(request) + "sizes";
	}

	/*** Countries */

	@RequestMapping(value = "/countries")
	public String countriesList(Model model) {

		loadCountryTableData(1, PAGE_SIZE, model);
		return "../admin/shipping/country_list";
	}

	@RequestMapping(value = "findCountry", method = RequestMethod.POST)
	public String findCountry(@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {

		loadCountryTableData(current, pageSize, model);
		return "../admin/shipping/_country_table";
	}

	private void loadCountryTableData(Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("list", service.listAllCountries(pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, service.countSizes());
	}

	@RequestMapping(value = "/country", method = RequestMethod.POST)
	public String saveCountry(@ModelAttribute("category") @Valid Country country, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/shipping/_edit_country";
		} else {
			if (country.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Item added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Item updated successfully!");
			}
			service.saveCountry(country);
			return "redirect:" + getUrlPrefix(request) + "countries";
		}

	}

	@RequestMapping(value = "/country/add", method = RequestMethod.GET)
	public String addCountry(Model model) {
		Country country = new Country();

		List<ParcelSize> sizeList = service.listAllSizez();
		for (ParcelSize s : sizeList) {
			country.getCostList().add(new ParcelCost(country, s));
		}
		model.addAttribute("country", country);
		return "../admin/shipping/_edit_country";
	}

	@RequestMapping(value = "/country/{id}/update", method = RequestMethod.GET)
	public String editCountry(@PathVariable("id") long id, Model model) {
		Country country = service.findCountryById(id);
		List<ParcelSize> sizeList = service.listAllSizez();
		List<ParcelCost> costList = new ArrayList<>();
		for (ParcelSize s : sizeList) {
			ParcelCost cost = service.findOneByCountryAndSize(country, s);
			if (cost == null) {
				cost = new ParcelCost(country, s);
			}
			costList.add(cost);
		}
		country.setCostList(costList);
		model.addAttribute("country", country);

		return "../admin/shipping/_edit_country";
	}

	@RequestMapping(value = "/country/{id}/delete", method = RequestMethod.GET)
	public String deleteCountry(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		service.deleteCountryById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Country deleted successfully!");
		return "redirect:" + getUrlPrefix(request) + "countries";
	}

}
