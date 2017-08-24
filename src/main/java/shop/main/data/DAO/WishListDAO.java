package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.WishList;

public interface WishListDAO extends JpaRepository<WishList, Long> {
	List<WishList> findAll();

	@Query("SELECT item FROM WishList item,User u where item.user = u.username AND u.username=:username group by item.id ORDER BY item.id")
	Page<WishList> findByUsername(@Param("username") String username, Pageable pageable);

	@Query("SELECT count(distinct item.id) FROM WishList item,User u where item.user = u.username AND u.username=:username group by item.id ORDER BY item.id")
	Long countByUsername(@Param("username") String username);

}
