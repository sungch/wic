package bettercare.wic.dal.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String address;

	private String name;

	private String phone;

	@Column(name="wic_number")
	private String wicNumber;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private List<Voucher> vouchers;


  public Customer(String wicNumber, String name, String phone, String address) {
		this.wicNumber = wicNumber;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

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

	public String getWicNumber() {
		return this.wicNumber;
	}

	public void setWicNumber(String wicNumber) {
		this.wicNumber = wicNumber;
	}

  public List<Voucher> getVouchers() {
    return this.vouchers;
  }

  public void setVouchers(List<Voucher> vouchers) {
    this.vouchers = vouchers;
  }

  public Voucher addVoucher(Voucher voucher) {
    getVouchers().add(voucher);
    voucher.setCustomerId(this.getId());
    return voucher;
  }

  public Voucher removeVoucher(Voucher voucher) {
    getVouchers().remove(voucher);
    voucher.setVoucherNumber(null);
    return voucher;
  }


  @Override
	public String toString() {
		return String.format("address:%s name:%s phone:%s wicNumber:%s ",
				this.getAddress(), this.getName(), this.getPhone(), this.getWicNumber());
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
				+ getStringHash(this.getWicNumber())
				+ getStringHash(this.getWicNumber());
	}

	private int getStringHash(String val) {
		return val == null ? 0 : val.hashCode();
	}

	@Override
	public boolean equals(Object thatObj) {
		if(thatObj == null) {
			return false;
		}
		if(!(thatObj instanceof Customer)) {
			return false;
		}
		Customer that = (Customer)thatObj;
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