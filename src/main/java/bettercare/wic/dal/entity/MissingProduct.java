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
	@SequenceGenerator(name="MISSING_PRODUCT_ID_GENERATOR", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MISSING_PRODUCT_ID_GENERATOR")
	private long id;

	private int quantity;

	@ManyToOne
	private WicOrder wicOrder;

	@Column(name = "product_id")
	private long productId;

	public MissingProduct() {
	}

	public MissingProduct(WicOrder wicOrder, long productId, int quantity) {
		this.wicOrder = wicOrder;
		this.productId = productId;
		this.quantity = quantity;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public WicOrder getWicOrder() {
		return this.wicOrder;
	}

	public void setWicOrder(WicOrder wicOrder) {
		this.wicOrder = wicOrder;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}