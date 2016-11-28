package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.objects.User;

public interface UserDAO extends CrudRepository<User, Long>{

	User findUserByUserName(String username);
	List<User> findAll();
	
}
