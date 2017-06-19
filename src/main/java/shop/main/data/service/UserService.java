package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.User;
import shop.main.data.entity.VerificationToken;
import shop.main.validation.EmailExistsException;

public interface UserService {
	void save(User user);

	void delete(User user);

	List<User> listAll();

	void deleteById(long id);

	User fingUserById(long id);

	User findByUsername(String username);

	User registerNewUserAccount(User accountDto) throws EmailExistsException;

	// email validation
	void createVerificationTokenForUser(User user, String token);

	VerificationToken getVerificationToken(String VerificationToken);

	VerificationToken generateNewVerificationToken(String token);

	String validateVerificationToken(String token);

	User getUserByToken(String verificationToken);

	// reset password

	// User findUserByEmail(String email);

	// void createPasswordResetTokenForUser(User user, String token);

	// PasswordResetToken getPasswordResetToken(String token);
	//
	// User getUserByPasswordResetToken(String token);
	//
	// void changeUserPassword(User user, String password);
	//
	// boolean checkIfValidOldPassword(User user, String password);

}
