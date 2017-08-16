package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.Block;

public interface BlockDAO extends JpaRepository<Block, Long> {

	List<Block> findAll();

	List<Block> findAllByBlockURL(String blockURL);

	long count();
}
