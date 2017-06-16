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
import shop.main.data.entity.SiteProperty;
import shop.main.data.service.SitePropertyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfig.class})
@WebAppConfiguration
public class TestSiteProperty {
	
	private final String NAME = "siteName";
	private final String VALUE = "JShop";
	
	
	@Autowired
	@Qualifier("sitePropertyService")
	 private SitePropertyService sitePropertyService;
	
	
	@Test
	public void findByName() {
	List<SiteProperty> results = sitePropertyService.listAll();
	results.stream().forEach(c -> System.out.println(c.getName()+" "+c.getContent()));
	System.out.println("*");
	System.out.println("*****************found results "+results.size());
	System.out.println("*");
	
	SiteProperty result = sitePropertyService.findOneByName(NAME);
	Assert.assertEquals(result.getContent(), VALUE);
	}
	

}
