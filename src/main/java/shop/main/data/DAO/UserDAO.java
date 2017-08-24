package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.User;

public interface UserDAO extends JpaRepository<User, Long> {

	User findUserByUsername(String username);

	User findUserById(Long id);

	List<User> findAll();

	User findByEmail(String email);

	User findOneByUsername(String username);

	Page<User> findAll(Pageable pageable);

	long count();

	@Query("SELECT item FROM User item,UserRole r where item.username = r.user AND (:role is NULL OR :role='' OR r.role = :role) AND (:name is NULL OR item.username LIKE :name) AND (:email is NULL OR :email='' or item.email LIKE :email) AND (:status is NULL OR item.enabled = :status) group by item.id ORDER BY item.id")
	Page<User> findAll(@Param("name") String name, @Param("status") Boolean status, @Param("email") String email,
			@Param("role") String role, Pageable pageable);

	@Query("SELECT count(distinct item.id) FROM User item, UserRole r where item.username = r.user AND (:role is NULL OR :role='' OR r.role = :role) AND (:name is NULL OR item.username LIKE :name) AND (:email is NULL OR :email='' or item.email LIKE :email) AND (:status is NULL OR item.enabled = :status) ORDER BY item.id")
	long countAll(@Param("name") String name, @Param("status") Boolean status, @Param("email") String email,
			@Param("role") String role);

}
