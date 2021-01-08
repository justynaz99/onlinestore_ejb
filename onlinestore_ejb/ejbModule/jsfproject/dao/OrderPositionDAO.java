package jsfproject.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Order;
import jsfproject.entities.OrderPosition;
import jsfproject.entities.User;

@Stateless
public class OrderPositionDAO {
	private final static String UNIT_NAME = "jsfproject-simplePU";
	
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	

	public void create(OrderPosition orderPosition) {
		em.persist(orderPosition);
	}

	public OrderPosition merge(OrderPosition orderPosition) {
		return em.merge(orderPosition);
	}

	public void remove(OrderPosition orderPosition) {
		em.remove(em.merge(orderPosition));
	}
	public OrderPosition find(OrderPosition id) {
		return em.find(OrderPosition.class, id);
	}
	
	public List<OrderPosition> getPositionsFromThisOrder(Order order) {
		List<OrderPosition> list = em.createQuery("select p from OrderPosition o where o.order = :order")
				.setParameter("order", order.getIdOrder())
				.getResultList();
		return list;
	}
	
	public List<OrderPosition> listAllPositions() {
        return em.createQuery("SELECT o FROM OrderPosition o", OrderPosition.class).getResultList();
    }
	
	

}
