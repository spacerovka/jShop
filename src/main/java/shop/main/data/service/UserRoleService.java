package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.User;
import shop.main.data.entity.UserRole;

public interface UserRoleService {
	void save(UserRole role);

	void delete(UserRole role);

	List<UserRole> listAll();

	void deleteById(long id);

	UserRole fingById(long id);

	List<UserRole> findByUserName(String username);

	UserRole findOneByUserAndRole(User user, String role);
}
