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

	/**
	 * Note. Unidirectional
	 * mappedBy and @JoinColumn(name = "") are exclusive to each other.
	 * If mappedBy is used, I had to annotate from many side as well.
	 * Using @JoinColumn(name = "") I do not need to do anything on many side in 1:many relationship.
	 * It tell JPA that this a foreign key name in many side.
	 * This also prevents cyclic data structure.
	 */
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

}