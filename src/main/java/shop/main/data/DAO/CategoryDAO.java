package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Long> {
	List<Category> findAll();

	List<Category> findAllCategoryByParentCategory(Category parentCategory);

	@Query("SELECT item FROM Category item where item.status = true and item.categoryURL =:url ORDER BY item.id")
	List<Category> findCategoryByUrl(@Param("url") String url);

	Page<Category> findAll(Pageable pageable);

	long count();

	@Query("SELECT item FROM Category item where (:name is NULL OR item.categoryName LIKE :name) AND (:url is NULL OR item.categoryURL LIKE :url) ORDER BY item.id")
	Page<Category> findByNameAndURL(@Param("name") String name, @Param("url") String url, Pageable pageable);

	@Query("SELECT count(distinct item.id) FROM Category item where (:name is NULL OR item.categoryName LIKE :name) AND (:url is NULL OR item.categoryURL LIKE :url) ORDER BY item.id")
	long countByNameAndURL(@Param("name") String name, @Param("url") String url);

	List<Category> findAllCategoryByParentCategoryAndStatus(Category parentCategory, boolean status);
}
