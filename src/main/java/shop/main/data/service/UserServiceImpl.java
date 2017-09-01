package shop.main.data.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.PasswordResetTokenDAO;
import shop.main.data.DAO.UserDAO;
import shop.main.data.DAO.VerificationTokenDAO;
import shop.main.data.entity.PasswordResetToken;
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
	private PasswordResetTokenDAO passwordTokenDAO;

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
			deleteById(user.getId());
			entityManager.merge(user);
		}

	}

	@Override
	public User findUserById(long id) {
		return userDAO.findUserById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByUsername(String username) {

		return userDAO.findUserByUsername(username);

	}

	@Transactional
	@Override
	public void deleteById(long id) {

		Session session = (Session) entityManager.getDelegate();
		String hql_roles = "delete from UserRole role where role.user.id=:id";
		Query query = session.createQuery(hql_roles);
		query.setParameter("id", id);
		query.executeUpdate();

		String hql = "delete from User item where item.id=:id";
		Query deleteUser = session.createQuery(hql);
		deleteUser.setParameter("id", id);
		deleteUser.executeUpdate();

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
		save(user);
		verificationTokenDAO.delete(verificationToken);
		return TOKEN_VALID;
	}

	@Override
	public User getUserByToken(String verificationToken) {
		User user = verificationTokenDAO.findByToken(verificationToken).getUser();
		return user;
	}

	@Override
	public void changeUserPassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		save(user);

	}

	@Override
	public boolean checkIfValidOldPassword(User user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenDAO.save(myToken);
	}

	@Override
	public User findUserByEmail(final String email) {
		return userDAO.findByEmail(email);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordTokenDAO.findByToken(token);
	}

	@Override
	public User getUserByPasswordResetToken(final String token) {
		return passwordTokenDAO.findByToken(token).getUser();
	}

	@Override
	public String validatePasswordResetToken(long id, String token) {
		final PasswordResetToken passToken = passwordTokenDAO.findByToken(token);
		if ((passToken == null) || (passToken.getUser().getId() != id)) {
			return "invalidToken";
		}

		final LocalDateTime tooday = LocalDateTime.now();
		if (Duration.between(passToken.getExpiryDate(), tooday).toMillis() <= 0) {
			passwordTokenDAO.delete(passToken);
			return TOKEN_EXPIRED;
		}

		final User user = passToken.getUser();
		final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
				Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return null;
	}

	@Transactional
	@Override
	public List<User> findAll(String name, String status, String email, String role, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		if (email != null)
			email = "%" + email + "%";

		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}
		Session session = (Session) entityManager.getDelegate();
		String hql = "SELECT item FROM User item,UserRole r where item.username = r.user AND (:role is NULL OR :role='' OR r.role = :role) AND (:name is NULL OR item.username LIKE :name) AND (:email is NULL OR :email='' or item.email LIKE :email) AND (:status is NULL OR item.enabled = :status) group by item.id ORDER BY item.id";
		org.hibernate.Query query = session.createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("status", bStatus);
		query.setParameter("email", email);
		query.setParameter("role", role);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return query.list();
		// return userDAO.findAll(name, bStatus, email, role,
		// pageable).getContent();
	}

	@Override
	public long countAll(String name, String status, String email, String role) {
		if (name != null)
			name = "%" + name + "%";
		if (email != null)
			email = "%" + email + "%";

		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}

		return userDAO.countAll(name, bStatus, email, role);
	}

}
