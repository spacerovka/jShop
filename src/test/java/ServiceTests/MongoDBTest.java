package ServiceTests;

import java.math.BigDecimal;
import java.util.HashMap;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderProduct;
import shop.main.data.mongo.OrderRepository;
import shop.main.data.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
public class MongoDBTest {

	private final String NAME = "This is Name";
	private final String EMAIL = "email@email.com";
	private final String PHONE = "555-77-88-33";

	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private final BigDecimal DUMMY_BIG_DECIMAL = new BigDecimal(10.00);
	private final BigDecimal SUM = new BigDecimal(100.00);
	private final Integer QUANTITY = 10;
	private Pageable pageable;
	private Order testOrder;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestBlockService.class);

	@Before
	public void before() {
		dao.deleteAll();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
		testOrder = new Order();
		testOrder.setUsername(DUMMY_STRING);
		testOrder.setFullName(NAME);
		testOrder.setPhone(PHONE);
		testOrder.setEmail(EMAIL);
		HashMap<String, OrderProduct> product_list = new HashMap<String, OrderProduct>();
		OrderProduct product = new OrderProduct(DUMMY_STRING, DUMMY_BIG_DECIMAL, QUANTITY, DUMMY_STRING);
		product_list.put(product.getProduct_SKU(), product);
		testOrder.setProduct_list(product_list);
		testOrder.setSum(SUM);
	}

	@Autowired
	@Qualifier("orderService")
	public OrderService orderService;

	@Autowired
	private OrderRepository dao;

	@Test
	public void findByUsername() {
		LOGGER.info("findByUsername");
		orderService.save(testOrder);
		List<Order> result = orderService.findByUsername(testOrder.getUsername(), pageable);
		Assert.assertEquals(result.get(0).getSum(), SUM);
	};

	@Test
	public void countByUsername() {
		LOGGER.info("countByUsername");
		orderService.save(testOrder);
		long result = orderService.countByUsername(testOrder.getUsername());
		Assert.assertEquals(result, 1);
	};

	@Test
	public void filter() {
		LOGGER.info("filter");
		orderService.save(testOrder);
		List<Order> result = orderService.filter(NAME, PHONE, EMAIL, pageable);
		Assert.assertEquals(result.size(), 1);
	};

	@Test
	public void countFiltered() {
		LOGGER.info("countFiltered");
		orderService.save(testOrder);
		long result = orderService.count(NAME, PHONE, EMAIL);
		Assert.assertEquals(result, 1);
	};

	@Test
	public void save() {
		LOGGER.info("save");
		orderService.save(testOrder);
		long result = orderService.count();
		Assert.assertEquals(result, 1);
	};

	@Test
	public void count() {
		LOGGER.info("count");
		orderService.save(testOrder);
		orderService.save(new Order());
		long result = orderService.count();
		Assert.assertEquals(result, 2);
	};

	@Test
	public void findOne() {
		LOGGER.info("findOne");
		orderService.save(testOrder);
		Order result = orderService.findOne(testOrder.getOrderId());
		Assert.assertNotNull(result);
	};

	@Test
	public void delete() {
		LOGGER.info("delete");
		orderService.save(testOrder);
		orderService.delete(testOrder.getOrderId());
		Order result = orderService.findOne(testOrder.getOrderId());
		Assert.assertNull(result);
	};

}
