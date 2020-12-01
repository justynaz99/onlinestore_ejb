package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the order_position database table.
 * 
 */
@Entity
@Table(name="order_position")
@NamedQuery(name="OrderPosition.findAll", query="SELECT o FROM OrderPosition o")
public class OrderPosition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_position")
	private int idPosition;

	@Column(name="price_product")
	private BigDecimal priceProduct;

	private int quantity;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order order;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="id_product")
	private Product product;

	public OrderPosition() {
	}

	public int getIdPosition() {
		return this.idPosition;
	}

	public void setIdPosition(int idPosition) {
		this.idPosition = idPosition;
	}

	public BigDecimal getPriceProduct() {
		return this.priceProduct;
	}

	public void setPriceProduct(BigDecimal priceProduct) {
		this.priceProduct = priceProduct;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}