package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.StaticPage;

public interface StaticPageService {
	void save(StaticPage block);

	void delete(StaticPage block);

	List<StaticPage> listAll();

	StaticPage findOneByURL(String url);

	List<StaticPage> findAllByURL(String url);

	List<StaticPage> findAllByName(String name);

	StaticPage findById(long id);

	void deleteById(long id);

	boolean checkUniqueURL(StaticPage page);

	List<StaticPage> findByNameAndStatus(String name, String status, Pageable pageable);

	long countByNameAndStatus(String name, String status);
}
