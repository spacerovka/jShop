package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.SiteProperty;

public interface SitePropertyDAO extends JpaRepository<SiteProperty, Long> {
	List<SiteProperty> findAll();

	SiteProperty findOneByName(String name);
}
