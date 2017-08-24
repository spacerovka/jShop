package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.Option;

public interface OptionService {
	void save(Option option);

	void delete(Option option);

	List<Option> listAll();

	Option findOptionById(long id);

	List<Option> findOptionByOptionGroup(long id);

	void deleteById(long id);

	List<Option> findByName(String name, Pageable pageable);

	long countByName(String name);

	List<Option> findAllByName(String name, Pageable pageable);
}
