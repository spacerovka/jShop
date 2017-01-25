package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.CategoryDAO;
import shop.main.data.DAO.MenuItemDAO;
import shop.main.data.objects.Block;
import shop.main.data.objects.MenuItem;

@Service("menuItemService")
public class MenuItemServiceImpl implements MenuItemService{

	@Autowired
	private MenuItemDAO menuDAO;
	
	@Override
	public void save(MenuItem item) {
		menuDAO.save(item);
		
	}

	@Override
	public void delete(MenuItem item) {
		menuDAO.delete(item);
		
	}

	@Override
	public List<MenuItem> listAll() {
		return menuDAO.findAll();
	}

	@Override
	public MenuItem findById(long id) {
		return menuDAO.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		menuDAO.delete(id);
		
	}

}
