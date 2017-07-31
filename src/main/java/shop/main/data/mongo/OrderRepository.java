package shop.main.data.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	List<Order> findByFullNameLikeAndPhoneLikeAndEmailLike(String fullName, String phone, String email);

	Page<Order> findByUsername(String username, Pageable pageable);

	long countByUsername(String username);

	@Query(value = "{ $and: [ { 'fullName' : {$regex:?0,$options:'i'} }, { 'phone' : {$regex:?1,$options:'i'}}, { 'email' : {$regex:?2,$options:'i'}}  ] }")
	Page<Order> filter(String fullName, String phone, String email, Pageable pageable);

	@Query(value = "{ $and: [ { 'fullName' : {$regex:?0,$options:'i'} }, { 'phone' : {$regex:?1,$options:'i'}}, { 'email' : {$regex:?2,$options:'i'}}  ] }", count = true)
	long count(String fullName, String phone, String email);
}
