package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.MenuItem;

public interface MenuItemDAO extends JpaRepository<MenuItem, Long> {

	List<MenuItem> findAllByStatus(boolean status);

	long count();
}
