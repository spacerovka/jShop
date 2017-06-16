package ComponentsText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
public class PasswordEncodeTest {

	@Qualifier("passwordEncoder")
	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	public void testAddOptionGroup() {

		String pass = "admin";
		System.out.println("pass is " + passwordEncoder.encode(pass) + "    finish");
		System.exit(1);
	}

}
