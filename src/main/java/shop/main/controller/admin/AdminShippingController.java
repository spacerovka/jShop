package shop.main.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import shop.main.data.entity.Country;
import shop.main.data.entity.ParcelCost;
import shop.main.data.entity.ParcelSize;
import shop.main.data.service.ShippingCostService;

@Controller
@RequestMapping(value = { "/a", "/manager" })
public class AdminShippingController extends AdminController {
	@Autowired
	ShippingCostService service;

	@RequestMapping(value = "/sizes")
	public String sizeList(Model model) {

		model.addAttribute("list", service.listAllSizez());
		return "../admin/shipping/size_list";
	}

	@RequestMapping(value = "/size", method = RequestMethod.POST)
	public String saveSize(@ModelAttribute("category") @Valid ParcelSize size, Model model, BindingResult result,
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
	public String categoriesList(Model model) {

		model.addAttribute("list", service.listAllCountries());
		return "../admin/shipping/country_list";
	}

	@RequestMapping(value = "/country", method = RequestMethod.POST)
	public String saveCountry(@ModelAttribute("category") @Valid Country country, Model model, BindingResult result,
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
