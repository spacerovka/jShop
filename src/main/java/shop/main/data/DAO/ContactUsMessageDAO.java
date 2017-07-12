package shop.main.data.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.ContactUsMessage;

public interface ContactUsMessageDAO extends JpaRepository<ContactUsMessage, Long> {

}
