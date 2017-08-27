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
import shop.main.data.entity.MenuItem;
import shop.main.data.entity.SiteProperty;
import shop.main.data.service.SitePropertyService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:site-property.sql" })
public class TestSiteProperty {

	private final String NAME = "SITE_NAME";
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
	private SitePropertyService service;

	@Test
	public void findOneByName() {

		SiteProperty result =service.findOneByName(NAME);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(result.getContent(), VALUE);
	}

	@Test
	public void save() {
		LOGGER.info("save");
		SiteProperty toSave = new SiteProperty();
		toSave.setName(DUMMY_STRING);
		toSave.setContent(DUMMY_STRING);
		service.save(toSave);
		SiteProperty found = service.findById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void listAll() {
		LOGGER.info("listAll");
		List<SiteProperty> results = service.listAll();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(3, results.size());
	}

	@Test
	public void deleteById() {
		LOGGER.info("deleteById");
		service.deleteById(1);
		SiteProperty result = service.findById(1);
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void findById() {
		LOGGER.info("findById");
		SiteProperty result = service.findById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

}
