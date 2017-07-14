package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;
import static shop.main.controller.admin.AdminController.MANAGER_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
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

import shop.main.data.entity.Discount;
import shop.main.data.service.DiscountService;

@Controller
@RequestMapping(value = { ADMIN_PREFIX, MANAGER_PREFIX })
public class AdminDiscountController extends AdminController {
	@Autowired
	DiscountService discountService;

	@RequestMapping(value = "/discounts")
	public String discountsList(Model model) {
		loadTableData("", null, 1, PAGE_SIZE, model);
		return "../admin/discounts/discounts";
	}

	@RequestMapping(value = "/findDiscounts", method = RequestMethod.POST)
	public String findDiscounts(@RequestParam String name, @RequestParam String status,
			@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {
		loadTableData(name, status, current, pageSize, model);
		return "../admin/discounts/_table";

	}

	private void loadTableData(String name, String status, Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("discountList", discountService.findByNameAndStatus(name, status, pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, discountService.countByNameAndStatus(name, status));
	}

	@RequestMapping(value = "/discount", method = RequestMethod.POST)
	public String saveDiscount(@ModelAttribute("discount") @Valid Discount discount, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/discounts/edit_discount";
		} else {
			if (discount.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Discount added successfully!");
				if (discountService.notUniqueCoupon(discount.getCoupon())) {
					model.addAttribute("errorSummary",
							(new ArrayList<String>(Arrays.asList("Coupon code must be unique!"))));
					return "../admin/discounts/edit_discount";
				}
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

}
