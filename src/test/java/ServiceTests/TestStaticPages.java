package ServiceTests;

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
import shop.main.data.entity.Block;
import shop.main.data.entity.Product;
import shop.main.data.entity.StaticPage;
import shop.main.data.service.StaticPageService;
import shop.main.utils.Constants;
import shop.main.utils.sqltracker.AssertSqlCount;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:static_pages.sql" })
public class TestStaticPages {

	private final String URL = "delivery";
	private final String NAME = "Delivery";
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private Pageable pageable;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestBlockService.class);

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Autowired
	@Qualifier("staticPageService")
	private StaticPageService service;

	@Test
	public void save() {
		LOGGER.info("save");
		StaticPage toSave = new StaticPage();
		toSave.setName(DUMMY_STRING);
		service.save(toSave);
		StaticPage found = service.findById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void findById() {
		LOGGER.info("findById");
		StaticPage result = service.findById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}


	@Test
	public void findOneByURL() {
		LOGGER.info("findOneByURL");
		StaticPage result = service.findOneByURL(URL);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}


	@Test
	public void deleteById() {
		LOGGER.info("deleteById");
		service.deleteById(1);
		StaticPage result = service.findById(1);
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void checkUniqueURL() {
		LOGGER.info("checkUniqueURL");
		StaticPage p = new StaticPage();
		p.setUrl(URL);
		boolean unique = service.checkUniqueURL(p);
		Assert.assertFalse(unique);
	}

	@Test
	public void countByNameAndStatus() {
		long result = service.countByNameAndStatus(NAME, "true");
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result);
	}

	@Test
	public void findByNameAndStatusQueryCount() {
		List<StaticPage> products = service.findByNameAndStatus(NAME, "true", pageable);
		// query count, and if not null query data
		AssertSqlCount.assertSelectCount(1);
	}

	@Test
	public void findByNameAndStatusValidateResult() {
		List<StaticPage> products = service.findByNameAndStatus(NAME, "true", pageable);
		Assert.assertEquals(1, products.size());
	}

}
