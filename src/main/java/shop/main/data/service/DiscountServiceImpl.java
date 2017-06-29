package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Transactional
	@Override
	public List<Discount> findByNameAndStatus(String name, String status) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from Discount item where item.salename like '%" + name + "%'";
		if (status != null && !status.isEmpty()) {
			hql += " and item.status = " + status + "";
		}
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}

	@Override
	public Discount findById(long id) {

		return discountDAO.findOne(id);
	}

}
