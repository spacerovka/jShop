package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.User;

public interface UserDAO extends CrudRepository<User, Long> {

	User findUserByUsername(String username);

	User findUserById(Long id);

	List<User> findAll();

	User findByEmail(String email);

	User findOneByUsername(String username);

}
