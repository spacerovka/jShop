package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.entity.Block;



public interface BlockDAO extends CrudRepository<Block, Long> {
	List<Block> findAll();
	List<Block> findAllByBlockURL(String blockURL);
}
