package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_order")
	private int idOrder;

	@Lob
	private String comment;

	@Temporal(TemporalType.DATE)
	private Date date;


	private BigDecimal value;

	//bi-directional many-to-one association to OrderPosition
	@OneToMany(mappedBy="order")
	private List<OrderPosition> orderPositions;

	//bi-directional many-to-one association to OrderStatus
	@ManyToOne
	@JoinColumn(name="id_status")
	private OrderStatus orderStatus;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;

	public Order() {
	}
	
	

	public int getIdOrder() {
		return this.idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public List<OrderPosition> getOrderPositions() {
		return this.orderPositions;
	}

	public void setOrderPositions(List<OrderPosition> orderPositions) {
		this.orderPositions = orderPositions;
	}

	public OrderPosition addOrderPosition(OrderPosition orderPosition) {
		getOrderPositions().add(orderPosition);
		orderPosition.setOrder(this);

		return orderPosition;
	}

	public OrderPosition removeOrderPosition(OrderPosition orderPosition) {
		getOrderPositions().remove(orderPosition);
		orderPosition.setOrder(null);

		return orderPosition;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}