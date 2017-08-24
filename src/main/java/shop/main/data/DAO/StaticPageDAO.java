package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.StaticPage;

public interface StaticPageDAO extends JpaRepository<StaticPage, Long> {
	List<StaticPage> findAll();

	StaticPage findOneByUrl(String url);

	List<StaticPage> findAllByNameContaining(String name);

	List<StaticPage> findAllByUrlContaining(String url);

	Page<StaticPage> findAll(Pageable pageable);

	long count();

	@Query("SELECT item FROM StaticPage item where (:name is NULL OR item.name LIKE :name) AND (:status is NULL OR :status ='' OR item.status = :status) ORDER BY item.id")
	Page<StaticPage> findByNameAndStatus(@Param("name") String name, @Param("status") String status,
			Pageable pageable);

	@Query("SELECT count(*) FROM StaticPage item where (:name is NULL OR item.name LIKE :name) AND (:status is NULL OR item.status = :status) ORDER BY item.id")
	long countByNameAndStatus(@Param("name") String name, @Param("status") Boolean status);

}
