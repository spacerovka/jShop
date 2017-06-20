package shop.main.data.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.User;
import shop.main.data.entity.VerificationToken;

public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Long> {
	VerificationToken findByToken(String token);

	VerificationToken findByUser(User user);
}
