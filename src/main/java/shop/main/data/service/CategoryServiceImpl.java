package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.CategoryDAO;
import shop.main.data.objects.Category;

@Service("categoryService")
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
	public Category findCategoryById(long id) {
		
		return categoryDAO.findOne(id);
	}

	@Override
	public List<Category> findAllParentCategories() {
		
		return categoryDAO.findAllCategoryByParentCategory(null);
	}

	@Override
	public boolean checkUniqueURL(Category category) {
		Category result = categoryDAO.findOneByCategoryURL(category.getCategoryURL());
		if(result == null){
			return true;
		}else if(result.getId().equals(category.getId())){
			return true;
		}
		return false;
	}

	@Override
	public void deleteCategoryById(long id) {
		
		List<Category> list = categoryDAO.findAllCategoryByParentCategory(categoryDAO.findOne(id));
		for (Category child : list) {
		    child.setParentCategory(null);
		    categoryDAO.save(child);
		}		
		categoryDAO.delete(id);
		
	}

}
