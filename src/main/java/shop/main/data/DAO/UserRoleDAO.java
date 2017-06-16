package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.User;
import shop.main.data.entity.UserRole;

public interface UserRoleDAO extends CrudRepository<UserRole, Long> {

	List<UserRole> findAll();

	UserRole findOneByUserAndRole(User user, String role);

}
