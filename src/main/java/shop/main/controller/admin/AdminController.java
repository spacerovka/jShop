package shop.main.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import shop.main.data.service.SitePropertyService;
import shop.main.utils.Constants;

@Controller
public class AdminController {

	public static final String MANAGER_PREFIX = "/manager/";
	public static final String ADMIN_PREFIX = "/a/";
	public final static Integer PAGE_SIZE = 2;

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

	protected void addPaginator(Model model, int current, int pageSize, long count) {
		List<Integer> pagination = new ArrayList<>(0);
		long pagesCount = 0;
		if (count % pageSize > 0) {
			pagesCount = count / pageSize + 1;
		} else {
			pagesCount = count / pageSize;
		}
		for (int i = 1; i <= pagesCount; i++) {
			pagination.add(i);
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("pagesCount", pagesCount);
		if (pagesCount < 2) {
			model.addAttribute("RENDER_NEXT", false);
			model.addAttribute("RENDER_PREV", false);
		} else {
			if (current == pagesCount) {
				model.addAttribute("RENDER_NEXT", false);
			} else {
				model.addAttribute("RENDER_NEXT", true);
			}
			if (current == 1) {
				model.addAttribute("RENDER_PREV", false);
			} else {
				model.addAttribute("RENDER_PREV", true);
			}
		}
	}

}
