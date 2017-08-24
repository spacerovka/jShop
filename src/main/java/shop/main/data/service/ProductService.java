package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.Product;

public interface ProductService {
	void saveProduct(Product product);

	void deleteProduct(Product product);

	Product findProductById(long id);

	Product findProductByUrl(String url);

	Product findProductBySKU(String sku);

	boolean checkUniqueURL(Product product);

	boolean checkUniqueSKU(Product product);

	void deleteProductById(long id);

	List<Product> findAllFeatured();

	void updateRating(Long productId);

	List<Product> findPageable(String name, String url, String searchSKU, Pageable pageable);

	long countPageable(String name, String url, String searchSKU);

	List<Product> findFilteredProductsInCategory(List<Long> filterIds, List<Long> listOfCategories, Pageable pageable);

	long countFilteredProductsInCategory(List<Long> filterIds, List<Long> listOfCategories);

}
