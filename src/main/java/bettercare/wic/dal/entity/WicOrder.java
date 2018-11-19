package bettercare.wic.dal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name="wic_order")
@NamedQuery(name="WicOrder.findAll", query="SELECT o FROM WicOrder o")
public class WicOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="is_emergency")
	private boolean isEmergency;

	@CreationTimestamp
	@Column(name="ordered_time")
	private Timestamp orderedTime;

	@UpdateTimestamp
	@Column(name="status_update_time")
	private Timestamp statusUpdateTime;

	@NotBlank
	@Column(name="product_and_quantity")
	private String products;

	private String status;

	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;

	@JsonManagedReference
	@OneToOne(mappedBy = "wicOrder", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Delivery delivery;


	public WicOrder(boolean isEmergency, String products, String status, Voucher voucher) {
		this.isEmergency = isEmergency;
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

	public Timestamp getOrderedTime() {
		return this.orderedTime;
	}

	public void setOrderedTime(Timestamp orderedTime) {
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

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	@PreRemove
	public void disconnectWicOrder() {
		if(this.getVoucher() != null) {
			this.getVoucher().setWicOrder(null);
			this.setVoucher(null);
		}
		preRemoveDelivery(this.getDelivery());
		this.setDelivery(null);
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public void preRemoveDelivery(Delivery delivery) {
		if(delivery != null) {
			delivery.setWicOOrder(null);
		}
	}

	public boolean isEmergency() {
		return isEmergency;
	}

	public void setEmergency(boolean emergency) {
		isEmergency = emergency;
	}

	public Timestamp getStatusUpdateTime() {
		return statusUpdateTime;
	}

	public void setStatusUpdateTime(Timestamp statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}

	@Override
	public String toString() {
		return String.format("id:%s isEmergency:%s orderTime:%s orderContents:%s status:%s customerId:%s voucherId:%s",
							 this.getId(), this.isEmergency(),
							 this.getOrderedTime(), this.getProducts(), this.getStatus(),
							 this.getVoucher().getCustomer().toString(), this.getVoucher().toString());
	}

	@Override
	public int hashCode() {
		return (int) (Long.valueOf(this.getId()).hashCode() +
						(this.isEmergency() ? 1 : 0) +
						this.getOrderedTime().getTime() +
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
		return isSame(that.toString(), this.toString());
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