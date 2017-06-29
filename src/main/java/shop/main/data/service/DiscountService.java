package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.Discount;

public interface DiscountService {
	void save(Discount discount);

	void delete(Discount discount);

	void deleteById(long id);

	List<Discount> listAll();

	Discount findByCoupon(String cupon);

	List<Discount> findByNameAndStatus(String name, String status);

	Discount findById(long id);
}
