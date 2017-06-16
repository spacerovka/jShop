package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.MenuItem;

public interface MenuItemDAO extends CrudRepository<MenuItem, Long>{
	List<MenuItem> findAll();
	List<MenuItem> findAllByStatus(boolean status);
	
}
