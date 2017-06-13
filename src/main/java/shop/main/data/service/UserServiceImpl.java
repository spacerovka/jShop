package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.UserDAO;
import shop.main.data.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional
	@Override
	public void save(User user) {
		if (user.getId() == null) {
			user.setId(userDAO.count() + 1);
			entityManager.persist(user);
		} else {
			User oldUser = fingUserById(user.getId());
			delete(oldUser);
			entityManager.merge(user);
		}

	}

	@Override
	public void delete(User user) {
		userDAO.delete(user);
	}

	@Override
	public List<User> listAll() {
		return userDAO.findAll();
	}

	@Override
	public User fingUserById(long id) {
		return userDAO.findUserById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByUserName(String username) {

		return userDAO.findOneByUserName(username);

	}

	@Override
	public void deleteById(long id) {
		delete(userDAO.findUserById(id));

	}

}
