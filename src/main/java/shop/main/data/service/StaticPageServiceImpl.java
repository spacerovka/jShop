package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.StaticPageDAO;
import shop.main.data.entity.StaticPage;
import shop.main.data.entity.WishList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service("staticPageService")
public class StaticPageServiceImpl implements StaticPageService {

	@Autowired
	private StaticPageDAO dao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void save(StaticPage staticPage) {
		dao.save(staticPage);

	}

	@Override
	public StaticPage findById(long id) {

		return dao.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		dao.delete(id);

	}

	@Override
	public StaticPage findOneByURL(String url) {
		return dao.findOneByUrl(url);
	}


	@Override
	public boolean checkUniqueURL(StaticPage page) {
		StaticPage result = dao.findOneByUrl(page.getUrl());
		if (result == null) {
			return true;
		} else if (result.getId().equals(page.getId())) {
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public List<StaticPage> findByNameAndStatus(String name, String status, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}
//		return dao.findByNameAndStatus(name, bStatus, pageable).getContent();

		Session session = (Session) entityManager.getDelegate();

		String hql = "FROM StaticPage item where (:name is NULL OR item.name LIKE :name) AND (:status is NULL OR item.status = :status)";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("status", bStatus);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return (ArrayList<StaticPage>) query.list();
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

}
