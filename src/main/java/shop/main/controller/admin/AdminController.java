package shop.main.controller.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import shop.main.data.service.SitePropertyService;
import shop.main.utils.Constants;

@Controller
public class AdminController {

	public static final String MANAGER_PREFIX = "/manager/";
	public static final String ADMIN_PREFIX = "/a/";

	@Autowired
	protected SitePropertyService sitePropertyService;

	@ModelAttribute("SITE_NAME")
	public String getSiteName() {
		return this.sitePropertyService.findOneByName(Constants.SITE_NAME).getContent();
	}

	@ModelAttribute("URL_PREFIX")
	public String getUrlPrefix(HttpServletRequest request) {
		String prefix;
		if (hasRole(Constants.RoleType.MANAGER.name())) {
			prefix = "/manager/";
		} else {
			prefix = "/a/";
		}
		return prefix;
	}

	private boolean hasRole(String role) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}

}
