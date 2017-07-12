package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import shop.main.data.DAO.ContactUsMessageDAO;
import shop.main.data.entity.ContactUsMessage;

public class ContactUsMessageServiceImpl implements ContactUsMessageService {

	@Autowired
	private ContactUsMessageDAO itemDAO;

	@Override
	public void saveContactUsMessage(ContactUsMessage item) {
		itemDAO.save(item);

	}

	@Override
	public void deleteContactUsMessageById(long id) {
		itemDAO.delete(id);

	}

	@Override
	public List<ContactUsMessage> listAll() {

		return itemDAO.findAll();
	}

	@Override
	public ContactUsMessage findContactUsMessageById(long id) {

		return itemDAO.findOne(id);
	}

}
