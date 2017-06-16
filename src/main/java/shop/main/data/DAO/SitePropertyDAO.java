package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.Option;
import shop.main.data.entity.SiteProperty;

public interface SitePropertyDAO extends CrudRepository<SiteProperty, Long>{
	List<SiteProperty> findAll();
	
	SiteProperty findOneByName(String name);
}
