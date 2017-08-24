package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.WishListDAO;
import shop.main.data.entity.WishList;

@Service("wishListService")
public class WishListServiceImpl implements WishListService {

	@Autowired
	private WishListDAO wishListDAO;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void save(WishList item) {
		wishListDAO.save(item);

	}

	@Override
	public WishList findById(long id) {
		return wishListDAO.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		wishListDAO.delete(id);

	}

	@Transactional
	@Override
	public boolean isInWishlist(String productSKU, String username) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "SELECT count(*) FROM WishList w where w.product.sku = :productSKU  and w.user.username = :username ";
		Query query = session.createQuery(hql);
		query.setParameter("productSKU", productSKU);
		query.setParameter("username", username);
		Long result = (Long) query.uniqueResult();
		return result != null && result > 0;
	}

	@Transactional
	@Override
	public List<WishList> findByUsername(String username, Pageable pageable) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from WishList w where w.user.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return (ArrayList<WishList>) query.list();

		// return wishListDAO.findByUsername(username, pageable).getContent();
	}

	@Override
	public long countByUsername(String username) {

		return wishListDAO.countByUsername(username);
	}

}
