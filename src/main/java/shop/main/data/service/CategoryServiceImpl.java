package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
		Category result = dao.findOneByCategoryURL(category.getCategoryURL());
		if (result == null) {
			return true;
		} else if (result.getId().equals(category.getId())) {
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
		Session session = (Session) entityManager.getDelegate();

		String hql = "from Category c where c.status = true and c.categoryURL = '" + url + "'";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		List<Category> found = (List<Category>) query.list();
		if (found != null && !found.isEmpty()) {
			return found.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public List<Category> findByNameAndURL(String name, String url) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from Category c where c.categoryName like '%" + name + "%'" + " and c.categoryURL like '%" + url
				+ "%'";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}

}
