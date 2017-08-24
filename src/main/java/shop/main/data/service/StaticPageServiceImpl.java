package shop.main.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.StaticPageDAO;
import shop.main.data.entity.StaticPage;

@Service("staticPageService")
public class StaticPageServiceImpl implements StaticPageService {

	@Autowired
	private StaticPageDAO dao;

	@Override
	public void save(StaticPage staticPage) {
		dao.save(staticPage);

	}

	@Override
	public void delete(StaticPage staticPage) {
		dao.delete(staticPage);

	}

	@Override
	public List<StaticPage> listAll() {

		return dao.findAll();
	}

	@Override
	public List<StaticPage> findAllByURL(String url) {
		return dao.findAllByUrlContaining(url);
	}

	@Override
	public StaticPage findById(long id) {

		return dao.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		dao.delete(id);

	}

	@Override
	public StaticPage findOneByURL(String url) {
		return dao.findOneByUrl(url);
	}

	@Override
	public List<StaticPage> findAllByName(String name) {
		return dao.findAllByNameContaining(name);
	}

	@Override
	public boolean checkUniqueURL(StaticPage page) {
		StaticPage result = dao.findOneByUrl(page.getUrl());
		if (result == null) {
			return true;
		} else if (result.getId().equals(page.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public List<StaticPage> findByNameAndStatus(String name, String status, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}
		return dao.findByNameAndStatus(name, bStatus, pageable).getContent();
	}

	@Transactional
	@Override
	public long countByNameAndStatus(String name, String status) {
		if (name != null)
			name = "%" + name + "%";
		Boolean bStatus = null;
		if (status != null && !status.isEmpty()) {
			bStatus = Boolean.valueOf(status);
		}
		return dao.countByNameAndStatus(name, bStatus);
	}

}
