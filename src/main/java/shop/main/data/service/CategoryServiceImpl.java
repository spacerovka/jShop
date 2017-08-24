package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
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
	public List<Category> listAll() {

		return dao.findAll();
	}

	@Override
	public Category findCategoryById(long id) {

		return dao.findOne(id);
	}

	@Override
	public List<Category> findAllParentCategories() {

		return dao.findAllCategoryByParentCategoryAndStatus(null, true);
	}

	@Override
	public boolean checkUniqueURL(Category category) {
		List<Category> result = dao.findCategoryByUrl(category.getCategoryURL());
		if (result == null) {
			return true;
		} else if (result.get(0).getId().equals(category.getId())) {
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void deleteCategoryById(long id) {

		Session session = (Session) entityManager.getDelegate();
		String hql = "update Category set parentCategory = NULL where parentCategory='" + id + "'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
		dao.delete(id);

	}

	@Transactional
	@Override
	public Category findCategoryByUrl(String url) {

		List<Category> result = dao.findCategoryByUrl(url);
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
		// return dao.findByNameAndURL(name, url, pageable).getContent();

		Session session = (Session) entityManager.getDelegate();

		String hql = "SELECT item FROM Category item where (:name is NULL OR item.categoryName LIKE :name) AND (:url is NULL OR item.categoryURL LIKE :url) ORDER BY item.id";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("url", url);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return query.list();
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
