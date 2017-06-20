package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.ProductOption;

public interface ProductOptionDAO extends JpaRepository<ProductOption, Long> {
	List<ProductOption> findAll();
}
