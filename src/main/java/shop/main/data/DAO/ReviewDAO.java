package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.objects.Category;
import shop.main.data.objects.Product;
import shop.main.data.objects.Review;

public interface ReviewDAO extends CrudRepository<Review, Long>{
	List<Review> findAll();		
	
}
