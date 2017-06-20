package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.User;
import shop.main.data.entity.UserRole;

public interface UserRoleDAO extends JpaRepository<UserRole, Long> {

	List<UserRole> findAll();

	UserRole findOneByUserAndRole(User user, String role);

}
