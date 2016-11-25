package shop.main.controller;

import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class EmbeddedDatabaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedDatabaseController.class);


	@Autowired
	@Qualifier("dataSourceEmbedded")
	private DataSource embeded;
	
	/*@JdbcTemplate This class executes SQL queries or updates, 
	 * initiating iteration over ResultSets 
	 * and catching JDBC exceptions 
	 * and translating them to the generic, 
	 * more informative exception hierarchy 
	 * defined in the org.springframework.dao package. 
	 */	
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value="/embeded")
	public ModelAndView displayUsers(Principal principal) {
		//embedded database
		jdbcTemplate = new JdbcTemplate(embeded);
		
		List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT*FROM USER");
		List<String> data = new ArrayList<String>();
		for(Map<String, Object> user: users) {
			data.add("username: "+ user.get("username"));
			LOGGER.debug("username: "+ user.get("username"));
		}
		return new ModelAndView("db_test/embeded_db_test", "users", data);
	}
	
}
