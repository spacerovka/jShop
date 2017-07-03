package shop.main.data.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.Country;
import shop.main.data.entity.ParcelCost;
import shop.main.data.entity.ParcelSize;

public interface ParcelCostDAO extends JpaRepository<ParcelCost, Long> {
	ParcelCost findOneByCountryAndSize(Country country, ParcelSize size);
}
