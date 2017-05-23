package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.BlockDAO;
import shop.main.data.DAO.SitePropertyDAO;
import shop.main.data.objects.SiteProperty;

@Service("sitePropertyService")
public class SitePropertyServiceImpl implements SitePropertyService {

	@Autowired
	private SitePropertyDAO sitePropertyDAO;

	@Override
	public void save(SiteProperty property) {
		sitePropertyDAO.save(property);

	}

	@Override
	public void delete(SiteProperty property) {
		sitePropertyDAO.delete(property);

	}

	@Override
	public List<SiteProperty> listAll() {
		return sitePropertyDAO.findAll();
	}

	@Override
	public SiteProperty findOneByName(String name) {
		return sitePropertyDAO.findOneByName(name);
	}

	@Override
	public SiteProperty findById(long id) {

		return sitePropertyDAO.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		sitePropertyDAO.delete(id);

	}

}
