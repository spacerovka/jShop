package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.StaticPage;

public interface StaticPageDAO extends JpaRepository<StaticPage, Long> {
	List<StaticPage> findAll();

	StaticPage findOneByUrl(String url);

	List<StaticPage> findAllByNameContaining(String name);

	List<StaticPage> findAllByUrlContaining(String url);
}
