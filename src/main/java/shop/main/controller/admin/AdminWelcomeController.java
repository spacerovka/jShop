package shop.main.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminWelcomeController extends AdminController {

	@RequestMapping(value = ADMIN_PREFIX + "welcome")
	public String welcomeAdmin(Model model) {

		return "../admin/welcome";
	}

	@RequestMapping(value = MANAGER_PREFIX + "welcome")
	public String welcomeManager(Model model) {

		return "../admin/welcome_manager";
	}
}
