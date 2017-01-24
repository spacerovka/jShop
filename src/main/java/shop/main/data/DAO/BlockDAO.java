package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import shop.main.data.objects.Block;



public interface BlockDAO extends CrudRepository<Block, Long> {
	List<Block> findAll();
	Block findOneByURLAndTypeContaining(String URL, String type);
	List<Block> findAllByURL(String URL);
}
