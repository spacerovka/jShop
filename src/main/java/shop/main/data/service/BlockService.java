package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Block;

public interface BlockService {
	void saveBlock(Block block);
	void deleteCategory(Block block);
	List<Block> listAll();
	Block findOneByBlockURLAndType(String blockURL, String type);
	List<Block> findAllByBlockURL(String blockURL);
	Block findBlockById(long id);	
	void deleteBlockById(long id);
}
