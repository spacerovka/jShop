package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.BlockDAO;
import shop.main.data.objects.Block;

@Service("blockService")
public class BlockServiceImpl implements BlockService{

	
	@Autowired
	private BlockDAO blockDAO;
	
	@Override
	public void saveBlock(Block block) {
		blockDAO.save(block);
		
	}

	@Override
	public void deleteCategory(Block block) {
		blockDAO.delete(block);
		
	}

	@Override
	public List<Block> listAll() {
		
		return blockDAO.findAll();
	}

	@Override
	public Block findOneByBlockURLAndType(String URL, String type) {
		
		return blockDAO.findOneByBlockURLAndType(URL, type);
	}

	@Override
	public List<Block> findAllByBlockURL(String URL) {
		return blockDAO.findAllByBlockURL(URL);
	}

	@Override
	public Block findBlockById(long id) {
		
		return blockDAO.findOne(id);
	}

	@Override
	public void deleteBlockById(long id) {
		blockDAO.delete(id);
		
	}

}
