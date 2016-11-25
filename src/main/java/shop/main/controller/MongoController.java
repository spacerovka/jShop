package shop.main.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;

@Controller
public class MongoController {

	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value = "/mongo")
	public ModelAndView displayUsers(Principal principal) {
		// embedded database
		/*Order order = new Order();
		
		order.setProduct_name("Lovely thing");
		order.setProduct_quantity(3);
		order.setSumm(new BigDecimal(100.66));
		order.setNumber(order.hashCode());
		
		Order order2 = new Order();
		
		order2.setProduct_name("Pink thing");
		order2.setProduct_quantity(6);
		order2.setSumm(new BigDecimal(17.99));
		order2.setNumber(order2.hashCode());
		
        orderRepository.save(order);
        orderRepository.save(order2);*/
        List<Order> data = orderRepository.findAll();        
				
		return new ModelAndView("db_test/order_test", "orders", data);
	}
}
