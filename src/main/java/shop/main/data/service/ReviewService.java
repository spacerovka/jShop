package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.Category;
import shop.main.data.entity.Product;
import shop.main.data.entity.Review;

public interface ReviewService {
	void save(Review review);
	List<Review> listAll();
	Review findById(long id);
	void deleteById(long id);
	
}
