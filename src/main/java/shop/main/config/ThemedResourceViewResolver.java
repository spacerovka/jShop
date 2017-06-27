package shop.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import shop.main.data.DAO.SitePropertyDAO;
import shop.main.data.entity.SiteProperty;
import shop.main.utils.Constants;
import shop.main.utils.Properties;

public class ThemedResourceViewResolver extends InternalResourceViewResolver {
	@Autowired
	private SitePropertyDAO sitePropertyDAO;

	protected String getPrefix() {
		String prefix = "/pages/" + Properties.THEME_NAME + "/";
		SiteProperty property = sitePropertyDAO.findOneByName(Constants.THEME);
		if (property != null) {
			prefix = "/pages/" + property.getContent() + "/";
		}
		return prefix;
	}
}
