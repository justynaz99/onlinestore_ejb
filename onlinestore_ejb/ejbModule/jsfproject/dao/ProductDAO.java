package jsfproject.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import jsfproject.entities.Product;

@Stateless
public class ProductDAO {
	private final static String UNIT_NAME = "jsfproject-simplePU";
	
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public void create(Product product) {
		em.persist(product);
	}

	public Product merge(Product product) {
		return em.merge(product);
	}

	public void remove(Product product) {
		em.remove(em.merge(product));
	}

	public Product find(Object id) {
		return em.find(Product.class, id);
	}
	
	public List<Product> getFullList() {
		List<Product> list = null;

		Query query = em.createQuery("select p from Product p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	

}
