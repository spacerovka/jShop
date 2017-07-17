package shop.main.data.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import shop.main.data.entity.ContactUsMessage;

public interface ContactUsMessageService {
	void saveContactUsMessage(ContactUsMessage item);

	void deleteContactUsMessageById(long id);

	List<ContactUsMessage> listAll();

	ContactUsMessage findContactUsMessageById(long id);

	List<ContactUsMessage> findByStatus(String status, Pageable pageable);

	long countByStatus(String status);
}
