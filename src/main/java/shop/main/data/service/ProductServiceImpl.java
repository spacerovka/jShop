package shop.main.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.ProductDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.Product;

@Service("productService")
public class ProductServiceImpl implements ProductService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductDAO productDAO;
	
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

}
