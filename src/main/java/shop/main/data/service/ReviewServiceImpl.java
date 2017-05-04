package shop.main.data.service;

import java.util.List;

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
import shop.main.data.DAO.ReviewDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.Product;
import shop.main.data.objects.Review;
import shop.main.utils.HibernateUtil;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);
	
	@Autowired
	private ReviewDAO reviewDAO;
		
	@PersistenceContext
    protected EntityManager entityManager;

	@Override
	public void save(Review review) {
		reviewDAO.save(review);
		
	}

	@Override
	public void delete(Review review) {
		reviewDAO.delete(review);		
	}

	@Override
	public List<Review> listAll() {
		return reviewDAO.findAll();
	}

	@Override
	public Review fingById(long id) {		
		return reviewDAO.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		reviewDAO.delete(id);		
		
	}
	
	
}
