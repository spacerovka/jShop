package shop.main.data.DAO;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import shop.main.data.entity.PasswordResetToken;
import shop.main.data.entity.User;

public interface PasswordResetTokenDAO extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

	PasswordResetToken findByUser(User user);

	Stream<PasswordResetToken> findAllByExpiryDateLessThan(LocalDateTime now);

	void deleteByExpiryDateLessThan(LocalDateTime now);

	@Modifying

	@Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
	void deleteAllExpiredSince(LocalDateTime now);
}
