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
import shop.main.data.entity.Discount;
import shop.main.data.service.DiscountService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:discount.sql" })
public class TestDiscountService {

	private Pageable pageable;
	private final String COUPON = "getMySpring50";
	private final String NAME = "Spring 50% Sale";
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final byte DUMMY_NUMBER = 20;
	private final boolean DUMMY_STATUS = true;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private static final Logger LOGGER = LoggerFactory.getLogger(TestDiscountService.class);

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Autowired
	@Qualifier("discountService")
	private DiscountService service;

	@Test
	public void save() {
		LOGGER.info("save");

		Discount toSave = new Discount();
		toSave.setSalename(DUMMY_STRING);
		toSave.setCoupon(DUMMY_STRING);
		toSave.setDiscount(DUMMY_NUMBER);
		toSave.setStatus(DUMMY_STATUS);
		service.save(toSave);
		Discount found = service.findById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void deleteById() {
		LOGGER.info("deleteById");
		service.deleteById(1);
		Discount result = service.findById(1);
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void findById() {
		LOGGER.info("findById");
		Discount result = service.findById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByCoupon() {
		LOGGER.info("findByCoupon");
		Discount result = service.findByCoupon(COUPON);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByNameAndStatus() {
		List<Discount> result = service.findByNameAndStatus(NAME, String.valueOf(DUMMY_STATUS), pageable);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void getAllCount() {
		LOGGER.info("countByNameAndStatus");
		long result = service.countByNameAndStatus(NAME, String.valueOf(DUMMY_STATUS));
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result);
	}

	@Test
	public void notUniqueCoupon() {
		LOGGER.info("notUniqueCoupon");
		boolean notUnique = service.notUniqueCoupon(COUPON);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertTrue(notUnique);
	}

}
