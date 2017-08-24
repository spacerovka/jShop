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
import shop.main.data.service.MenuItemService;
import shop.main.utils.Constants;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:menu.sql" })
public class TestMenuService {

	private Pageable pageable;
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private final boolean DUMMY_STATUS = true;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestMenuService.class);
	private final String TYPE = Constants.RIGHT;

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Autowired
	@Qualifier("menuItemService")
	private MenuItemService service;

	@Test
	public void save() {
		LOGGER.info("save");

		MenuItem toSave = new MenuItem();
		toSave.setText(DUMMY_STRING);
		toSave.setURL(DUMMY_STRING);
		toSave.setStatus(DUMMY_STATUS);
		toSave.setType(TYPE);
		service.save(toSave);
		MenuItem found = service.findById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void deleteById() {
		LOGGER.info("deleteById");
		service.deleteById(1);
		MenuItem result = service.findById(1);
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void findById() {
		LOGGER.info("findById");
		MenuItem result = service.findById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void findLeftMenu() {
		LOGGER.info("findLeftMenu");
		List<MenuItem> result = service.findLeftMenu();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(3, result.size());
	}

	@Test
	public void findRightMenu() {
		LOGGER.info("findRightMenu");
		List<MenuItem> result = service.findRightMenu();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(2, result.size());
	}

	@Test
	public void listAll() {
		LOGGER.info("listAll");
		List<MenuItem> result = service.listAll(pageable);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(5, result.size());
	}

	@Test
	public void getAllCount() {
		LOGGER.info("countByNameAndStatus");
		long result = service.getAllCount();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(5, result);
	}

}
