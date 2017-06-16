package shop.main.data.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	List<Order> findByFullNameLikeAndPhoneLikeAndEmailLike(String fullName, String phone, String email);

	List<Order> findByUsername(String username);
}
