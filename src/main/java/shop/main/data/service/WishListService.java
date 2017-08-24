package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.WishList;

public interface WishListService {
	void save(WishList block);

	WishList findById(long id);

	void deleteById(long id);

	boolean isInWishlist(String productSKU, String username);

	List<WishList> findByUsername(String username, Pageable pageable);

	long countByUsername(String username);
}
