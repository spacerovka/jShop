package shop.main.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.OptionGroupDAO;
import shop.main.data.objects.OptionGroup;

@Service("optionGroupService")
public class OptionGroupServiceImpl implements OptionGroupService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(OptionGroupServiceImpl.class);
	
	@Autowired
	private OptionGroupDAO optionGroupDAO;

	@Override
	public void save(OptionGroup optionGroup) {
		optionGroupDAO.save(optionGroup);
		
	}

	@Override
	public void delete(OptionGroup optionGroup) {
		optionGroupDAO.delete(optionGroup);
	}

	@Override
	public List<OptionGroup> listAll() {
		return optionGroupDAO.findAll();
	}

	@Override
	public OptionGroup fingOptionById(long id) {
		return optionGroupDAO.findOne(id);
	}

	@Override
	public void deleteById(Long id) {
		optionGroupDAO.delete(id);
		
	}

	@Override
	public List<OptionGroup> findOptionGroupByName(String name) {
		
		return optionGroupDAO.findByOptionGroupNameContaining(name);
	}
	
	

}
