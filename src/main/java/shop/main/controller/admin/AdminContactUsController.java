package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;
import static shop.main.controller.admin.AdminController.MANAGER_PREFIX;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.service.ContactUsMessageService;

@Controller
@RequestMapping(value = { ADMIN_PREFIX, MANAGER_PREFIX })
public class AdminContactUsController extends AdminController {
	@Autowired
	ContactUsMessageService service;

	@RequestMapping(value = "/contactmessages")
	public String discountsList(Model model) {

		model.addAttribute("list", service.listAll());
		return "../admin/contactMessages/list";
	}

	@RequestMapping(value = "/contactmessage/{id}/update", method = RequestMethod.GET)
	public String editDiscount(@PathVariable("id") long id, Model model) {

		model.addAttribute("item", service.findContactUsMessageById(id));

		return "../admin/contactMessages/edit";
	}

	@RequestMapping(value = "/contactmessage/{id}/delete", method = RequestMethod.GET)
	public String deleteDiscount(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		service.deleteContactUsMessageById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");
		return "redirect:/contactmessages";
	}
}
