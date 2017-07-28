package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;
import static shop.main.controller.admin.AdminController.MANAGER_PREFIX;

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

import shop.main.data.entity.Option;
import shop.main.data.entity.OptionGroup;
import shop.main.data.service.OptionGroupService;
import shop.main.data.service.OptionService;

@Controller
@RequestMapping(value = { ADMIN_PREFIX, MANAGER_PREFIX })
public class AdminProductOptionsController extends AdminController {
	@Autowired
	private OptionService optionService;

	@Autowired
	private OptionGroupService optionGroupService;

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/options")
	public String optionsList(Model model) {
		loadOptionTableData("", 1, PAGE_SIZE, model);
		return "../admin/options/options";
	}

	@RequestMapping(value = "/findOption", method = RequestMethod.POST)
	public String findOption(@RequestParam String name,
			@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {
		loadOptionTableData(name, current, pageSize, model);
		return "../admin/options/_options_table";

	}

	@RequestMapping(value = "/optiongroups")
	public String optionGroupssList(Model model) {
		loadGroupTableData("", 1, PAGE_SIZE, model);
		return "../admin/options/option_groups";
	}

	@RequestMapping(value = "/findGroup", method = RequestMethod.POST)
	public String findGroup(@RequestParam String name,
			@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {
		loadGroupTableData(name, current, pageSize, model);
		return "../admin/options/_groups_table";

	}

	private void loadOptionTableData(String name, Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("optionList", optionService.findAllByName(name, pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, optionService.countByName(name));
	}

	private void loadGroupTableData(String name, Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("optiongroupList", optionGroupService.findAllByName(name, pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, optionGroupService.countByName(name));
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
