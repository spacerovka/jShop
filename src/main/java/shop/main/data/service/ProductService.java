package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Category;
import shop.main.data.objects.Product;

public interface ProductService {
	void saveProduct(Product product);
	void deleteProduct(Product product);
	List<Product> listAll();
	List<Product> findAllProductByCategory(Category category);
//	List<Product> listAllProductsByPrice (BigInteger price);
	Product fingProductById(long id);
}
