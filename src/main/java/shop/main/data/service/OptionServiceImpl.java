package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.OptionDAO;
import shop.main.data.DAO.ProductDAO;
import shop.main.data.DAO.UserDAO;
import shop.main.data.entity.Category;
import shop.main.data.entity.Option;
import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.entity.User;

@Service("optionService")
public class OptionServiceImpl implements OptionService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(OptionServiceImpl.class);
	
	@Autowired
	private OptionDAO optionDAO;
	
	@PersistenceContext
    protected EntityManager entityManager;

	@Override
	public void save(Option option) {
		optionDAO.save(option);
		
	}

	@Override
	public void delete(Option option) {
		optionDAO.delete(option);
	}

	@Override
	public List<Option> listAll() {
		return optionDAO.findAll();
	}

	@Override
	public Option fingOptionById(long id) {
		return optionDAO.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		optionDAO.delete(id);
		
	}
	
	@Transactional
	@Override
	public List<Option> findOptionByOptionGroup(long id) {
		Session session =(Session)entityManager.getDelegate();		
		String hql = "from Option o where o.optionGroup.id ="+id;

		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return (List<Option>) query.list();
	}

	@Override
	public List<Option> findAllByName(String name) {
		
		return optionDAO.findByOptionNameContaining(name);
	}
	
	

}
