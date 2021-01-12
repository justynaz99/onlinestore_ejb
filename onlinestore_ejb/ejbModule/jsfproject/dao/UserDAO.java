package jsfproject.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import jsfproject.entities.User;

@Stateless
public class UserDAO {
	private final static String UNIT_NAME = "jsfproject-simplePU";
	
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public void create(User user) {
		em.persist(user);
	}

	public User merge(User user) {
		return em.merge(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}

	public User find(Object id) {
		return em.find(User.class, id);
	}
	public List<User> findByFirstName(Object name) {
		return (List<User>) em.find(User.class, name);
	}
	

	
	public User checkUser(String username, String password) {
		List<User> users = em.createQuery("select u from User u where u.username = :username and u.password = :password")
				.setParameter("username", username)
				.setParameter("password", password)
				.getResultList();
		if (users.size()>0) return users.get(0);
		return null;
	}
	
	public List<User> listAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
	
	
	
	
	

}
