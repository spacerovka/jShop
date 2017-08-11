package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.Category;
import shop.main.data.entity.Product;

public interface ProductService {
	void saveProduct(Product product);

	void deleteProduct(Product product);

	List<Product> listAll();

	List<Product> findAllProductByCategory(Category category);

	Product fingProductById(long id);

	Product fingProductByUrl(String url);

	Product findProductBySKU(String sku);

	boolean checkUniqueURL(Product product);

	boolean notUniqueSKU(Product product);

	void deleteProductById(long id);

	List<Product> findAllActive();

	List<Product> findAllActiveWithinActiveCategory();

	List<Product> findAllFeatured();

	void updateRating(Long productId);

	List<Product> findFilteredProducts(List<Long> filterIds);

	List<Product> findPageable(String name, String url, String searchSKU, Pageable pageable);

	long countPageable(String name, String url, String searchSKU);

	List<Product> findFilteredProductsInCategory(List<Long> filterIds, List<Long> listOfCategories, Pageable pageable);

	long countFilteredProductsInCategory(List<Long> filterIds, List<Long> listOfCategories);

	List<Product> findByNameAndSKU(String name, String sku);

}
