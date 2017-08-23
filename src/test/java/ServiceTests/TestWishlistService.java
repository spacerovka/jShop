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
import shop.main.data.entity.Product;
import shop.main.data.entity.User;
import shop.main.data.entity.WishList;
import shop.main.data.service.ProductService;
import shop.main.data.service.UserService;
import shop.main.data.service.WishListService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:delete_wishlist_data.sql", "classpath:user.sql", "classpath:products_wishlist.sql",
		"classpath:wishlist.sql" })
public class TestWishlistService {

	private Pageable pageable;
	private User user;
	private Product product;
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private static final Logger LOGGER = LoggerFactory.getLogger(TestWishlistService.class);

	@Before
	public void before() {
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
		user = userService.fingUserById(1);
		product = productService.findProductById(1);
		AssertSqlCount.reset();
	}

	@Autowired
	@Qualifier("wishListService")
	private WishListService service;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	@Test
	public void save() {
		LOGGER.info("save");
		WishList toSave = new WishList();
		toSave.setUser(user);
		toSave.setProduct(product);
		service.save(toSave);

		WishList found = service.findById(toSave.getId());
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNotNull(found);
	}

	@Test
	public void findById() {
		LOGGER.info("findById");
		WishList result = service.findById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void deleteById() {
		LOGGER.info("deleteById");
		service.deleteById(1);
		WishList result = service.findById(1);
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void isInWishlist() {
		LOGGER.info("isInWishlist");
		WishList toSave = new WishList();
		toSave.setUser(user);
		toSave.setProduct(product);
		service.save(toSave);
		boolean result = service.isInWishlist(product.getSku(), user.getUsername());
		AssertSqlCount.assertSelectCount(2);
		Assert.assertTrue(result);
	}

	@Test
	public void findByUsername() {
		LOGGER.info("findByUsername");

		List<WishList> result = service.findByUsername(user.getUsername(), pageable);
		Assert.assertEquals(1, result.size());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result.size());

	}

	@Test
	public void countByUsername() {
		LOGGER.info("countByUsername");

		long result = service.countByUsername(user.getUsername());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result);
	}

}
