package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.ProductOption;

public interface ProductOptionDAO extends CrudRepository<ProductOption, Long>{
	List<ProductOption> findAll();
	
	//List<BlogPost> findAllBlogPostByUserAndTitleContaining(User user, String title);
}
