package bettercare.wic.dal.entity;

import java.io.Serializable;
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
	@SequenceGenerator(name="CUSTOMER_ID_GENERATOR", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CUSTOMER_ID_GENERATOR")
	private long id;

	private String address;

	private String name;

	private String phone;

	@Column(name="wic_id")
	private String wicId;

	public Customer(String wicId, String name, String phone, String address) {
		this.wicId = wicId;
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

	public String getWicId() {
		return this.wicId;
	}

	public void setWicId(String wicId) {
		this.wicId = wicId;
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
				+ getStringHash(this.getWicId());
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
		return true;
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