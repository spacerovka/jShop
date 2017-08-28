package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.UserRoleDAO;
import shop.main.data.entity.User;
import shop.main.data.entity.UserRole;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	@Autowired
	private UserRoleDAO userRoleDAO;

	@Override
	public void save(UserRole role) {
		userRoleDAO.save(role);

	}

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional
	@Override
	public List<UserRole> findByUserName(String username) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from UserRole role where role.user.username =:username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		return query.list();
	}

	@Transactional
	@Override
	public UserRole findOneByUserAndRole(User user, String role) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from UserRole role where role.user.username =:username and role.role=:role";
		Query query = session.createQuery(hql);
		query.setParameter("username", user.getUsername());
		query.setParameter("role", role);
		// return userRoleDAO.findOneByUserAndRole(user, role);
		return (UserRole) query.uniqueResult();
	}

}
