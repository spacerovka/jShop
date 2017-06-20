package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.OptionGroup;

public interface OptionGroupDAO extends JpaRepository<OptionGroup, Long> {
	List<OptionGroup> findAll();

	List<OptionGroup> findByOptionGroupNameContaining(String name);

}
