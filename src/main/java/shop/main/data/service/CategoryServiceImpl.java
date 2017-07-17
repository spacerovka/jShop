package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.CategoryDAO;
import shop.main.data.entity.Category;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO dao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void saveCategory(Category category) {

		dao.save(category);
	}

	@Override
	public void deleteCategory(Category category) {

		dao.delete(category);
	}

	@Override
	public List<Category> listAll() {

		return dao.findAll();
	}

	@Override
	public Category findCategoryById(long id) {

		return dao.findOne(id);
	}

	@Override
	public List<Category> findAllParentCategories() {

		return dao.findAllCategoryByParentCategory(null);
	}

	@Override
	public boolean checkUniqueURL(Category category) {
		List<Category> result = dao.fingCategoryByUrl(category.getCategoryURL());
		if (result == null) {
			return true;
		} else if (result.get(0).getId().equals(category.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteCategoryById(long id) {

		List<Category> list = dao.findAllCategoryByParentCategory(dao.findOne(id));
		for (Category child : list) {
			child.setParentCategory(null);
			dao.save(child);
		}
		dao.delete(id);

	}

	@Transactional
	@Override
	public Category fingCategoryByUrl(String url) {
		// Session session = (Session) entityManager.getDelegate();
		//
		// String hql = "from Category c where c.status = true and c.categoryURL
		// = '" + url + "'";
		// Query query = session.createQuery(hql);
		// System.out.println("*");
		// System.out.println("*");
		// System.out.println("query is " + query.getQueryString());
		// System.out.println("*");
		// System.out.println("*");
		// List<Category> found = (List<Category>) query.list();
		// if (found != null && !found.isEmpty()) {
		// return found.get(0);
		// } else {
		// return null;
		// }
		List<Category> result = dao.fingCategoryByUrl(url);
		if (result != null) {
			return result.get(0);
		}
		return null;
	}

	@Transactional
	@Override
	public List<Category> findByNameAndURL(String name, String url, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		if (url != null)
			url = "%" + url + "%";
		return dao.findByNameAndURL(name, url, pageable).getContent();
	}

	@Override
	public long countByNameAndURL(String name, String url) {
		if (name != null)
			name = "%" + name + "%";
		if (url != null)
			url = "%" + url + "%";
		return dao.countByNameAndURL(name, url);
	}

}
