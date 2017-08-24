package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.mongo.Order;

public interface OrderService {

	List<Order> findByUsername(String username, Pageable pageable);

	long countByUsername(String username);

	List<Order> filter(String fullName, String phone, String email, Pageable pageable);

	long count(String fullName, String phone, String email);

	void save(Order currentOrder);

	long count();

	Order findOne(String id);

	void delete(String id);

}
