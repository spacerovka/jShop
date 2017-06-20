package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.Category;
import shop.main.data.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {
	List<Product> findAll();

	List<Product> findAllProductByCategory(Category category);

	List<Product> findAllProductByStatus(boolean status);

	List<Product> findAllProductByFeatured(boolean featured);

	Product findOneByUrl(String url);

	Product findOneBySku(String sku);
}
