package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import shop.main.data.DAO.ContactUsMessageDAO;
import shop.main.data.entity.ContactUsMessage;

public class ContactUsMessageServiceImpl implements ContactUsMessageService {

	@Autowired
	private ContactUsMessageDAO dao;

	@Override
	public void saveContactUsMessage(ContactUsMessage item) {
		dao.save(item);

	}

	@Override
	public void deleteContactUsMessageById(long id) {
		dao.delete(id);

	}

	@Override
	public List<ContactUsMessage> listAll() {

		return dao.findAll();
	}

	@Override
	public ContactUsMessage findContactUsMessageById(long id) {

		return dao.findOne(id);
	}

}
