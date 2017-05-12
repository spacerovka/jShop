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
import shop.main.data.DAO.ProductDAO;
import shop.main.data.DAO.ProductOptionDAO;
import shop.main.data.DAO.UserDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.Option;
import shop.main.data.objects.Product;
import shop.main.data.objects.ProductOption;
import shop.main.data.objects.Review;
import shop.main.data.objects.User;

@Service("productOptionService")
public class ProductOptionServiceImpl implements ProductOptionService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(ProductOptionServiceImpl.class);
	
	@Autowired
	private ProductOptionDAO productOptionDAO;
	
	@PersistenceContext
    protected EntityManager entityManager;

	@Override
	public void save(ProductOption option) {
		productOptionDAO.save(option);
		
	}

	@Override
	public void delete(ProductOption option) {
		productOptionDAO.delete(option);
	}

	@Override
	public List<ProductOption> listAll() {
		return productOptionDAO.findAll();
	}

	@Transactional
	@Override
	public List<ProductOption> findProductOptionByOption(List<Long> idList) {
		Session session =(Session)entityManager.getDelegate();
		String idListString = "(" + StringUtils.join(idList, ",") + ")";
		
		String hql = "from ProductOption o where o.option.id in "+idListString
				+" and (o.product.status = true and o.product.category.status = true)"
				+" group by o.product";

		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return (List<ProductOption>) query.list();
	}

	
	
	

}
