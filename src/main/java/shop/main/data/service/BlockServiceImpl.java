package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.BlockDAO;
import shop.main.data.entity.Block;
import shop.main.data.entity.Category;

@Service("blockService")
public class BlockServiceImpl implements BlockService{

	
	@Autowired
	private BlockDAO blockDAO;
	
	@PersistenceContext
    protected EntityManager entityManager;
	
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

	@Transactional
	@Override
	public ArrayList<Block> findByPositionAndBlockURL(String position, String url) {
		
			Session session =(Session)entityManager.getDelegate();
			
			String hql = "from Block block where block.status = true and "
					+ "( block.blockURL = '"+url+"' or block.blockURL is null or block.blockURL='' )"
					+" and block.position = '"+position+"'";
			Query query = session.createQuery(hql);
			System.out.println("*");
			System.out.println("*");
			System.out.println("query is "+query.getQueryString());
			System.out.println("*");
			System.out.println("*");			
			return (ArrayList<Block>)query.list();
		
	}

}
