package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.Category;
import shop.main.data.entity.Product;
import shop.main.data.entity.Review;

public interface ReviewDAO extends CrudRepository<Review, Long>{
	List<Review> findAll();		
	
}
