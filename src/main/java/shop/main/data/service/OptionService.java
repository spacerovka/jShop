package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.Option;

public interface OptionService {
	void save(Option option);
	void delete(Option option);
	List<Option> listAll();
	Option fingOptionById(long id);
	List<Option> findOptionByOptionGroup(long id);
	void deleteById(long id);
	List<Option> findAllByName(String name);
}
