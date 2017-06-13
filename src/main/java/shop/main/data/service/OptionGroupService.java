package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.OptionGroup;

public interface OptionGroupService {
	void save(OptionGroup optionGroup);
	void delete(OptionGroup optionGroup);
	List<OptionGroup> listAll();	
	void deleteById(Long id);
	OptionGroup fingOptionById(long id);
	List<OptionGroup> findOptionGroupByName(String name);
}
