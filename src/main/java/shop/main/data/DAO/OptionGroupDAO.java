package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.OptionGroup;

public interface OptionGroupDAO extends CrudRepository<OptionGroup, Long>{
	List<OptionGroup> findAll();
	List<OptionGroup> findByOptionGroupNameContaining(String name);
	
}
