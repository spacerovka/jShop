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
	
	//email validation
	void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);
    String validateVerificationToken(String token);
    
    //reset password

//    PasswordResetToken getPasswordResetToken(String token);
//
//    User getUserByPasswordResetToken(String token);
//
//    void changeUserPassword(User user, String password);
//
//    boolean checkIfValidOldPassword(User user, String password);

    
}
