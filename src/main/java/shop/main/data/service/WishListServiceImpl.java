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
	public void delete(WishList item) {
		wishListDAO.delete(item);

	}

	@Override
	public List<WishList> listAll() {

		return wishListDAO.findAll();
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
	public ArrayList<WishList> findByProductSKUAndUsername(String productSKU, String username) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from WishList w where w.product.sku = '" + productSKU + "'" + " and w.user.username = '"
				+ username + "'";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return (ArrayList<WishList>) query.list();
	}

	// @Transactional
	// @Override
	// public ArrayList<WishList> findByUsername(String username) {
	//
	// Session session = (Session) entityManager.getDelegate();
	//
	// String hql = "from WishList w where w.user.username = '" + username +
	// "'";
	// Query query = session.createQuery(hql);
	// System.out.println("*");
	// System.out.println("*");
	// System.out.println("query is " + query.getQueryString());
	// System.out.println("*");
	// System.out.println("*");
	// return (ArrayList<WishList>) query.list();
	// }

	@Override
	public List<WishList> findByUsername(String username, Pageable pageable) {

		return wishListDAO.findByUsername(username, pageable).getContent();
	}

	@Override
	public long countByUsername(String username) {

		return wishListDAO.countByUsername(username);
	}

}
