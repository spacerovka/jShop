package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import shop.main.data.objects.Option;

public interface OptionDAO extends CrudRepository<Option, Long>{
	List<Option> findAll();
	
	//List<BlogPost> findAllBlogPostByUserAndTitleContaining(User user, String title);
}
