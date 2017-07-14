package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.Option;

public interface OptionDAO extends JpaRepository<Option, Long> {
	List<Option> findAll();

	List<Option> findByOptionNameContaining(String name);

	Page<Option> findAll(Pageable pageable);

	long count();
}
