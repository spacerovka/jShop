package shop.main.data.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.Discount;

public interface DiscountDAO extends JpaRepository<Discount, Long> {
	Discount findOneByCouponAndStatus(String coupon, boolean status);

	@Query("SELECT item FROM Discount item where (:name is NULL OR item.salename LIKE :name) AND (:status is NULL OR item.status = :status) ORDER BY item.id")
	Page<Discount> findByNameAndStatus(@Param("name") String name, @Param("status") Boolean status, Pageable pageable);

	@Query("SELECT count(*) FROM Discount item where (:name is NULL OR item.salename LIKE :name) AND (:status is NULL OR item.status = :status) ORDER BY item.id")
	long countByNameAndStatus(@Param("name") String name, @Param("status") Boolean status);

	Page<Discount> findAll(Pageable pageable);

	long count();

	Discount findOneByCoupon(String coupon);
}
