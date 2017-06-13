package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.CategoryOption;
import shop.main.data.entity.ProductOption;

@Deprecated
public interface CategoryOptionService {
	void save(CategoryOption option);
	void delete(CategoryOption option);
	List<CategoryOption> listAll();
	List<CategoryOption> findOptionsByCategoryList(List<Long> idList);
	CategoryOption findCategoryOptionByValues(long categoryId, long optionId);
}
