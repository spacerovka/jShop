package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import shop.main.data.DAO.CategoryDAO;
import shop.main.data.objects.Category;

public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Override
	public void saveCategory(Category category) {

		categoryDAO.save(category);
		
	}

	@Override
	public void deleteCategory(Category category) {

		categoryDAO.delete(category);
	}

	@Override
	public List<Category> listAll() {

		return categoryDAO.findAll();
	}

	@Override
	public Category fingCategoryById(long id) {
		
		return categoryDAO.findOne(id);
	}

}
