package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.DiscountDAO;
import shop.main.data.entity.Discount;

public class DiscountServiceImpl implements DiscountService {
	@Autowired
	DiscountDAO dao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void save(Discount discount) {
		dao.save(discount);

	}

	@Override
	public void deleteById(long id) {
		dao.delete(id);

	}

	@Override
	public Discount findByCoupon(String coupon) {
		Discount discount = dao.findOneByCouponAndStatus(coupon, true);
		return discount;
	}

	@Transactional
	@Override
	public List<Discount> findByNameAndStatus(String name, String status, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}
		// return dao.findByNameAndStatus(name, bStatus, pageable).getContent();
		Session session = (Session) entityManager.getDelegate();

		String hql = "SELECT item FROM Discount item where (:name is NULL OR item.salename LIKE :name) AND (:status is NULL OR item.status = :status) ORDER BY item.id";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("status", bStatus);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return query.list();
	}

	@Override
	public Discount findById(long id) {

		return dao.findOne(id);
	}

	@Transactional
	@Override
	public long countByNameAndStatus(String name, String status) {
		if (name != null)
			name = "%" + name + "%";
		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}
		return dao.countByNameAndStatus(name, bStatus);
	}

	@Override
	public boolean notUniqueCoupon(String coupon) {

		return dao.findOneByCoupon(coupon) != null;
	}

}
