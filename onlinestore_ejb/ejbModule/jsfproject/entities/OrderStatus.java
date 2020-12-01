package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the order_status database table.
 * 
 */
@Entity
@Table(name="order_status")
@NamedQuery(name="OrderStatus.findAll", query="SELECT o FROM OrderStatus o")
public class OrderStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_status")
	private int idStatus;

	@Lob
	private String description;

	private String name;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="orderStatus")
	private List<Order> orders;

	public OrderStatus() {
	}

	public int getIdStatus() {
		return this.idStatus;
	}

	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setOrderStatus(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setOrderStatus(null);

		return order;
	}

}