package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.OptionDAO;
import shop.main.data.entity.Option;

@Service("optionService")
public class OptionServiceImpl implements OptionService {

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
		Session session = (Session) entityManager.getDelegate();
		String hql = "from Option o where o.optionGroup.id =" + id;

		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return (List<Option>) query.list();
	}

	@Override
	public List<Option> findAllByName(String name, Pageable pageable) {

		return optionDAO.findByOptionNameContaining(name, pageable).getContent();
	}

	@Override
	public List<Option> findByName(String name, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		return optionDAO.findByName(name, pageable).getContent();
	}

	@Override
	public long countByName(String name) {
		if (name != null)
			name = "%" + name + "%";
		return optionDAO.countByName(name);
	}

}
