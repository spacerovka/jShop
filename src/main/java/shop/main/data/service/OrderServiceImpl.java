package shop.main.data.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository dao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public List<Order> findByUsername(String username, Pageable pageable) {

		return dao.findByUsername(username, pageable).getContent();
	}

	@Override
	public long countByUsername(String username) {
		return dao.countByUsername(username);
	}

	@Override
	public List<Order> filter(String fullName, String phone, String email, Pageable pageable) {
		return dao.filter(fullName, phone, email, pageable).getContent();
	}

	@Override
	public long count(String fullName, String phone, String email) {
		return dao.count(fullName, phone, email);
	}

	@Override
	public void save(Order currentOrder) {
		String orderCount = String.valueOf(count());
		orderCount = "0" + orderCount;
		currentOrder.setNumber(
				Calendar.getInstance().get(Calendar.YEAR) + "_" + orderCount + "_" + currentOrder.getSum().intValue());
		currentOrder.setDate(new Date());
		dao.save(currentOrder);

	}

	@Override
	public long count() {
		return dao.count();
	}

	@Override
	public Order findOne(String id) {
		return dao.findOne(id);
	}

	@Override
	public void delete(String id) {
		dao.delete(id);
	}

}
