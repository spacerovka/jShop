package ServiceTests;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.Product;
import shop.main.data.entity.Review;
import shop.main.data.service.ProductService;
import shop.main.data.service.ReviewService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:delete_test_data.sql", "classpath:categories.sql", "classpath:options.sql",
		"classpath:products.sql" })
public class TestProductService {
	private final String PRODUCT_NAME = "DRACULAURA";
	private final String PRODUCT_SKU = "PI90870";
	private final String PRODUCT_URL = "draculaura";
	private final String PRODUCT_SEARCH_NAME = "drac";
	private final String PRODUCT_SEARCH_URL = "drac";
	private final String DUMMY_STRING = "DUMMY_STRING";
	private final BigDecimal DUMMY_NUMBER = new BigDecimal(99.99);
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private Pageable pageable;
	private final List<Long> filterIds = Arrays.asList(new Long[] { 1L, 2L }),
			listOfCategories = Arrays.asList(new Long[] { 5L, 6L });
	private static final Logger LOGGER = LoggerFactory.getLogger(TestProductService.class);
	@Autowired
	private Environment environment;

	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	@Autowired
	@Qualifier("reviewService")
	private ReviewService reviewService;

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Test
	public void findPageableQueryCount() {
		LOGGER.info("***");
		LOGGER.info("*" + environment.getProperty("totest"));
		List<Product> products = productService.findPageable(PRODUCT_SEARCH_NAME, PRODUCT_SEARCH_URL, "", pageable);
		AssertSqlCount.assertSelectCount(1);

	}

	@Test
	public void findPageableValidateResult() {

		List<Product> products = productService.findPageable(PRODUCT_SEARCH_NAME, PRODUCT_SEARCH_URL, "", pageable);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(products.get(0).getName(), PRODUCT_NAME);
		Assert.assertEquals(products.get(0).getUrl(), PRODUCT_URL);
		Assert.assertEquals(1, products.size());

	}

	@Test
	public void countPageable() {
		long result = productService.countPageable(PRODUCT_SEARCH_NAME, PRODUCT_SEARCH_URL, "");
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result);
	}

	@Test
	public void saveProduct() {
		Product p = new Product();
		p.setSku(DUMMY_STRING);
		p.setUrl(DUMMY_STRING);
		p.setCategory(null);
		p.setName(DUMMY_STRING);
		p.setPrice(DUMMY_NUMBER);
		productService.saveProduct(p);
		Product saved = productService.findProductBySKU(DUMMY_STRING);
		Assert.assertEquals(DUMMY_STRING, saved.getUrl());
	}

	@Test
	public void deleteProduct() {
		Product saved = productService.findProductById(1);
		productService.deleteProduct(saved);
		Product found = productService.findProductById(1);
		Assert.assertNull(found);
	}

	@Test
	public void deleteProductById() {
		productService.deleteProductById(1);
		Product found = productService.findProductById(1);
		Assert.assertNull(found);
	}

	@Test
	public void findProductById() {
		Product found = productService.findProductById(1);
		Assert.assertEquals(new Long(1), found.getId());
	}

	@Test
	public void findProductByUrl() {
		Product p = new Product();
		p.setSku(DUMMY_STRING);
		p.setUrl(DUMMY_STRING);
		p.setCategory(null);
		p.setName(DUMMY_STRING);
		p.setPrice(DUMMY_NUMBER);
		productService.saveProduct(p);
		Product saved = productService.findProductByUrl(DUMMY_STRING);
		Assert.assertEquals(DUMMY_STRING, saved.getSku());
	}

	@Test
	public void findProductBySKU() {
		Product p = new Product();
		p.setSku(DUMMY_STRING);
		p.setUrl(DUMMY_STRING);
		p.setCategory(null);
		p.setName(DUMMY_STRING);
		p.setPrice(DUMMY_NUMBER);
		productService.saveProduct(p);
		Product saved = productService.findProductBySKU(DUMMY_STRING);
		Assert.assertEquals(DUMMY_STRING, saved.getUrl());
	}

	@Test
	public void checkUniqueURL() {
		Product p = new Product();
		p.setUrl(PRODUCT_URL);
		boolean unique = productService.checkUniqueURL(p);
		Assert.assertFalse(unique);
	}

	@Test
	public void checkUniqueSKU() {
		Product p = new Product();
		p.setSku(PRODUCT_SKU);
		boolean unique = productService.checkUniqueSKU(p);
		Assert.assertFalse(unique);
	}

	@Test
	public void findAllFeatured() {
		List<Product> products = productService.findAllFeatured();
		Assert.assertEquals(3, products.size());// according to sql in test
												// start
	}

	@Test
	public void countFilteredProductsInCategory() {
		long result = productService.countFilteredProductsInCategory(filterIds, listOfCategories);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(2, result);
	}

	@Test
	public void findFilteredProductsInCategoryQueryCount() {
		List<Product> products = productService.findFilteredProductsInCategory(filterIds, listOfCategories, pageable);
		// query count, and if not null query data
		AssertSqlCount.assertSelectCount(1);
	}

	@Test
	public void findFilteredProductsInCategoryValidateResult() {
		List<Product> products = productService.findFilteredProductsInCategory(filterIds, listOfCategories, pageable);
		Assert.assertEquals(2, products.size());
	}

	@Test
	public void updateRating() {
		LOGGER.info("updateRating");
		Long productId = 1L;
		Product product = productService.findProductById(productId);
		Review r = new Review(DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, 5, product);
		reviewService.save(r);
		productService.updateRating(productId);
		long rating = productService.findProductById(productId).getRating();
		Assert.assertEquals(5, rating);

	}

}
