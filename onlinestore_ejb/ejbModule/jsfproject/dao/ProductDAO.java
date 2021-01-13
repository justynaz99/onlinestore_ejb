package jsfproject.dao;

import java.util.List;
import java.util.Map;

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
	private int quantity = 2;
	private int offset = 0;
	private int page = 1;
	private int lastPage;

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public int getQuantity() {
		return quantity;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLastPage() {
		return lastPage;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
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
		lastPage = (int) Math.round(countProducts() / quantity) + 1;

		if (page <= 0)
			page = 1;

		return (List<Product>) em.createQuery("SELECT p FROM Product p", Product.class).setMaxResults(getQuantity())
				.setFirstResult(getOffset()).getResultList();
	}

	public List<Product> getList(Map<String, Object> searchParams) {
		List<Product> list = null;
		String where = "";
		String name = (String) searchParams.get("name");
		
		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.name like :name ";
		}
		
		Query query = em.createQuery("SELECT p FROM Product p " + where);

		if (name != null) {
			query.setParameter("name", name + "%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
