package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.Option;

public interface OptionDAO extends JpaRepository<Option, Long> {
	List<Option> findAll();

	Page<Option> findByOptionNameContaining(String name, Pageable pageable);

	Page<Option> findAll(Pageable pageable);

	long count();

	@Query("SELECT item FROM Option item where (:name is NULL OR item.optionName LIKE :name) ORDER BY item.id")
	Page<Option> findByName(@Param("name") String name, Pageable pageable);

	@Query("SELECT count(*) FROM Option item where (:name is NULL OR item.optionName LIKE :name) ORDER BY item.id")
	long countByName(@Param("name") String name);
}
