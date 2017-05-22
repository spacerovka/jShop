package shop.main.data.mongo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
	
	List<Order> findByUserNameLikeAndPhoneLikeAndEmailLike(String userNAme, 
									            String phone,
									            String email);

}
