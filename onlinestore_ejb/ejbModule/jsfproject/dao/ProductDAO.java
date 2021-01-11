package jsfproject.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import jsfproject.entities.Product;
import jsfproject.entities.User;

@Stateless
public class ProductDAO {
	private final static String UNIT_NAME = "jsfproject-simplePU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	private Integer quantity = 2;
	private Integer page;
	private Integer lastPage;

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	public Integer getLastPage() {
		return lastPage;
	}

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

	public int countProducts() {
		List<Product> list = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
		return list.size();
	}

	public List<Product> listAllProducts() {
		lastPage = (int) Math.floor(countProducts() / quantity);

		if (page == null || page > lastPage || page < 1)
			page = 1;

		return (List<Product>) em.createQuery("SELECT p FROM Product p", Product.class).setMaxResults(getQuantity())
				.setFirstResult(getQuantity()).getResultList();
	}

}
