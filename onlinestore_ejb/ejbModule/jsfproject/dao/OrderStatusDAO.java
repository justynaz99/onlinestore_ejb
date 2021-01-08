package jsfproject.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Order;
import jsfproject.entities.OrderPosition;
import jsfproject.entities.OrderStatus;
import jsfproject.entities.User;

@Stateless
public class OrderStatusDAO {
	private final static String UNIT_NAME = "jsfproject-simplePU";
	
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	

	public void create(OrderStatus orderStatus) {
		em.persist(orderStatus);
	}

	public OrderStatus merge(OrderStatus orderStatus) {
		return em.merge(orderStatus);
	}

	public void remove(OrderStatus orderStatus) {
		em.remove(em.merge(orderStatus));
	}
	public OrderStatus find(OrderStatus id) {
		return em.find(OrderStatus.class, id);
	}
	
	

}
