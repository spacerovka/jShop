package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Block;

public interface BlockService {
	void save(Block block);
	void delete(Block block);
	List<Block> listAll();
	Block findOneByURLAndType(String blockURL, String type);
	List<Block> findAllByURL(String blockURL);
	Block findById(long id);	
	void deleteById(long id);
}
