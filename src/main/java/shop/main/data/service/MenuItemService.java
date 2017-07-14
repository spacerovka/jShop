package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.MenuItem;

public interface MenuItemService {
	void save(MenuItem item);

	void delete(MenuItem item);

	MenuItem findById(long id);

	void deleteById(long id);

	List<MenuItem> findLeftMenu();

	List<MenuItem> findRightMenu();

	List<MenuItem> listAll(Pageable pageable);

	long getAllCount();
}
