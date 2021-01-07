package jsfproject.dao;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpSession;

import jsfproject.entities.Order;
import jsfproject.entities.User;


@Stateless
public class OrderDAO {
	private final static String UNIT_NAME = "jsfproject-simplePU";
	
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	@Inject
	FacesContext context;
	
	public void create(Order order) {
		em.persist(order);
	}

	public Order merge(Order order) {
		return em.merge(order);
	}

	public void remove(Order order) {
		em.remove(em.merge(order));
	}

	public Order find(Object id) {
		return em.find(Order.class, id);
	}
	
//	public User checkUser(String username, String password) {
//		List<User> users = em.createQuery("select u from User u where u.username = :username and u.password = :password")
//				.setParameter("username", username)
//				.setParameter("password", password)
//				.getResultList();
//		if (users.size()>0) return users.get(0);
//		return null;
//	}
//	
//	public List<User> listAll() {
//        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
//    }
	
	
	public void createOrder() {
		long millis=System.currentTimeMillis();  
		Date date=new Date(millis);  
		
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		int userID = (int) session.getAttribute("userID");
		
		em.createNativeQuery("INSERT INTO Orders VALUES (?, ?, 0, 1)")
		.setParameter(1, date)
		.setParameter(2, userID);
		
	}
	
	
	
	
	

}
