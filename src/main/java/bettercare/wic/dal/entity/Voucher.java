package bettercare.wic.dal.entity;

import bettercare.wic.model.VoucherModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;


/**
 * The persistent class for the voucher database table.
 */
@Entity
@Table(name = "voucher")
@NamedQuery(name="Voucher.findAll", query="SELECT v FROM Voucher v")
public class Voucher implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Future
    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @Past
    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "voucher_number")
    private String voucherNumber;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Voucher(Timestamp startDate, Timestamp expirationDate, String voucherNumber, Customer customer) {
        this.voucherNumber = voucherNumber;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.customer = customer;
    }

    public Voucher (VoucherModel voucherModel, Customer customer) {
        this.voucherNumber = voucherModel.getVoucherNumber();
        this.startDate = voucherModel.getStartDate();
        this.expirationDate = voucherModel.getExpirationDate();
        this.customer = customer;
    }

    @JsonManagedReference
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

    public Timestamp getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Timestamp getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public String getVoucherNumber() {
        return this.voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        return (int) (Long.valueOf(this.getId()).hashCode() + this.getStartDate().getTime() + this.getExpirationDate().getTime() + getStringHash(this.getVoucherNumber()));
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
