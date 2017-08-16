package ServiceTests;

import java.util.List;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
public class MongoDBTest {

	private final String NAME = "ni";
	private final String EMAIL = "so";
	private final String PHONE = "33";

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
	@Qualifier("orderRepository")
	private OrderRepository orderRepository;

	@Test
	public void findByQuery() {

		List<Order> results = orderRepository.findByFullNameLikeAndPhoneLikeAndEmailLike(NAME, PHONE, EMAIL);
		// Assert.assertEquals(products.get(0).getName(), PRODUCT_NAME);

		results.stream().forEach(r -> System.out.println(r.getUsername() + " " + r.getPhone() + " " + r.getEmail()));
		System.out.println("*");
		System.out.println("*****************found results " + results.size());
		System.out.println("*");
	}
}
