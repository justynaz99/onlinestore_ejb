package jsfproject.dao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import jsfproject.entities.OrderStatus;
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

	public OrderStatus find(Object id) {
		return em.find(OrderStatus.class, id);
	}

	public List<Order> listAllOrders() {
		return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
	}
	
	

}
