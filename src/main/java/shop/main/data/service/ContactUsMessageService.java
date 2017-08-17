package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.ContactUsMessage;

public interface ContactUsMessageService {
	void save(ContactUsMessage item);

	void deleteById(long id);

	ContactUsMessage findById(long id);

	List<ContactUsMessage> findByStatus(String status, Pageable pageable);

	long countByStatus(String status);
}
