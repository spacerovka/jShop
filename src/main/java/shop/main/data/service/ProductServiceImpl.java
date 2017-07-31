package shop.main.data.service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

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

import shop.main.data.DAO.ProductDAO;
import shop.main.data.DAO.ProductOptionDAO;
import shop.main.data.entity.Category;
import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.entity.Review;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductOptionDAO productOptionDAO;

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private ProductOptionService productOptionService;

	@Override
	public void saveProduct(Product product) {
		LOGGER.info("ProductServiceImpl: save product is called");
		productDAO.save(product);

	}

	@Override
	public void deleteProduct(Product product) {

		LOGGER.info("ProductServiceImpl: delete product is called");
		productDAO.delete(product);

	}

	@Override
	public Product fingProductById(long id) {

		return productDAO.findOne(id);
	}

	@Override
	public List<Product> listAll() {

		return productDAO.findAll();
	}

	@Override
	public List<Product> findAllProductByCategory(Category category) {

		return productDAO.findAllProductByCategory(category);
	}

	@Override
	public boolean checkUniqueURL(Product product) {
		Product result = productDAO.findOneByUrl(product.getUrl());
		if (result == null) {
			return true;
		} else if (result.getId().equals(product.getId())) {
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

		Session session = (Session) entityManager.getDelegate();

		String hql = "from Product product where product.status = true and product.category.status = true";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}

	@Transactional
	@Override
	public List<Product> findAllFeatured() {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from Product product where product.status = true and product.featured=true and product.category.status = true";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}

	@Transactional
	@Override
	public void updateRating(Long productId) {
		Session session = (Session) entityManager.getDelegate();

		List<Review> reviews = productDAO.findOne(productId).getReviews();
		OptionalDouble rating = reviews.stream().mapToInt(a -> a.getRating()).average();
		String hql = "update Product set rating = '" + Math.round(rating.getAsDouble()) + "' where id = '" + productId
				+ "'";
		// UPDATE `test_spring`.`product` SET `rating`='2' WHERE `id`='2';
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		query.executeUpdate();
		// return query.list();
	}

	@Override
	public List<Product> findFilteredProducts(List<Long> filterIds) {
		List<ProductOption> options = productOptionService.findProductOptionByOption(filterIds);
		List<Product> products = options.stream().map(ProductOption::getProduct).collect(Collectors.toList());
		return products;
	}

	@Transactional
	@Override
	public List<Product> findFilteredProductsInCategory(List<Long> filterIds, List<Long> listOfCategories,
			Pageable pageable) {

		if (filterIds != null && filterIds.isEmpty())
			filterIds = null;
		if (listOfCategories != null && listOfCategories.isEmpty())
			listOfCategories = null;

		if (filterIds == null || filterIds.isEmpty()) {
			if (listOfCategories == null || listOfCategories.isEmpty()) {
				return productDAO.findFilteredProductsInCategory(pageable).getContent();
			} else {
				return productDAO.findFilteredProductsInCategory(listOfCategories, pageable).getContent();
			}
		} else {
			return productDAO.findFilteredProductsInCategory(filterIds, listOfCategories, pageable).getContent();
		}

	}

	@Override
	public long countFilteredProductsInCategory(List<Long> filterIds, List<Long> listOfCategories) {
		if (filterIds == null || filterIds.isEmpty()) {
			if (listOfCategories == null || listOfCategories.isEmpty()) {
				return productDAO.countFilteredProductsInCategory();
			} else {
				return productDAO.countFilteredProductsInCategory(listOfCategories);
			}
		} else {
			return productDAO.countFilteredProductsInCategory(filterIds, listOfCategories);
		}

	}

	@Override
	public Product findProductBySKU(String sku) {

		return productDAO.findOneBySku(sku);
	}

	@Override
	public boolean notUniqueSKU(Product product) {
		Product result = productDAO.findOneBySku(product.getSku());
		if (result == null) {
			return true;
		} else if (result.getId().equals(product.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public List<Product> findPageable(String name, String url, String searchSKU, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		if (url != null)
			url = "%" + url + "%";
		if (searchSKU != null)
			searchSKU = "%" + searchSKU + "%";
		return productDAO.findPageable(name, url, searchSKU, pageable).getContent();
	}

	@Override
	public long countPageable(String name, String url, String searchSKU) {
		if (name != null)
			name = "%" + name + "%";
		if (url != null)
			url = "%" + url + "%";
		if (searchSKU != null)
			searchSKU = "%" + searchSKU + "%";
		return productDAO.countPageable(name, url, searchSKU);
	}

	@Transactional
	@Override
	public List<Product> findByNameAndSKU(String name, String sku) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from Product product where product.status = true and product.name like '%" + name + "%'"
				+ " and product.sku like '%" + sku + "%'";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}

}
