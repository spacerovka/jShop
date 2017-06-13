package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.CategoryOption;
import shop.main.data.entity.ProductOption;

public interface CategoryOptionDAO extends CrudRepository<CategoryOption, Long>{
	List<CategoryOption> findAll();
	
	//List<BlogPost> findAllBlogPostByUserAndTitleContaining(User user, String title);
}
