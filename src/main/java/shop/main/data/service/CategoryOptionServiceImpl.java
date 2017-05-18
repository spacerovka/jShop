package shop.main.data.service;

import java.util.List;
import java.util.OptionalDouble;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import shop.main.data.DAO.OptionDAO;
import shop.main.data.DAO.CategoryDAO;
import shop.main.data.DAO.CategoryOptionDAO;
import shop.main.data.DAO.UserDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.Option;
import shop.main.data.objects.Category;
import shop.main.data.objects.CategoryOption;
import shop.main.data.objects.Review;
import shop.main.data.objects.User;

@Deprecated
@Service("categoryOptionService")
public class CategoryOptionServiceImpl implements CategoryOptionService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(CategoryOptionServiceImpl.class);
	
	@Autowired
	private CategoryOptionDAO productOptionDAO;
	
	@PersistenceContext
    protected EntityManager entityManager;

	@Override
	public void save(CategoryOption option) {
		productOptionDAO.save(option);
		
	}

	@Override
	public void delete(CategoryOption option) {
		productOptionDAO.delete(option);
	}

	@Override
	public List<CategoryOption> listAll() {
		return productOptionDAO.findAll();
	}

	@Transactional
	@Override
	public List<CategoryOption> findOptionsByCategoryList(List<Long> idList) {
		Session session =(Session)entityManager.getDelegate();
		String idListString = "(" + StringUtils.join(idList, ",") + ")";
		

		String hql = "from CategoryOption o where o.category.id in "+idListString
				+" and o.category.status = true"
				+" group by o.option";

		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return (List<CategoryOption>) query.list();
	}

	@Transactional
	@Override
	public CategoryOption findCategoryOptionByValues(long categoryId, long optionId) {
		Session session =(Session)entityManager.getDelegate();
		
		String hql = "from CategoryOption o where o.category.id = "+categoryId
				+" and o.option.id = "+optionId
				+" limit 1";

		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return (CategoryOption) query.uniqueResult();
	}
	

	
	
	

}
