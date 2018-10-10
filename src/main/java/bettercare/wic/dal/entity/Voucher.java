package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the voucher database table.
 * 
 */
@Entity
@Table(name="voucher")
@NamedQuery(name="Voucher.findAll", query="SELECT v FROM Voucher v")
public class Voucher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VOUCHER_ID_GENERATOR", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VOUCHER_ID_GENERATOR")
	private long id;

	@Column(name="expiration_date")
	private Date expirationDate;

	@Column(name="start_date")
	private Date startDate;

	@Column(name="voucher_id")
	private String voucherId;

	public Voucher(String voucherId, Date startDate, Date expirationDate) {
		this.startDate = startDate;
		this.expirationDate = expirationDate;
		this.voucherId = voucherId;
	}

	public Voucher() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getVoucherId() {
		return this.voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

}