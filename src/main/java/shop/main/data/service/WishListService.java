package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.WishList;

public interface WishListService {
	void save(WishList block);

	void delete(WishList block);

	List<WishList> listAll();

	WishList findById(long id);

	void deleteById(long id);

	ArrayList<WishList> findByProductSKUAndUsername(String productSKU, String username);

	List<WishList> findByUsername(String username, Pageable pageable);

	long countByUsername(String username);
}
