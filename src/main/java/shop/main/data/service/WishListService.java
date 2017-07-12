package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import shop.main.data.entity.WishList;

public interface WishListService {
	void save(WishList block);

	void delete(WishList block);

	List<WishList> listAll();

	WishList findById(long id);

	void deleteById(long id);

	ArrayList<WishList> findByProductSKUAndUsername(String productSKU, String username);

	ArrayList<WishList> findByUsername(String username);
}
