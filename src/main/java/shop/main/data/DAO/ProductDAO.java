package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.Category;
import shop.main.data.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {
	List<Product> findAll();

	List<Product> findAllProductByCategory(Category category);

	List<Product> findAllProductByStatus(boolean status);

	List<Product> findAllProductByFeatured(boolean featured);

	Product findOneByUrl(String url);

	Product findOneBySku(String sku);

	Page<Product> findAll(Pageable pageable);

	long count();

	@Query("SELECT item FROM Product item where (:name is NULL OR item.name LIKE :name) AND (:url is NULL OR item.url LIKE :url) AND (:searchSKU is NULL OR item.sku LIKE :searchSKU) ORDER BY item.id")
	Page<Product> findPageable(@Param("name") String name, @Param("url") String url,
			@Param("searchSKU") String searchSKU, Pageable pageable);

	@Query("SELECT count(*) FROM Product item where (:name is NULL OR item.name LIKE :name) AND (:url is NULL OR item.url LIKE :url) AND (:searchSKU is NULL OR item.sku LIKE :searchSKU) ORDER BY item.id")
	long countPageable(@Param("name") String name, @Param("url") String url, @Param("searchSKU") String searchSKU);
}
