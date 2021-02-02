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
	private int quantity = 6;
	private int offset = 0;
	private int page = 1;
	private int lastPage;

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public int getQuantity() {
		return quantity;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
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

	

	public void create(Product product) {
		em.persist(product);
	}

	public Product merge(Product product) {
		return em.merge(product);
	}

	public void remove(Product product) {
		em.remove(product);
	}

	public Product find(Object id) {
		return em.find(Product.class, id);
	}

	public int countProducts() {
		List<Product> list = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
		return list.size();
	}


	public List<Product> getList(Map<String, Object> searchParams) {
		lastPage = (int) Math.round(countProducts() / quantity) + 1;
		List<Product> list = null;
		String where = "";
		String orderBy = "";
		String name = (String) searchParams.get("name");
		String selectedItem = (String) searchParams.get("selectedItem");
		if (page <= 0)
			page = 1;
		
		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.name like :name ";
		}
		
		if (selectedItem != null) {
			if(selectedItem == "1") {
				orderBy = "order by p.name asc ";
			}
			else {
				orderBy = "order by p.name desc ";
			}
			
		} else {
			orderBy = "order by p.name desc ";
		}
		
		Query query = em.createQuery("SELECT p FROM Product p " + where).setMaxResults(getQuantity())
				.setFirstResult(getOffset());
		

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
