package shop.main.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderProduct;
import shop.main.data.mongo.OrderRepository;

@Controller
public class MongoController {

	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value = "/mongo")
	public ModelAndView displayUsers(Principal principal) {

		orderRepository.deleteAll();

		Order order = new Order();	
		
		order.setSumm(new BigDecimal(335+395));
		order.setNumber(order.hashCode());
		
		OrderProduct prod1= new OrderProduct("Lovely thing", new BigDecimal(33.5), 10);
		prod1.setProductId("00987654");
		OrderProduct prod2= new OrderProduct("Pink thing", new BigDecimal(39.5), 10);
		prod2.setProductId("8765490");

		Map<String, OrderProduct> products = new HashMap<String, OrderProduct>();
		products.put(prod1.getProductId(), prod1);
		products.put(prod2.getProductId(), prod2);
		order.setProduct_list(products);		
		
        orderRepository.save(order);
        List<Order> data = orderRepository.findAll();        
				
		return new ModelAndView("db_test/order_test", "orders", data);
	}
}
