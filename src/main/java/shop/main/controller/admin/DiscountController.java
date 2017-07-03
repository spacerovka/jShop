package shop.main.controller.admin;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.Discount;
import shop.main.data.service.DiscountService;

@Controller
@RequestMapping(value = { "/a", "/manager" })
public class DiscountController extends AdminController {
	@Autowired
	DiscountService discountService;

	@RequestMapping(value = "/discounts")
	public String discountsList(Model model) {

		model.addAttribute("discountList", discountService.listAll());
		return "../admin/discounts/discounts";
	}

	@RequestMapping(value = "/discount", method = RequestMethod.POST)
	public String saveDiscount(@ModelAttribute("discount") @Valid Discount discount, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/discounts/edit_discount";
		} else {
			if (discount.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Discount added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Discount updated successfully!");
			}

			discountService.save(discount);
			return "redirect:" + getUrlPrefix(request) + "discounts";
		}

	}

	@RequestMapping(value = "/discount/add", method = RequestMethod.GET)
	public String addDiscount(Model model) {

		model.addAttribute("discount", new Discount());
		model.addAttribute("urlError", "");
		model.addAttribute("parentDiscountList", discountService.listAll());
		return "../admin/discounts/edit_discount";
	}

	@RequestMapping(value = "/discount/{id}/update", method = RequestMethod.GET)
	public String editDiscount(@PathVariable("id") long id, Model model) {

		model.addAttribute("discount", discountService.findById(id));

		return "../admin/discounts/edit_discount";
	}

	@RequestMapping(value = "/discount/{id}/delete", method = RequestMethod.GET)
	public String deleteDiscount(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		discountService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Discount deleted successfully!");
		return "redirect:" + getUrlPrefix(request) + "discounts";
	}

	@RequestMapping(value = "/findDiscounts", method = RequestMethod.POST)
	public String findDiscounts(@RequestParam String name, @RequestParam String status, Model model) {

		model.addAttribute("categoryList", discountService.findByNameAndStatus(name, status));
		return "../admin/categories/_table";

	}

}
