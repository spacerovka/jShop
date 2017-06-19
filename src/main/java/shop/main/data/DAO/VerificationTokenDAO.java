package shop.main.data.DAO;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.User;
import shop.main.data.entity.VerificationToken;

public interface VerificationTokenDAO extends CrudRepository<VerificationToken, Long> {
	VerificationToken findByToken(String token);

	VerificationToken findByUser(User user);
}
