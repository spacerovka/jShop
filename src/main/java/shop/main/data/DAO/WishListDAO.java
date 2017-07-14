package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.WishList;

public interface WishListDAO extends JpaRepository<WishList, Long> {
	List<WishList> findAll();

	Page<WishList> findAll(Pageable pageable);

	long count();

}
