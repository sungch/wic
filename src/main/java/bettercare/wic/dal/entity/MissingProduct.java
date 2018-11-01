package bettercare.wic.dal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private int quantity;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "wicOrder_id")
	private WicOrder wicOrder;

	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public MissingProduct() {
	}

	public MissingProduct(WicOrder wicOrder, Product product, int quantity) {
		this.wicOrder = wicOrder;
		this.product = product;
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

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return String.format("id:%s missingProductId:%s quantity:%s", this.getId() + this.getQuantity());
	}

	@Override
	public boolean equals(Object that_) {
		if (that_ == null) {
			return false;
		}
		if (!(that_ instanceof MissingProduct)) {
			return false;
		}
		MissingProduct that = (MissingProduct) that_;
		return isSame(that.toString(), this.toString());
	}

	@Override
	public int hashCode() {
		return super.hashCode() + getStringHash(this.toString());
	}

	private int getStringHash(String val) {
		return val == null ? 0 : val.hashCode();
	}

	private boolean isSame(String that, String me) {
		if (that == null) {
			return me == null;
		}
		if (me == null) {
			return false;
		}
		return that.equals(me);
	}


}