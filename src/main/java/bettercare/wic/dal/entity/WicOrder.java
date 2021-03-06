package bettercare.wic.dal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp()
	@Column(name="ordered_time")
	private Timestamp orderedTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	@Column(name="update_time")
	private Timestamp updateTime;

	@NotBlank
	@Column(name="product_and_quantity")
	private String products;

	private String status;

	@OneToOne(fetch = FetchType.EAGER) // EAGER to pass unit test
	@JsonBackReference
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;

	@JsonManagedReference
	@OneToOne(mappedBy = "wicOrder", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Delivery delivery;

	@JsonManagedReference
	@OneToMany(mappedBy = "wicOrder", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MissingProduct> missingProducts = new ArrayList<>();

	public WicOrder(String products, String status, Voucher voucher) {
		this.products = products;
		this.status = status;
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

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

    public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public List<MissingProduct> getMissingProducts() {
		return missingProducts;
	}

	public void setMissingProducts(List<MissingProduct> missingProducts) {
		this.getMissingProducts().clear();
		if(missingProducts != null && !missingProducts.isEmpty()) {
			this.getMissingProducts().addAll(missingProducts);
		}
	}

	public void addMissingProducts(MissingProduct missingProduct) {
		if(missingProduct != null) {
			missingProduct.setWicOrder(this);
			this.missingProducts.add(missingProduct);
		}
	}

	@PreRemove
	public void preRemove() {
		this.setVoucher(null);
		if(this.getDelivery() != null) {
			this.setDelivery(null);
		}
		if(this.getMissingProducts() != null && !this.getMissingProducts().isEmpty()) {
			this.getMissingProducts().clear();
		}
	}

	public boolean hasMissingProducts() {
		return !this.getMissingProducts().isEmpty();
	}

	@Override
	public String toString() {
		return String.format("id:%s hasMissingProduct:%s orderTime:%s orderContents:%s status:%s customerId:%s voucherId:%s",
							 this.getId(), this.hasMissingProducts(), this.getOrderedTime(), this.getProducts(), this.getStatus(),
							 this.getVoucher().getCustomer().toString(), this.getVoucher().toString());
	}

	@Override
	public int hashCode() {
		return (int) (Long.valueOf(this.getId()).hashCode() +
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