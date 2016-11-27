package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.objects.Product;



public interface ProductDAO extends CrudRepository<Product, Long>{
	List<Product> findAll();
	
	//List<BlogPost> findAllBlogPostByUserAndTitleContaining(User user, String title);
}
