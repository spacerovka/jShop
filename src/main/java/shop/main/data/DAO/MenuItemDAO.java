package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.MenuItem;

public interface MenuItemDAO extends JpaRepository<MenuItem, Long> {
	List<MenuItem> findAll();

	List<MenuItem> findAllByStatus(boolean status);

	Page<MenuItem> findAll(Pageable pageable);

	long count();
}
