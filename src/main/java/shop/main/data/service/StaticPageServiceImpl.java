package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.StaticPageDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.StaticPage;

@Service("staticPageService")
public class StaticPageServiceImpl implements StaticPageService{

	
	@Autowired
	private StaticPageDAO staticPageDAO;
	
	@Override
	public void save(StaticPage staticPage) {
		staticPageDAO.save(staticPage);
		
	}

	@Override
	public void delete(StaticPage staticPage) {
		staticPageDAO.delete(staticPage);
		
	}

	@Override
	public List<StaticPage> listAll() {
		
		return staticPageDAO.findAll();
	}

	
	@Override
	public List<StaticPage> findAllByURL(String url) {
		return staticPageDAO.findAllByUrlContaining(url);
	}

	@Override
	public StaticPage findById(long id) {
		
		return staticPageDAO.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		staticPageDAO.delete(id);
		
	}

	@Override
	public StaticPage findOneByURL(String url) {
		return staticPageDAO.findOneByUrl(url);
	}

	@Override
	public List<StaticPage> findAllByName(String name) {
		return staticPageDAO.findAllByNameContaining(name);
	}
	
	@Override
	public boolean checkUniqueURL(StaticPage page) {
		StaticPage result = staticPageDAO.findOneByUrl(page.getUrl());
		if(result == null){
			return true;
		}else if(result.getId().equals(page.getId())){
			return true;
		}
		return false;
	}

}
