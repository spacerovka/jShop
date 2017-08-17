package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.ContactUsMessageDAO;
import shop.main.data.entity.ContactUsMessage;

public class ContactUsMessageServiceImpl implements ContactUsMessageService {

	@Autowired
	private ContactUsMessageDAO dao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void save(ContactUsMessage item) {
		dao.save(item);

	}

	@Override
	public void deleteById(long id) {
		dao.delete(id);

	}

	@Override
	public ContactUsMessage findById(long id) {

		return dao.findOne(id);
	}

	@Transactional
	@Override
	public List<ContactUsMessage> findByStatus(String status, Pageable pageable) {
		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}
		Session session = (Session) entityManager.getDelegate();

		String hql = "from ContactUsMessage item where item.watched = :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", bStatus);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		return query.list();
		// return dao.findByStatus(bStatus, pageable).getContent();
	}

	@Override
	public long countByStatus(String status) {
		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}
		return dao.countByStatus(bStatus);
	}

}
