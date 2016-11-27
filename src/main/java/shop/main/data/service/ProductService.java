package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Product;

public interface ProductService {
	void saveProduct(Product product);
	void deleteProduct(Product product);
	List<Product> listAll();
//	List<Product> listAllProductsByCategory(User user, boolean draft);
//	List<Product> listAllProductsByPrice (BigInteger price);
	Product fingProductById(long id);
}
