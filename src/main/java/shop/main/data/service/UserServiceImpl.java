package shop.main.data.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.UserDAO;
import shop.main.data.entity.User;
import shop.main.data.entity.UserRole;
import shop.main.utils.Constants;
import shop.main.validation.EmailExistsException;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	PasswordEncoder passwordEncoder;

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

	@Transactional
	@Override
	public User registerNewUserAccount(User user) throws EmailExistsException {

		if (emailExist(user.getEmail())) {
			throw new EmailExistsException("There is an account with that email address:" + user.getEmail());
		}

		if (user.getPassword() == null) {
			user.setPassword(new BigInteger(130, new SecureRandom()).toString(32));
			System.out.println("+++++++++++++++" + user.getPassword());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUserRole(new HashSet<UserRole>(1));
		user.getUserRole().add(new UserRole(user, Constants.RoleType.USER.name()));
		save(user);
		return user;
	}

	private boolean emailExist(String email) {
		User user = userDAO.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

}
