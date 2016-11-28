package shop.main.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.OptionDAO;
import shop.main.data.DAO.ProductDAO;
import shop.main.data.DAO.ProductOptionDAO;
import shop.main.data.DAO.UserDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.Option;
import shop.main.data.objects.Product;
import shop.main.data.objects.ProductOption;
import shop.main.data.objects.User;

@Service("productOptionService")
public class ProductOptionServiceImpl implements ProductOptionService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(ProductOptionServiceImpl.class);
	
	@Autowired
	private ProductOptionDAO productOptionDAO;

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

	@Override
	public ProductOption fingOptionById(long id) {
		return productOptionDAO.findOne(id);
	}
	
	

}
