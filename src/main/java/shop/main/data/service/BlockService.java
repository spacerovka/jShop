package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Block;

public interface BlockService {
	void saveBlock(Block block);
	void deleteCategory(Block block);
	List<Block> listAll();
	Block findOneByURLAndType(String URL, String type);
	List<Block> findAllByURL(String URL);
	Block findBlockById(long id);	
	void deleteBlockById(long id);
}
