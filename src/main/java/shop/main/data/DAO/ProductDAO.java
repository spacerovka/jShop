package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.objects.Category;
import shop.main.data.objects.Product;

public interface ProductDAO extends CrudRepository<Product, Long>{
	List<Product> findAll();	
	List<Product> findAllProductByCategory(Category category);
	List<Product> findAllProductByStatus(boolean status);
	Product findOneByUrl(String url);
}
