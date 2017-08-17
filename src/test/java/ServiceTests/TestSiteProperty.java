package ServiceTests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.SiteProperty;
import shop.main.data.service.SitePropertyService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:site-property.sql" })
public class TestSiteProperty {

	private final String NAME = "siteName";
	private final String VALUE = "JShop";
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private Pageable pageable;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestSiteProperty.class);

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Autowired
	@Qualifier("sitePropertyService")
	private SitePropertyService sitePropertyService;

	@Test
	public void findByName() {
		List<SiteProperty> results = sitePropertyService.listAll();
		results.stream().forEach(c -> System.out.println(c.getName() + " " + c.getContent()));
		System.out.println("*");
		System.out.println("*****************found results " + results.size());
		System.out.println("*");

		SiteProperty result = sitePropertyService.findOneByName(NAME);
		Assert.assertEquals(result.getContent(), VALUE);
	}

}
