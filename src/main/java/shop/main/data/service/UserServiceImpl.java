package shop.main.data.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.UserDAO;
import shop.main.data.DAO.VerificationTokenDAO;
import shop.main.data.entity.User;
import shop.main.data.entity.UserRole;
import shop.main.data.entity.VerificationToken;
import shop.main.utils.Constants;
import shop.main.validation.EmailExistsException;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private VerificationTokenDAO verificationTokenDAO;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PersistenceContext
	protected EntityManager entityManager;

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

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
	public User findByUsername(String username) {

		return userDAO.findUserByUsername(username);

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

	@Override
	public void createVerificationTokenForUser(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		verificationTokenDAO.save(myToken);
	}

	@Override
	public VerificationToken getVerificationToken(String VerificationToken) {

		return verificationTokenDAO.findByToken(VerificationToken);
	}

	@Override
	public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = verificationTokenDAO.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = verificationTokenDAO.save(vToken);
		return vToken;
	}

	@Override
	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = verificationTokenDAO.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}

		final User user = verificationToken.getUser();
		final LocalDateTime tooday = LocalDateTime.now();
		if (Duration.between(verificationToken.getExpiryDate(), tooday).toMillis() <= 0) {
			verificationTokenDAO.delete(verificationToken);
			return TOKEN_EXPIRED;
		}

		user.setEnabled(true);
		// tokenRepository.delete(verificationToken);
		save(user);
		return TOKEN_VALID;
	}

	@Override
	public User getUserByToken(String verificationToken) {
		User user = verificationTokenDAO.findByToken(verificationToken).getUser();
		return user;
	}

}
