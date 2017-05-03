package shop.main.data.service;

import java.util.List;
import java.util.OptionalDouble;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.ProductDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.Product;
import shop.main.data.objects.Review;
import shop.main.utils.HibernateUtil;

@Service("productService")
public class ProductServiceImpl implements ProductService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductDAO productDAO;
		
	@PersistenceContext
    protected EntityManager entityManager;
	
	@Override
	public void saveProduct(Product product) {
		LOGGER.info("ProductServiceImpl: save product is called");
		productDAO.save(product);
		
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		LOGGER.info("ProductServiceImpl: delete product is called");
		productDAO.delete(product);
		
	}

	@Override
	public Product fingProductById(long id) {
		// TODO Auto-generated method stub
		return productDAO.findOne(id);
	}

	@Override
	public List<Product> listAll() {
		// TODO Auto-generated method stub
		return productDAO.findAll();
	}

	@Override
	public List<Product> findAllProductByCategory(Category category) {
		// TODO Auto-generated method stub
		return productDAO.findAllProductByCategory(category);
	}
	
	@Override
	public boolean checkUniqueURL(Product product) {
		Product result = productDAO.findOneByUrl(product.getUrl());
		if(result == null){
			return true;
		}else if(result.getId().equals(product.getId())){
			return true;
		}
		return false;
	}

	@Override
	public void deleteProductById(long id) {
				
		productDAO.delete(id);
		
	}

	@Override
	public Product fingProductByUrl(String url) {

		return productDAO.findOneByUrl(url);
	}

	@Override
	public List<Product> findAllActive() {
		
		return productDAO.findAllProductByStatus(true);
	}
	
	@Transactional
	@Override
	public List<Product> findAllActiveWithinActiveCategory() {
		
		Session session =(Session)entityManager.getDelegate();
		
		String hql = "from Product product where product.status = true and product.category.status = true";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}
	
	@Transactional
	@Override
	public List<Product> findAllFeatured() {
		Session session =(Session)entityManager.getDelegate();
		
		String hql = "from Product product where product.status = true and product.featured=true and product.category.status = true";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}
	
	@Transactional
	@Override
	public void updateRating(Long productId) {
		Session session =(Session)entityManager.getDelegate();
		
		List<Review> reviews = productDAO.findOne(productId).getReviews();
		OptionalDouble rating = reviews.stream().mapToInt(a -> a.getRating()).average();
		String hql = "update Product set rating = '"+Math.round(rating.getAsDouble())+"' where id = '"+productId+"'";
		//UPDATE `test_spring`.`product` SET `rating`='2' WHERE `id`='2';
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		query.executeUpdate();
//		return query.list();
	}

}
