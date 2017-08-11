package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.Category;
import shop.main.data.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {
	List<Product> findAll();

	List<Product> findAllProductByCategory(Category category);

	List<Product> findAllProductByStatus(boolean status);

	List<Product> findAllProductByFeatured(boolean featured);

	Product findOneByUrl(String url);

	Product findOneBySku(String sku);

	Page<Product> findAll(Pageable pageable);

	long count();

	@Query("SELECT item FROM Product item where (:name is NULL OR item.name LIKE :name) AND (:url is NULL OR item.url LIKE :url) AND (:searchSKU is NULL OR item.sku LIKE :searchSKU) GROUP BY item.id ORDER BY item.id")
	Page<Product> findPageable(@Param("name") String name, @Param("url") String url,
			@Param("searchSKU") String searchSKU, Pageable pageable);

	@Query("SELECT count(*) FROM Product item where (:name is NULL OR item.name LIKE :name) AND (:url is NULL OR item.url LIKE :url) AND (:searchSKU is NULL OR item.sku LIKE :searchSKU) GROUP BY item.id ORDER BY item.id")
	long countPageable(@Param("name") String name, @Param("url") String url, @Param("searchSKU") String searchSKU);

	@Query("SELECT p FROM Product p,ProductOption o, Category c " + "where p.id=o.product AND p.category=c.id "
			+ "AND (o.id in :idListString)" + " and p.status = true" + " and c.status = true"
			+ " and (c.id in :categoryList)" + " ORDER by p.id")
	Page<Product> findFilteredProductsInCategory(@Param("idListString") List<Long> idListString,
			@Param("categoryList") List<Long> categoryList, Pageable pageable);

	@Query("SELECT count(distinct p.id) FROM Product p,ProductOption o, Category c "
			+ "where p.id=o.product AND p.category=c.id " + "AND (o.id in :idListString) " + "and p.status = true "
			+ "and c.status = true and (c.id in :categoryList)" + " ORDER by p.id")
	long countFilteredProductsInCategory(@Param("idListString") List<Long> idListString,
			@Param("categoryList") List<Long> categoryList);

	@Query("SELECT p FROM Product p, Category c " + "where p.category=c.id " + "and p.status = true "
			+ "and c.status = true and c.id in :categoryList" + " ORDER by p.id")
	Page<Product> findFilteredProductsInCategory(@Param("categoryList") List<Long> categoryList, Pageable pageable);

	@Query("SELECT count(distinct p.id) FROM Product p, Category c " + "where p.category=c.id " + "and p.status = true "
			+ "and c.status = true and c.id in :categoryList" + " ORDER by p.id")
	long countFilteredProductsInCategory(@Param("categoryList") List<Long> categoryList);

	@Query("SELECT p FROM Product p, Category c " + "where p.category=c.id " + "and p.status = true "
			+ "and c.status = true " + " ORDER by p.id")
	Page<Product> findFilteredProductsInCategory(Pageable pageable);

	@Query("SELECT count(*) FROM Product p, Category c " + "where p.category=c.id " + "and p.status = true "
			+ "and c.status = true " + " ORDER by p.id")
	long countFilteredProductsInCategory();

}
