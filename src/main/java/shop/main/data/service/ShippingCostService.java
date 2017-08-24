package shop.main.data.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.Country;
import shop.main.data.entity.ParcelCost;
import shop.main.data.entity.ParcelSize;

public interface ShippingCostService {
	void saveSize(ParcelSize size);

	void saveCountry(Country country);

	void deleteSize(ParcelSize size);

	void deleteCountry(Country country);

	void deleteSizeById(Long id);

	void deleteCountryById(Long id);

	Country findCountryById(Long id);

	ParcelSize findSizeById(Long id);

	List<Country> listAllCountries();

	List<ParcelSize> listAllSizez();

	BigDecimal getShippingCost(String countryName, String sizeName);

	Country getCountryByName(String name);

	ParcelCost findOneByCountryAndSize(Country country, ParcelSize size);

	List<Country> listAllCountries(Pageable pageable);

	List<ParcelSize> listAllSizez(Pageable pageable);

	long countCountries();

	long countSizes();

}
