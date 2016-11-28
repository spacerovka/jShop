package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.ProductOption;

public interface ProductOptionService {
	void save(ProductOption option);
	void delete(ProductOption option);
	List<ProductOption> listAll();
	
//	List<Product> listAllProductsByPrice (BigInteger price);
	ProductOption fingOptionById(long id);
}
