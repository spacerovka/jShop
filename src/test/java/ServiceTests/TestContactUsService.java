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
import shop.main.data.entity.ContactUsMessage;
import shop.main.data.service.ContactUsMessageService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:contact.sql" })
public class TestContactUsService {

	private static final String STATUS = "true";
	private final String EMAIL = "email@email.email";
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private Pageable pageable;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestCategoryService.class);

	@Autowired
	@Qualifier("contactUsMessageService")
	private ContactUsMessageService service;

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Test
	public void save() {
		LOGGER.info("save");
		ContactUsMessage toSave = new ContactUsMessage();
		toSave.setUserEmail(EMAIL);
		toSave.setComment(DUMMY_STRING);
		toSave.setTheme(DUMMY_STRING);
		toSave.setUserName(DUMMY_STRING);
		service.save(toSave);
		ContactUsMessage found = service.findById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void deleteById() {
		LOGGER.info("deleteById");
		service.deleteById(1);
		ContactUsMessage result = service.findById(1);
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void findById() {
		LOGGER.info("findById");
		ContactUsMessage result = service.findById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByStatus() {
		LOGGER.info("findByStatus");
		List<ContactUsMessage> result = service.findByStatus(STATUS, pageable);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(2, result.size());
	}

	@Test
	public void countByStatus() {
		LOGGER.info("countByStatus");
		long result = service.countByStatus(STATUS);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(2, result);
	}
}
