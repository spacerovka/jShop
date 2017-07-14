package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.DiscountDAO;
import shop.main.data.entity.Discount;

public class DiscountServiceImpl implements DiscountService {
	@Autowired
	DiscountDAO discountDAO;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void save(Discount discount) {
		discountDAO.save(discount);

	}

	@Override
	public void delete(Discount discount) {
		discountDAO.delete(discount);

	}

	@Override
	public void deleteById(long id) {
		discountDAO.delete(id);

	}

	@Override
	public List<Discount> listAll() {

		return discountDAO.findAll();
	}

	@Override
	public Discount findByCoupon(String coupon) {
		Discount discount = discountDAO.findOneByCouponAndStatus(coupon, true);
		return discount;
	}

	@Override
	public List<Discount> findByNameAndStatus(String name, String status, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		return discountDAO.findByNameAndStatus(name, status, pageable).getContent();
	}

	@Override
	public Discount findById(long id) {

		return discountDAO.findOne(id);
	}

	@Override
	public long getAllCount() {
		return discountDAO.count();
	}

	@Override
	public List<Discount> listAll(Pageable pageable) {
		return discountDAO.findAll(pageable).getContent();
	}

	@Transactional
	@Override
	public long countByNameAndStatus(String name, String status) {
		if (name != null)
			name = "%" + name + "%";
		return discountDAO.countByNameAndStatus(name, status);
	}

	@Override
	public boolean notUniqueCoupon(String coupon) {

		return discountDAO.findOneByCoupon(coupon) != null;
	}

}
