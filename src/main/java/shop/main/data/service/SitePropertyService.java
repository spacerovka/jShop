package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.Block;
import shop.main.data.entity.SiteProperty;

public interface SitePropertyService {
	void save(SiteProperty property);
	List<SiteProperty> listAll();
	SiteProperty findOneByName(String name);	
	SiteProperty findById(long id);	
	void deleteById(long id);
}
