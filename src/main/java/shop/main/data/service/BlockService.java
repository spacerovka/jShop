package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import shop.main.data.entity.Block;

public interface BlockService {
	void save(Block block);
	void delete(Block block);
	List<Block> listAll();	
	List<Block> findAllByURL(String blockURL);
	Block findById(long id);	
	void deleteById(long id);
	ArrayList<Block> findByPositionAndBlockURL(String position, String url);
}
