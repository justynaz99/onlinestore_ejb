package jsfproject.dao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
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
import jsfproject.entities.OrderStatus;
import jsfproject.entities.User;

@Stateless
public class OrderDAO {
	private final static String UNIT_NAME = "jsfproject-simplePU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	@Inject
	FacesContext context;
	
	@EJB
	OrderStatusDAO orderStatusDAO;

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

	public List<Order> listAllOrders() {
		return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
	}
	
	public boolean cartExists(Object user) {
		OrderStatus orderStatus = new OrderStatus();
		//OrderStatusDAO orderStatusDAO = new OrderStatusDAO();
		orderStatus = orderStatusDAO.getCart();
		List<Order> orders = em.createQuery("select o from Order o where o.user = :user and o.orderStatus = :orderStatus")
				.setParameter("user", user)
				.setParameter("orderStatus", orderStatus)
				.getResultList();
		if (orders.size()>0) return true;
		return false;
	}
	

}
