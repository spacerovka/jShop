package shop.main.data.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.Country;

public interface CountryDAO extends JpaRepository<Country, Long> {
	Country findOneByName(String name);

	Page<Country> findAll(Pageable pageable);

	long count();
}
