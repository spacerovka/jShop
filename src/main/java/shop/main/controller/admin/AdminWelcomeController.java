package shop.main.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminWelcomeController extends AdminController {

	@RequestMapping(value = "/admin/welcome")
	public String welcome(Model model) {

		return "../admin/welcome";
	}

	@RequestMapping(value = "/manager/welcome")
	public String welcomemanager(Model model) {

		return "../admin/welcome_manager";
	}
}
