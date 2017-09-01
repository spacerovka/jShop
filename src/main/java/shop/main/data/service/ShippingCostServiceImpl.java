package shop.main.data.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.CountryDAO;
import shop.main.data.DAO.ParcelCostDAO;
import shop.main.data.DAO.ParcelSizeDAO;
import shop.main.data.entity.Country;
import shop.main.data.entity.ParcelCost;
import shop.main.data.entity.ParcelSize;

@Service("shippingCostService")
public class ShippingCostServiceImpl implements ShippingCostService {
	@Autowired
	ParcelSizeDAO sizeDAO;

	@Autowired
	CountryDAO countryDAO;

	@Autowired
	ParcelCostDAO costDAO;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void saveSize(ParcelSize size) {
		sizeDAO.save(size);

	}

	@Override
	public void saveCountry(Country country) {
		for (ParcelCost cost : country.getCostList()) {
			costDAO.save(cost);
		}
		countryDAO.save(country);

	}

	@Transactional
	@Override
	public void deleteSizeById(Long id) {
		Session session = (Session) entityManager.getDelegate();
		String hql_costs = "delete from ParcelCost coat where cost.size.id=:id";
		Query query = session.createQuery(hql_costs);
		query.setParameter("id", id);
		query.executeUpdate();

		String hql = "delete from ParcelSize item where item.id=:id";
		Query delete = session.createQuery(hql);
		delete.setParameter("id", id);
		delete.executeUpdate();

	}

	@Transactional
	@Override
	public void deleteCountryById(Long id) {
		Session session = (Session) entityManager.getDelegate();
		String hql_costs = "delete from ParcelCost coat where cost.country.id=:id";
		Query query = session.createQuery(hql_costs);
		query.setParameter("id", id);
		query.executeUpdate();

		String hql = "delete from Country item where item.id=:id";
		Query delete = session.createQuery(hql);
		delete.setParameter("id", id);
		delete.executeUpdate();

	}

	@Override
	public List<Country> listAllCountries() {

		return countryDAO.findAll();
	}

	@Override
	public List<ParcelSize> listAllSizez() {

		return sizeDAO.findAll();
	}

	@Transactional
	@Override
	public BigDecimal getShippingCost(String countryName, String sizeName) {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from ParcelCost item where item.country.name =:countryName  and item.size.name=:sizeName";
		Query query = session.createQuery(hql);
		query.setParameter("countryName", countryName);
		query.setParameter("sizeName", sizeName);
		ArrayList<ParcelCost> list = (ArrayList<ParcelCost>) query.list();
		if (!list.isEmpty()) {
			return list.get(0).getCost();
		} else {
			return new BigDecimal(0);
		}
	}

	@Override
	public Country getCountryByName(String name) {

		return countryDAO.findOneByName(name);
	}

	@Override
	public Country findCountryById(Long id) {

		return countryDAO.findOne(id);
	}

	@Override
	public ParcelSize findSizeById(Long id) {

		return sizeDAO.findOne(id);
	}

	@Transactional
	@Override
	public ParcelCost findOneByCountryAndSize(Country country, ParcelSize size) {
		Session session = (Session) entityManager.getDelegate();
		String hql = "from ParcelCost cost where cost.country.id =" + country.getId() + " and cost.size="
				+ size.getId();

		Query query = session.createQuery(hql);
		System.out.println("*");
		System.out.println("*");
		System.out.println("query is " + query.getQueryString());
		System.out.println("*");
		System.out.println("*");

		List<ParcelCost> list = (List<ParcelCost>) query.list();
		if (list != null) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public List<Country> listAllCountries(Pageable pageable) {
		Session session = (Session) entityManager.getDelegate();
		String hql = "from Country";
		Query query = session.createQuery(hql);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return (List<Country>) query.list();
	}

	@Transactional
	@Override
	public List<ParcelSize> listAllSizez(Pageable pageable) {
		Session session = (Session) entityManager.getDelegate();
		String hql = "from ParcelSize";
		Query query = session.createQuery(hql);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return (List<ParcelSize>) query.list();
	}

	@Override
	public long countCountries() {
		return countryDAO.count();
	}

	@Override
	public long countSizes() {
		return sizeDAO.count();
	}

}
