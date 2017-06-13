package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.ProductOption;

public interface ProductOptionService {
	void save(ProductOption option);
	void delete(ProductOption option);
	List<ProductOption> listAll();
	List<ProductOption> findProductOptionByOption(List<Long> idList);
	List<ProductOption> findOptionsByCategoryList(List<Long> idList);
	
}
