package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.BlockDAO;
import shop.main.data.entity.Block;

@Service("blockService")
public class BlockServiceImpl implements BlockService {

	@Autowired
	private BlockDAO dao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void save(Block block) {
		dao.save(block);

	}

	@Override
	public void delete(Block block) {
		dao.delete(block);

	}

	@Override
	public List<Block> findAllByURL(String URL) {
		return dao.findAllByBlockURL(URL);
	}

	@Override
	public Block findById(long id) {

		return dao.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		dao.delete(id);

	}

	@Transactional
	@Override
	public ArrayList<Block> findByPositionAndBlockURL(String position, String url) {

		Session session = (Session) entityManager.getDelegate();

		String hql = "from Block block where block.status = true and " + "( block.blockURL = '" + url
				+ "' or block.blockURL is null or block.blockURL='' )" + " and block.position = '" + position + "'";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return (ArrayList<Block>) query.list();

	}

	@Override
	public List<Block> listAll(Pageable pageable) {

		return dao.findAll(pageable).getContent();
	}

	@Override
	public long getAllCount() {
		return dao.count();
	}

}
