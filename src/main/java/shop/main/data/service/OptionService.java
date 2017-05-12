package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Option;

public interface OptionService {
	void save(Option option);
	void delete(Option option);
	List<Option> listAll();
	
//	List<Product> listAllProductsByPrice (BigInteger price);
	Option fingOptionById(long id);
}
