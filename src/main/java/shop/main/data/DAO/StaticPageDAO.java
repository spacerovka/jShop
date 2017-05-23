package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.objects.Block;
import shop.main.data.objects.StaticPage;



public interface StaticPageDAO extends CrudRepository<StaticPage, Long> {
	List<StaticPage> findAll();
	StaticPage findOneByUrl(String url);
	List<StaticPage> findAllByNameContaining(String name);
	List<StaticPage> findAllByUrlContaining(String url);
}
