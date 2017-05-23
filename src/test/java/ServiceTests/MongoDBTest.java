package ServiceTests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;
import shop.main.data.objects.Category;
import shop.main.data.service.CategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfig.class})
@WebAppConfiguration
public class MongoDBTest {
	
	private final String NAME = "ni";
	private final String EMAIL = "so";
	private final String PHONE = "33";
	
	@Autowired
	@Qualifier("orderRepository")	
	private OrderRepository orderRepository;
	
	
	@Test
	public void findByQuery() {
	
	List<Order> results = orderRepository.findByUserNameLikeAndPhoneLikeAndEmailLike(NAME, PHONE, EMAIL);
//	Assert.assertEquals(products.get(0).getName(), PRODUCT_NAME);
	
	results.stream().forEach(r -> System.out.println(r.getUserName()+" "+r.getPhone()+" "+r.getEmail()));
	System.out.println("*");
	System.out.println("*****************found results "+results.size());
	System.out.println("*");
	}
}
