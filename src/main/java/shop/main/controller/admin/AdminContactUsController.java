package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;
import static shop.main.controller.admin.AdminController.MANAGER_PREFIX;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.service.ContactUsMessageService;

@Controller
@RequestMapping(value = { ADMIN_PREFIX, MANAGER_PREFIX })
public class AdminContactUsController extends AdminController {
	@Autowired
	ContactUsMessageService service;

	@RequestMapping(value = "/contactmessages")
	public String messagesList(Model model) {

		loadTableData(null, 1, PAGE_SIZE, model);
		return "../admin/contactMessages/list";
	}

	@RequestMapping(value = "/findMessages", method = RequestMethod.POST)
	public String findMessages(@RequestParam String status,
			@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {

		loadTableData(status, current, pageSize, model);
		return "../admin/contactMessages/_table";

	}

	private void loadTableData(String status, Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("messageList", service.findByStatus(status, pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, service.countByStatus(status));
	}

	@RequestMapping(value = "/contactmessage/{id}/update", method = RequestMethod.GET)
	public String editMessage(@PathVariable("id") long id, Model model) {

		model.addAttribute("item", service.findById(id));

		return "../admin/contactMessages/edit";
	}

	@RequestMapping(value = "/contactmessage/{id}/delete", method = RequestMethod.GET)
	public String deleteMessage(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		service.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");
		return "redirect:/contactmessages";
	}
}
