package shop.main.data.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.main.data.entity.ContactUsMessage;

public interface ContactUsMessageDAO extends JpaRepository<ContactUsMessage, Long> {
	Page<ContactUsMessage> findAll(Pageable pageable);

	long count();

	@Query("SELECT item FROM ContactUsMessage item where (:status is NULL OR item.watched = :status) ORDER BY item.id")
	Page<ContactUsMessage> findByStatus(@Param("status") Boolean status, Pageable pageable);

	@Query("SELECT count(*) FROM ContactUsMessage item where (:status is NULL OR item.watched = :status) ORDER BY item.id")
	long countByStatus(@Param("status") Boolean status);
}
