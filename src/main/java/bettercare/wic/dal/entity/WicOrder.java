package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name="wic_order")
//@NamedQuery(name="WicOrder.findAll", query="SELECT o FROM WicOrder o")
public class WicOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ORDER_ID_GENERATOR", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_ID_GENERATOR")
	private long id;

	@Column(name="is_emergency")
	private boolean isEmergency;

	@Column(name="ordered_time")
	private long orderedTime;

	@Column(name="product_and_quantity")
	private String products;

	private String status;

	@OneToMany(mappedBy = "wicOrder", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MissingProduct> missingProducts;

	@OneToOne
	private Voucher voucher;

	@OneToOne(mappedBy = "wicOrder", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Delivery delivery;


	public WicOrder(boolean isEmergency, long orderedTime, String products, String status, Voucher voucher) {
		this.isEmergency = isEmergency;
		this.orderedTime = orderedTime;
		this.products = products;
		this.status = status;
		this.isEmergency = isEmergency;
		this.voucher = voucher;
	}

	public WicOrder() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getIsEmergency() {
		return this.isEmergency;
	}

	public void setIsEmergency(boolean isEmergency) {
		this.isEmergency = isEmergency;
	}

	public long getOrderedTime() {
		return this.orderedTime;
	}

	public void setOrderedTime(long orderedTime) {
		this.orderedTime = orderedTime;
	}

	public String getProducts() {
		return this.products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MissingProduct> getMissingProducts() {
		return this.missingProducts;
	}

	public void setMissingProducts(List<MissingProduct> missingProducts) {
		this.missingProducts = missingProducts;
	}

	public MissingProduct addMissingProduct(MissingProduct missingProduct) {
		getMissingProducts().add(missingProduct);
		missingProduct.setWicOrder(this);
		return missingProduct;
	}

	public MissingProduct removeMissingProduct(MissingProduct missingProduct) {
		getMissingProducts().remove(missingProduct);
		missingProduct.setWicOrder(null);
		return missingProduct;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucherId(Voucher voucher) {
		this.voucher = voucher;
	}

	@Override
	public String toString() {
		return String.format("id:%s missing:%s isEmergency:%s orderTime:%s orderContents:%s status:%s",
				this.getId(), this.getMissingProducts(), this.getIsEmergency(),
				this.getOrderedTime(), this.getProducts(), this.getStatus());
	}

	@Override
	public int hashCode() {
		return (int) (Long.valueOf(this.getId()).hashCode() +
						(this.getMissingProducts().isEmpty() ? 0 : 1) +
						(this.getIsEmergency() ? 1 : 0) +
						this.getOrderedTime() +
						getStringHash(getProducts()) +
						getStringHash(this.getStatus()));
	}

	private int getStringHash(String val) {
		return val == null ? 0 : val.hashCode();
	}

	@Override
	public boolean equals(Object thatObj) {
		if(thatObj == null) {
			return false;
		}
		if(!(thatObj instanceof WicOrder)) {
			return false;
		}
		WicOrder that = (WicOrder) thatObj;
		return !isDifferent(that.toString(), this.toString());
	}

	private boolean isDifferent(String that, String me) {
		if(that == null) {
			return me == null;
		}
		else {
			return that.equals(me);
		}
	}

}