package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.Category;
import shop.main.data.entity.Product;

public interface CategoryService {
	void saveCategory(Category category);
	void deleteCategory(Category category);
	List<Category> listAll();
	List<Category> findAllParentCategories();
	Category fingCategoryByUrl(String url);
	Category findCategoryById(long id);
	boolean checkUniqueURL(Category category);
	void deleteCategoryById(long id);
	List<Category> findByNameAndURL(String name, String url);
}
