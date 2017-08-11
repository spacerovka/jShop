package ServiceTests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.springframework.transaction.annotation.Transactional;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.Product;
import shop.main.data.service.ProductService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:delete_test_data.sql", "classpath:categories.sql", "classpath:options.sql",
		"classpath:products.sql" })
@Transactional
public class TestProductService {
	private final String PRODUCT_NAME = "DRACULAURA";
	private final String PRODUCT_URL = "draculaura";
	private final String PRODUCT_SEARCH_NAME = "drac";
	private final String PRODUCT_SEARCH_URL = "drac";
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private Pageable pageable;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestProductService.class);
	@Autowired
	private Environment environment;

	@BeforeClass
	public static void beforeClass() {
		LOGGER.info("***");
	}

	@Autowired
	@Qualifier("productService")
	private ProductService productService;

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
	//

	// methods to test

	// void saveProduct(Product product);
	//
	// void deleteProduct(Product product);
	//
	// List<Product> listAll();
	//
	// List<Product> findAllProductByCategory(Category category);
	//
	// Product fingProductById(long id);
	//
	// Product fingProductByUrl(String url);
	//
	// Product findProductBySKU(String sku);
	//
	// boolean checkUniqueURL(Product product);
	//
	// boolean notUniqueSKU(Product product);
	//
	// void deleteProductById(long id);
	//
	// List<Product> findAllActive();
	//
	// List<Product> findAllActiveWithinActiveCategory();
	//
	// List<Product> findAllFeatured();
	//
	// void updateRating(Long productId);
	//
	// List<Product> findFilteredProducts(List<Long> filterIds);
	//
	//

	// List<Product> findFilteredProductsInCategory(List<Long> filterIds,
	// List<Long> listOfCategories, Pageable pageable);
	//
	// long countFilteredProductsInCategory(List<Long> filterIds, List<Long>
	// listOfCategories);
	//
	// List<Product> findByNameAndSKU(String name, String sku);
}
