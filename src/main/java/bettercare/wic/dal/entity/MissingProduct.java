package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


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

	@Column(name = "wicOrder_id")
	private long wicOrderId;

	@Column(name = "product_id")
	private long productId;

	public MissingProduct() {
	}

	public MissingProduct(long wicOrderId, long productId, int quantity) {
		this.wicOrderId = wicOrderId;
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

	public long getWicOrderId() {
		return this.wicOrderId;
	}

	public void setWicOrder(long wicOrderId) {
		this.wicOrderId = wicOrderId;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
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