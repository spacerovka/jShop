package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Category;

public interface CategoryService {
	void saveCategory(Category category);
	void deleteCategory(Category category);
	List<Category> listAll();
	List<Category> findAllParentCategories();
//	List<Product> findAllProductByCategory(Category category);
//	List<Product> listAllProductsByPrice (BigInteger price);
	Category fingCategoryById(long id);
}
