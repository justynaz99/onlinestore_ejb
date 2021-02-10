package jsfproject.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
	
	public static final String ID = "$31$"; // Each token produced by this class uses this identifier as a prefix.
	public static final int DEFAULT_COST = 16; // The minimum recommended cost, used by default
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final int SIZE = 128;
	private static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");
	private final SecureRandom random;
	private final int cost;

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public UserDAO() {
		this(DEFAULT_COST);
	}

	public UserDAO(int cost) {
		iterations(cost); /* Validate cost */
		this.cost = cost;
		this.random = new SecureRandom();
	}

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
	

	private static int iterations(int cost) {
		if ((cost < 0) || (cost > 30))
			throw new IllegalArgumentException("cost: " + cost);
		return 1 << cost;
	}
	
	public boolean authenticate(char[] password, String token) {
		Matcher m = layout.matcher(token);
		if (!m.matches())
			throw new IllegalArgumentException("Invalid token format");
		int iterations = iterations(Integer.parseInt(m.group(1)));
		byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
		byte[] salt = Arrays.copyOfRange(hash, 0, SIZE / 8);
		byte[] check = pbkdf2(password, salt, iterations);
		int zero = 0;
		for (int idx = 0; idx < check.length; ++idx)
			zero |= hash[salt.length + idx] ^ check[idx];
		return zero == 0;
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
		KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
		try {
			SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
			return f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
		} catch (InvalidKeySpecException ex) {
			throw new IllegalStateException("Invalid SecretKeyFactory", ex);
		}
	}

	@Deprecated
	public boolean authenticate(String password, String token) {
		return authenticate(password.toCharArray(), token);
	}

	public User checkUser(String enteredEmail, String enteredPass) {
		String databasePass;
		List<User> users = em.createQuery("select u from User u where u.email = :email")
				.setParameter("email", enteredEmail).getResultList();

		if (users.size() > 0)
			databasePass = users.get(0).getPassword();
		else return null;
		
		if(authenticate(enteredPass, databasePass)) 
			return users.get(0);
		else return null;
		
		
		
	}

	public List<User> listAllUsers() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	public void editUser(User user) {
		Query query = em.createQuery("update User u set u.firstName = :firstName" + " where u.idUser = :idUser");
		query.setParameter("idUser", user.getIdUser());
		query.setParameter("firstName", user.getFirstName());
	}

}
