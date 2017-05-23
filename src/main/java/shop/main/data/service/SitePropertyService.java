package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Block;
import shop.main.data.objects.SiteProperty;

public interface SitePropertyService {
	void save(SiteProperty property);
	void delete(SiteProperty property);
	List<SiteProperty> listAll();
	SiteProperty findOneByName(String name);	
	SiteProperty findById(long id);	
	void deleteById(long id);
}
