package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.objects.Block;



public interface BlockDAO extends CrudRepository<Block, Long> {
	List<Block> findAll();
	Block findOneByBlockURLAndType(String blockURL, String type);
	List<Block> findAllByBlockURL(String blockURL);
}