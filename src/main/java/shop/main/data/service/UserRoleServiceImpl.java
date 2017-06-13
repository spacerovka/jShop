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

	@Override
	public void delete(UserRole role) {
		userRoleDAO.delete(role);

	}

	@Override
	public List<UserRole> listAll() {

		return userRoleDAO.findAll();
	}

	@Override
	public void deleteById(long id) {
		userRoleDAO.delete(id);

	}

	@Override
	public UserRole fingById(long id) {
		// TODO Auto-generated method stub
		return userRoleDAO.findOne(id);
	}

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional
	@Override
	public List<UserRole> findByUserName(String username) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from UserRole role where role.username ='" + username + "'";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}

	@Transactional
	@Override
	public UserRole findOneByUserAndRole(User user, String role) {
		return userRoleDAO.findOneByUserAndRole(user, role);
	}

}
