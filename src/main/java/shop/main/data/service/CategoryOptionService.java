package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.CategoryOption;
import shop.main.data.objects.ProductOption;

public interface CategoryOptionService {
	void save(CategoryOption option);
	void delete(CategoryOption option);
	List<CategoryOption> listAll();
	List<CategoryOption> findOptionsByCategoryList(List<Long> idList);
}
