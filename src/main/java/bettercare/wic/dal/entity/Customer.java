package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="customer")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CUSTOMER_ID_GENERATOR", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CUSTOMER_ID_GENERATOR")
	private long id;

	private String address;

	private String name;

	private String phone;

	@Column(name="wic_id")
	private String wicId;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private List<Delivery> deliveries;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private List<Voucher> vouchers;

	public Customer() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWicId() {
		return this.wicId;
	}

	public void setWicId(String wicId) {
		this.wicId = wicId;
	}

	public List<Delivery> getDeliveries() {
		return this.deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public Delivery addDelivery(Delivery delivery) {
		getDeliveries().add(delivery);
		delivery.setCustomer(this);

		return delivery;
	}

	public Delivery removeDelivery(Delivery delivery) {
		getDeliveries().remove(delivery);
		delivery.setCustomer(null);

		return delivery;
	}

	public List<Voucher> getVouchers() {
		return this.vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public Voucher addVoucher(Voucher voucher) {
		getVouchers().add(voucher);
		voucher.setCustomer(this);

		return voucher;
	}

	public Voucher removeVoucher(Voucher voucher) {
		getVouchers().remove(voucher);
		voucher.setCustomer(null);

		return voucher;
	}

	@Override
	public String toString() {
		return String.format("customerId:%s address:%s name:%s phone:%s wicNumber:%s ",
				this.getId(), this.getAddress(), this.getName(), this.getPhone(), this.getWicId());
	}

	/**
	 * Compute int for fields that defines equals()
	 */
	@Override
	public int hashCode() {
		return Long.valueOf(this.getId()).hashCode()
				+ Long.valueOf(this.getAddress()).hashCode()
				+ getStringHash(this.getName())
				+ getStringHash(this.getPhone())
				+ getStringHash(this.getWicId())
				+ getCollectionHash(this.getVouchers())
				+ getStringHash(this.getWicId());
	}

	private int getCollectionHash(List<Voucher> vouchers) {
		if(vouchers == null) {
			return 0;
		}
		int hash = 0;
		for(Voucher voucher : vouchers) {
			if(voucher.getExpirationDate() != null) {
				hash += voucher.getExpirationDate().hashCode();
			}
			hash += voucher.getId();
			if(voucher.getStartDate() != null) {
				hash += voucher.getStartDate().hashCode();
			}
			if(voucher.getVoucherId() != null) {
				hash += getStringHash(voucher.getVoucherId());
			}
		}
		return hash;
	}

	private int getStringHash(String val) {
		return val == null ? 0 : val.hashCode();
	}

	@Override
	public boolean equals(Object thatObj) {
		if(thatObj == null) {
			return false;
		}
		if(!(thatObj instanceof Product)) {
			return false;
		}
		Customer that = (Customer)thatObj;
		if(isDifferent(that.getName(), this.getName())) {
			return false;
		}
		if(isDifferent(that.getAddress(), this.getAddress())) {
			return false;
		}
		if(isDifferent(that.getPhone(), this.getPhone())) {
			return false;
		}
		if(isDifferent(that.getWicId(), this.getWicId())) {
			return false;
		}
		if(isCollectionDifferent(that.getVouchers(), this.getVouchers())) {
			return false;
		}
		return true;
	}

	private boolean isCollectionDifferent(List<Voucher> that, List<Voucher> me) {
		if(that == null) {
			return me == null;
		}
		else if (me == null){
			return false;
		}
		return that.size() == me.size();
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