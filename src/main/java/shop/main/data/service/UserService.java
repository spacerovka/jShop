package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.User;

public interface UserService {
	void save(User user);
	void delete(User user);
	List<User> listAll();
	
//	List<Product> listAllProductsByPrice (BigInteger price);
	User fingUserById(long id);
	User findByUserName(String username);
}
