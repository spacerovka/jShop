package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.PasswordResetToken;
import shop.main.data.entity.User;
import shop.main.data.entity.VerificationToken;
import shop.main.validation.EmailExistsException;

public interface UserService {
	void save(User user);

	List<User> findAll(String name, String status, String email, String role, Pageable pageable);

	long countAll(String name, String status, String email, String role);

	void deleteById(long id);

	User findUserById(long id);

	User findByUsername(String username);

	User registerNewUserAccount(User accountDto) throws EmailExistsException;

	/**
	 * email validation
	 * 
	 * @param user
	 * @param token
	 */
	void createVerificationTokenForUser(User user, String token);

	VerificationToken getVerificationToken(String VerificationToken);

	VerificationToken generateNewVerificationToken(String token);

	String validateVerificationToken(String token);

	User getUserByToken(String verificationToken);

	// reset password

	User findUserByEmail(String email);

	void createPasswordResetTokenForUser(User user, String token);

	PasswordResetToken getPasswordResetToken(String token);

	User getUserByPasswordResetToken(String token);

	void changeUserPassword(User user, String password);

	boolean checkIfValidOldPassword(User user, String password);

	String validatePasswordResetToken(long id, String token);

}
