package ServiceTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.User;
import shop.main.data.service.UserRoleService;
import shop.main.data.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
public class UserTest {

	private final Long USERID = 1L;
	private final String ROLE = "ADMIN";
	private final String USERNAME = "admin";

	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	// @Test
	// public void findByNameAndURL() {
	// User user = userService.fingUserById(USERID);
	// UserRole oldRole = userRoleService.findOneByUserAndRole(user, ROLE);
	//
	// System.out.println("*");
	// System.out.println("*****************found user role" +
	// oldRole.getUserRoleId());
	// System.out.println("*");
	// Assert.assertEquals(oldRole.getUser().getUsername(), USERNAME);
	// }

	@Test
	public void findByUsername() {
		User user = userService.findByUsername(USERNAME);
		System.out.println("*");
		System.out.println(user.getUsername() + " " + user.getUserRole().size() + " roles");
		System.out.println("*");
	}
	//
}
