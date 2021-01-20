package jsfproject.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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

	@Inject
	FacesContext context;

	public void create(OrderStatus orderStatus) {
		em.persist(orderStatus);
	}

	public OrderStatus merge(OrderStatus orderStatus) {
		return em.merge(orderStatus);
	}

	public void remove(OrderStatus orderStatus) {
		em.remove(em.merge(orderStatus));
	}

	public OrderStatus find(Object id) {
		return em.find(OrderStatus.class, id);
	}

	public OrderStatus getCartStatus() {
		return (OrderStatus) em
				.createQuery("select o from OrderStatus o where o.idStatus = :idStatus")
				.setParameter("idStatus", 1).getSingleResult();
	}

}
