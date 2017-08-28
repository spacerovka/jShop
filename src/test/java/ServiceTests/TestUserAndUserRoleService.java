package ServiceTests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.User;
import shop.main.data.entity.UserRole;
import shop.main.data.service.UserRoleService;
import shop.main.data.service.UserService;
import shop.main.utils.Constants;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:users.sql" })
public class TestUserAndUserRoleService {

	private final Long USERID = 1L;
	private final String ROLE = "USER";
	private final String USERNAME = "default";
	private final Integer PAGE_SIZE = 10;
	private final Integer CURRENT = 0;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private Pageable pageable;
	private User user;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestBlockService.class);

	@Before
	public void before() {

		pageable = new PageRequest(CURRENT, PAGE_SIZE);
		user = userService.fingUserById(1);
		AssertSqlCount.reset();
	}

	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Test
	public void findByUsername() {
		User found = userService.findByUsername(USERNAME);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}
	// UserService {
	// void save(User user);
	//
	// List<User> findAll(String name, String status, String email, String role,
	// Pageable pageable);
	//
	// long countAll(String name, String status, String email, String role);
	//
	// void deleteById(long id);
	//
	// User fingUserById(long id);
	//
	// User findByUsername(String username);
	//
	// User registerNewUserAccount(User accountDto) throws EmailExistsException;
	//
	// /**
	// * email validation
	// *
	// * @param user
	// * @param token
	// */
	// void createVerificationTokenForUser(User user, String token);
	//
	// VerificationToken getVerificationToken(String VerificationToken);
	//
	// VerificationToken generateNewVerificationToken(String token);
	//
	// String validateVerificationToken(String token);
	//
	// User getUserByToken(String verificationToken);
	//
	// // reset password
	//
	// User findUserByEmail(String email);
	//
	// void createPasswordResetTokenForUser(User user, String token);
	//
	// PasswordResetToken getPasswordResetToken(String token);
	//
	// User getUserByPasswordResetToken(String token);
	//
	// void changeUserPassword(User user, String password);
	//
	// boolean checkIfValidOldPassword(User user, String password);
	//
	// String validatePasswordResetToken(long id, String token);
	//
	// }

	@Test
	public void save() {
		LOGGER.info("save");
		UserRole toSave = new UserRole(user, DUMMY_STRING);
		userRoleService.save(toSave);
		List<UserRole> found = userRoleService.findByUserName(user.getUsername());
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNotNull(found);
	}

	@Test
	public void findByUserName() {
		LOGGER.info("findByUserName");
		List<UserRole> found = userRoleService.findByUserName(user.getUsername());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(found.size(), 1);
	}

	@Test
	public void findOneByUserAndRole() {
		LOGGER.info("findOneByUserAndRole");
		UserRole found = userRoleService.findOneByUserAndRole(user, Constants.RoleType.USER.name());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}
}
