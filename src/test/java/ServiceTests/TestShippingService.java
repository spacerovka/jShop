package ServiceTests;

import java.math.BigDecimal;
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
import shop.main.data.entity.Country;
import shop.main.data.entity.ParcelCost;
import shop.main.data.entity.ParcelSize;
import shop.main.data.service.ShippingCostService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:shippingCost.sql" })
public class TestShippingService {

	private Pageable pageable;
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private final String NAME = "USA";
	private final String SIZE = "Regular";
	private final BigDecimal DUMMY_NUMBER = new BigDecimal(99.99);
	private static final Logger LOGGER = LoggerFactory.getLogger(TestShippingService.class);

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Autowired
	@Qualifier("shippingCostService")
	private ShippingCostService service;

	@Test
	public void saveCountry() {
		LOGGER.info("saveCountry");
		Country toSave = new Country();
		toSave.setName(DUMMY_STRING);
		toSave.setBasetarif(DUMMY_NUMBER);
		service.saveCountry(toSave);

		Country found = service.findCountryById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void saveSize() {
		LOGGER.info("saveSize");
		ParcelSize toSave = new ParcelSize();
		toSave.setName(DUMMY_STRING);
		service.saveSize(toSave);
		ParcelSize found = service.findSizeById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void deleteSizeById() {
		LOGGER.info("deleteSizeById");
		service.deleteSizeById(1L);
		ParcelSize result = service.findSizeById(1L);
		AssertSqlCount.assertSelectCount(1);
		AssertSqlCount.assertDeleteCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void deleteCountryById() {
		LOGGER.info("deleteCountryById");
		service.deleteCountryById(1L);
		Country result = service.findCountryById(1L);
		AssertSqlCount.assertSelectCount(1);
		AssertSqlCount.assertDeleteCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void findCountryById() {
		LOGGER.info("findCountryById");
		Country result = service.findCountryById(1L);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void findSizeById() {
		LOGGER.info("findSizeById");
		ParcelSize result = service.findSizeById(1L);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void listAllCountries() {
		LOGGER.info("listAll");
		List<Country> results = service.listAllCountries();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, results.size());
	}

	@Test
	public void listAllSizez() {
		LOGGER.info("listAllSizez");
		List<ParcelSize> results = service.listAllSizez();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(2, results.size());
	}

	@Test
	public void listAllCountriesPageable() {
		LOGGER.info("listAllCountriesPageable");
		List<Country> results = service.listAllCountries(pageable);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, results.size());
	}

	@Test
	public void listAllSizezPageable() {
		LOGGER.info("listAllSizezPageable");
		List<ParcelSize> results = service.listAllSizez(pageable);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(2, results.size());
	}

	@Test
	public void getShippingCost() {
		LOGGER.info("getShippingCost");
		BigDecimal result = service.getShippingCost(NAME, SIZE);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(result, new BigDecimal(10.00));
	}

	@Test
	public void getCountryByName() {
		LOGGER.info("getCountryByName");
		Country found = service.getCountryByName(NAME);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void findOneByCountryAndSize() {
		LOGGER.info("findOneByCountryAndSize");
		ParcelCost found = service.findOneByCountryAndSize(service.getCountryByName(NAME), service.findSizeById(1L));
		AssertSqlCount.assertSelectCount(3);
		Assert.assertNotNull(found);
	}

	@Test
	public void countCountries() {
		LOGGER.info("countCountries");
		long result = service.countCountries();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result);
	}

	@Test
	public void countSizes() {
		LOGGER.info("countSizes");
		long result = service.countSizes();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(2, result);
	}

}
