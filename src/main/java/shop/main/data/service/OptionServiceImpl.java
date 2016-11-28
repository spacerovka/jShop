package shop.main.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.OptionDAO;
import shop.main.data.DAO.ProductDAO;
import shop.main.data.DAO.UserDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.Option;
import shop.main.data.objects.Product;
import shop.main.data.objects.User;

@Service("productService")
public class OptionServiceImpl implements OptionService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(OptionServiceImpl.class);
	
	@Autowired
	private OptionDAO optionDAO;

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
	
	

}
