package ServiceTests;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.BeforeClass;
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
import shop.main.data.service.ProductService;
import shop.main.data.service.ReviewService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:delete_test_data.sql", "classpath:categories.sql", "classpath:options.sql",
		"classpath:products.sql" })
public class TestReviewService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestProductService.class);
	private Pageable pageable;
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private final BigDecimal DUMMY_NUMBER = new BigDecimal(99.99);

	@Autowired
	private Environment environment;

	@BeforeClass
	public static void beforeClass() {
		LOGGER.info("***");
	}

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

}
