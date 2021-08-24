package com.luxoft.springdb.lab2.dao.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luxoft.springdb.lab2.dao.CountryDao;
import com.luxoft.springdb.lab2.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Repository
public class CountryJpaDaoImpl implements CountryDao {

	@Autowired
	EntityManagerFactory emf;

	@Override
	@Transactional
	public void save(Country country) {
		EntityManager em = emf.createEntityManager();
		if (em != null) {
			em.persist(country);
			em.close();
		}
	}

	@Override
	@Transactional
	public List<Country> getAllCountries() {
		List<Country> countryList = null;
		EntityManager em = emf.createEntityManager();
		if (em != null) {
			countryList = em.createQuery("select e from Country e", Country.class).getResultList();
			em.close();
		}
		return countryList;
	}

	@Override
	public Country getCountryByName(String name) {
		// TODO: Implement it
		return null;
	}

}
