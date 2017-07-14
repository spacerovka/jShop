package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.Block;

public interface BlockService {
	void save(Block block);

	void delete(Block block);

	long getAllCount();

	List<Block> listAll(Pageable pageable);

	List<Block> findAllByURL(String blockURL);

	Block findById(long id);

	void deleteById(long id);

	ArrayList<Block> findByPositionAndBlockURL(String position, String url);
}
