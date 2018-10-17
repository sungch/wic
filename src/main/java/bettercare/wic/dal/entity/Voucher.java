package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the voucher database table.
 */
@Entity
@Table(name = "voucher")
@NamedQuery(name="Voucher.findAll", query="SELECT v FROM Voucher v")
public class Voucher implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "VOUCHER_ID_GENERATOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "VOUCHER_ID_GENERATOR")
    private long id;

    @Column(name = "expiration_date")
    private long expirationDate;

    @Column(name = "start_date")
    private long startDate;

    @Column(name = "voucher_number")
    private String voucherNumber;

    @Column(name = "customer_id")
    private long customerId;

    public Voucher(long startDate, long expirationDate, String voucherNumber, long customerId) {
        this.voucherNumber = voucherNumber;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.customerId = customerId;
    }

    @OneToOne(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private WicOrder wicOrder;


    public Voucher() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getStartDate() {
        return this.startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getVoucherNumber() {
        return this.voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

	public WicOrder getWicOrder() {
		return wicOrder;
	}

	public void setWicOrder(WicOrder wicOrder) {
		this.wicOrder = wicOrder;
	}

    @Override
    public String toString() {
        return String.format("id:%s start:%s expire:%s voucherId:%s", this.getId(), this.getStartDate(), this.getExpirationDate(), this.getVoucherNumber());
    }

    @Override
    public int hashCode() {
        return (int) (Long.valueOf(this.getId()).hashCode() + this.getStartDate() + this.getExpirationDate()+ getStringHash(this.getVoucherNumber()));
    }

    private int getStringHash(String val) {
        return val == null ? 0 : val.hashCode();
    }

    @Override
    public boolean equals(Object thatObj) {
        if (thatObj == null) {
            return false;
        }
        if (!(thatObj instanceof Voucher)) {
            return false;
        }
        Voucher that = (Voucher) thatObj;
        return !isDifferent(that.toString(), this.toString());
    }

    private boolean isDifferent(String that, String me) {
        if (that == null) {
            return me == null;
        }
        else {
            return that.equals(me);
        }
    }

}