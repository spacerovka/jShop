package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.OptionGroup;

public interface OptionGroupDAO extends JpaRepository<OptionGroup, Long> {
	List<OptionGroup> findAll();

	Page<OptionGroup> findByOptionGroupNameContaining(String name, Pageable pageable);

	Page<OptionGroup> findAll(Pageable pageable);

	long count();

	@Query("SELECT item FROM OptionGroup item where (:name is NULL OR item.optionGroupName LIKE :name) ORDER BY item.id")
	Page<OptionGroup> findByName(@Param("name") String name, Pageable pageable);

	@Query("SELECT count(*) FROM OptionGroup item where (:name is NULL OR item.optionGroupName LIKE :name) ORDER BY item.id")
	long countByName(@Param("name") String name);
}
