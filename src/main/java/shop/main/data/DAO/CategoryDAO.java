package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Long> {
	List<Category> findAll();

	List<Category> findAllCategoryByParentCategory(Category parentCategory);

	// List<BlogPost> findAllBlogPostByUserAndTitleContaining(User user, String
	// title);
	// void update(Category category);
	Category findOneByCategoryURL(String categoryURL);

	Page<Category> findAll(Pageable pageable);

	long count();
}
