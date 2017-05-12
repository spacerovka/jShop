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
	public void save(Block block) {
		blockDAO.save(block);
		
	}

	@Override
	public void delete(Block block) {
		blockDAO.delete(block);
		
	}

	@Override
	public List<Block> listAll() {
		
		return blockDAO.findAll();
	}

	@Override
	public Block findOneByURLAndType(String URL, String type) {
		
		return blockDAO.findOneByBlockURLAndType(URL, type);
	}

	@Override
	public List<Block> findAllByURL(String URL) {
		return blockDAO.findAllByBlockURL(URL);
	}

	@Override
	public Block findById(long id) {
		
		return blockDAO.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		blockDAO.delete(id);
		
	}

}
