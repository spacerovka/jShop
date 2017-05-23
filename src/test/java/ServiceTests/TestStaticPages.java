package ServiceTests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.objects.SiteProperty;
import shop.main.data.objects.StaticPage;
import shop.main.data.service.SitePropertyService;
import shop.main.data.service.StaticPageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfig.class})
@WebAppConfiguration
public class TestStaticPages {
	
	private final String URL = "about";
	private final String VALUE = "About";
	
	
	@Autowired
	@Qualifier("staticPageService")	
	 private StaticPageService staticPageService;
	
	
	@Test
	public void findByName() {
		
	StaticPage result = staticPageService.findOneByURL(URL);
	Assert.assertEquals(result.getName(), VALUE);
	}
	

}
