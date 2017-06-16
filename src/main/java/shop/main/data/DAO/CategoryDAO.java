package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.Category;

public interface CategoryDAO extends CrudRepository<Category, Long>{
	List<Category> findAll();
	List<Category> findAllCategoryByParentCategory(Category parentCategory);
	//List<BlogPost> findAllBlogPostByUserAndTitleContaining(User user, String title);
	//void update(Category category);
	Category findOneByCategoryURL(String categoryURL);
}
