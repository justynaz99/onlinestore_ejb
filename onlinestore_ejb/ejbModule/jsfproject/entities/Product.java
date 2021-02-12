package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product")
	private int idProduct;

	@Column(name = "available_quantity")
	private int availableQuantity;

	private String name;

	private BigDecimal price;

	@OneToMany(mappedBy = "product")
	private List<OrderPosition> orderPositions;

	public Product() {
	}

	public Integer getIdProduct() {
		return this.idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public Integer getAvailableQuantity() {
		return this.availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<OrderPosition> getOrderPositions() {
		return this.orderPositions;
	}

	public void setOrderPositions(List<OrderPosition> orderPositions) {
		this.orderPositions = orderPositions;
	}

	public OrderPosition addOrderPosition(OrderPosition orderPosition) {
		getOrderPositions().add(orderPosition);
		orderPosition.setProduct(this);

		return orderPosition;
	}

	public OrderPosition removeOrderPosition(OrderPosition orderPosition) {
		getOrderPositions().remove(orderPosition);
		orderPosition.setProduct(null);

		return orderPosition;
	}

}