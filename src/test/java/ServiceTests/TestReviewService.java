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
import shop.main.data.entity.SiteProperty;
import shop.main.data.service.ProductService;
import shop.main.data.service.ReviewService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:delete_test_data.sql", "classpath:products_wishlist.sql", "classpath:reviews.sql" })
public class TestReviewService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestReviewService.class);
	private Pageable pageable;
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private final BigDecimal DUMMY_NUMBER = new BigDecimal(99.99);

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
	public void save() {
		LOGGER.info("save");
		Long productId = 1L;
		Product product = productService.findProductById(productId);
		AssertSqlCount.reset();
		Review toSave = new Review(DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, 5, product);
		reviewService.save(toSave);
		Review found = reviewService.findById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}
	@Test
	public void listAll() {
		LOGGER.info("listAll");
		List<Review> results = reviewService.listAll();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(5, results.size());
	}

	@Test
	public void deleteById() {
		LOGGER.info("deleteById");
		reviewService.deleteById(1);
		Review result = reviewService.findById(1);
		AssertSqlCount.assertSelectCount(4);
		AssertSqlCount.assertDeleteCount(1);
		Assert.assertNull(result);
	}

	@Test
	public void findById() {
		LOGGER.info("findById");
		Review result = reviewService.findById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

}
