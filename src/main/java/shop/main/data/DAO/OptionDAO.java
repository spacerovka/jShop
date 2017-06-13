package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.Option;

public interface OptionDAO extends CrudRepository<Option, Long>{
	List<Option> findAll();
	
	List<Option> findByOptionNameContaining(String name);
}
