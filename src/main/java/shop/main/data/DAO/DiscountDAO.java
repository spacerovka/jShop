package shop.main.data.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.Discount;

public interface DiscountDAO extends JpaRepository<Discount, Long> {
	Discount findOneByCouponAndStatus(String coupon, boolean status);
}
