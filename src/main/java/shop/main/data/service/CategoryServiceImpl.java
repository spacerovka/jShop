package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.CategoryDAO;
import shop.main.data.objects.Category;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@PersistenceContext
    protected EntityManager entityManager;
	
	@Override
	public void saveCategory(Category category) {
		
			categoryDAO.save(category);		
	}

	@Override
	public void deleteCategory(Category category) {

		categoryDAO.delete(category);
	}

	@Override
	public List<Category> listAll() {

		return categoryDAO.findAll();
	}

	@Override
	public Category findCategoryById(long id) {
		
		return categoryDAO.findOne(id);
	}

	@Override
	public List<Category> findAllParentCategories() {
		
		return categoryDAO.findAllCategoryByParentCategory(null);
	}

	@Override
	public boolean checkUniqueURL(Category category) {
		Category result = categoryDAO.findOneByCategoryURL(category.getCategoryURL());
		if(result == null){
			return true;
		}else if(result.getId().equals(category.getId())){
			return true;
		}
		return false;
	}

	@Override
	public void deleteCategoryById(long id) {
		
		List<Category> list = categoryDAO.findAllCategoryByParentCategory(categoryDAO.findOne(id));
		for (Category child : list) {
		    child.setParentCategory(null);
		    categoryDAO.save(child);
		}		
		categoryDAO.delete(id);
		
	}

	@Transactional
	@Override
	public Category fingCategoryByUrl(String url) {
		Session session =(Session)entityManager.getDelegate();
		
		String hql = "from Category c where c.status = true and c.categoryURL = '"+url+"'";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		List<Category> found = (List<Category>)query.list();
		if(found!=null){
			return found.get(0);
		}else{
			return null;
		}
	}

	@Transactional
	@Override
	public List<Category> findByNameAndURL(String name, String url) {
		Session session =(Session)entityManager.getDelegate();
		
		String hql = "from Category c where c.categoryName like '%"+name+"%'"
				+" and c.categoryURL like '%"+url+"%'";
		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is "+query.getQueryString());
		System.out.println("*");
		System.out.println("*");
		return query.list();
	}

}
