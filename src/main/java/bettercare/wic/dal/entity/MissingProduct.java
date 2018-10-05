package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the missing_product database table.
 * 
 */
@Entity
@Table(name="missing_product")
@NamedQuery(name="MissingProduct.findAll", query="SELECT m FROM MissingProduct m")
public class MissingProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MISSING_PRODUCT_ID_GENERATOR", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MISSING_PRODUCT_ID_GENERATOR")
	private long id;

	private byte quantity;

	//bi-directional many-to-one association to Order
	@ManyToOne
	private Order order;

	//bi-directional many-to-one association to Product
	@ManyToOne
	private Product product;

	public MissingProduct() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte getQuantity() {
		return this.quantity;
	}

	public void setQuantity(byte quantity) {
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