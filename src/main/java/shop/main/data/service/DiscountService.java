package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.Discount;

public interface DiscountService {
	void save(Discount discount);

	void delete(Discount discount);

	void deleteById(long id);

	Discount findByCoupon(String cupon);

	Discount findById(long id);

	long getAllCount();

	List<Discount> findByNameAndStatus(String name, String status, Pageable pageable);

	long countByNameAndStatus(String name, String status);

	boolean notUniqueCoupon(String coupon);
}
