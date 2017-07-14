package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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

import shop.main.data.entity.StaticPage;
import shop.main.data.service.StaticPageService;

@Controller
@RequestMapping(value = { ADMIN_PREFIX })
public class AdminStaticPageController extends AdminController {

	static final String FOLDER_PREFIX = "";

	@Autowired
	private StaticPageService service;

	@RequestMapping(value = "pages")
	public String pagesList(Model model) {

		loadTableData("", null, 1, PAGE_SIZE, model);
		return "../admin/staticPages/pages";
	}

	@RequestMapping(value = "/findStaticPages", method = RequestMethod.POST)
	public String findPages(@RequestParam String name, @RequestParam String status,
			@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {
		loadTableData(name, status, current, pageSize, model);
		return "../admin/staticPages/_table";

	}

	private void loadTableData(String name, String status, Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("pagesList", service.findByNameAndStatus(name, status, pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, service.countByNameAndStatus(name, status));
	}

	@RequestMapping(value = "page", method = RequestMethod.POST)
	public String savePage(@ModelAttribute("page") @Valid StaticPage page, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");

			return "../admin/staticPages/edit_page";
		} else if (page.isNew() && !service.checkUniqueURL(page)) {
			model.addAttribute("errorSummary", new ArrayList<String>(Arrays.asList("URL is not unique!")));
			model.addAttribute("urlError", "has-error");
			return "../admin/staticPages/edit_page";
		} else {
			if (page.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Category added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Category updated successfully!");
			}

			service.save(page);
			return "redirect:" + ADMIN_PREFIX + "pages";
		}

	}

	@RequestMapping(value = "page/add", method = RequestMethod.GET)
	public String addPage(Model model) {

		model.addAttribute("page", new StaticPage());
		model.addAttribute("urlError", "");
		return "../admin/staticPages/edit_page";
	}

	@RequestMapping(value = "page/{id}/update", method = RequestMethod.GET)
	public String editPage(@PathVariable("id") long id, Model model) {

		model.addAttribute("page", service.findById(id));
		model.addAttribute("urlError", "");

		return "../admin/staticPages/edit_page";
	}

	@RequestMapping(value = "page/{id}/delete", method = RequestMethod.GET)
	public String deletePage(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		service.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Category deleted successfully!");
		return "redirect:" + ADMIN_PREFIX + "pages";
	}
}
