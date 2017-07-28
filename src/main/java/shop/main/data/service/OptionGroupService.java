package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.OptionGroup;

public interface OptionGroupService {
	void save(OptionGroup optionGroup);

	void delete(OptionGroup optionGroup);

	List<OptionGroup> listAll();

	void deleteById(Long id);

	OptionGroup fingOptionById(long id);

	List<OptionGroup> findByName(String name, Pageable pageable);

	long countByName(String name);

	List<OptionGroup> findAllByName(String name, Pageable pageable);
}
