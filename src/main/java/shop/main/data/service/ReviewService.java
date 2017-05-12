package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Category;
import shop.main.data.objects.Product;
import shop.main.data.objects.Review;

public interface ReviewService {
	void save(Review review);
	void delete(Review review);
	List<Review> listAll();	
	Review fingById(long id);	
	void deleteById(long id);
	
}
