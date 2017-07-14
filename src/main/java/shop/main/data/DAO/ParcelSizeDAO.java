package shop.main.data.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.ParcelSize;

public interface ParcelSizeDAO extends JpaRepository<ParcelSize, Long> {
	Page<ParcelSize> findAll(Pageable pageable);

	long count();
}
