package bettercare.wic.dal.entity;

import bettercare.wic.model.CustomerModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * The persistent class for the customer database table.
 */
@Entity
@Table(name = "customer")
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String address;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    @Column(name = "wic_number")
    private String wicNumber;

    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Voucher> vouchers = new ArrayList<>();


    public Customer(String wicNumber, String name, String phone, String address) {
        this.wicNumber = wicNumber;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Customer(CustomerModel customerModel) {
        this(customerModel.getWicNumber(), customerModel.getName(), customerModel.getPhone(), customerModel.getAddress());
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
        this.getVouchers().clear();
        if(vouchers != null && !vouchers.isEmpty()) {
            this.getVouchers().addAll(vouchers);
        }

    }

    public void addVoucher(Voucher voucher) {
        if(voucher != null) {
            this.getVouchers().add(voucher);
            voucher.setCustomer(this);
        }
    }

    @Override
    public String toString() {
        return String.format("address:%s name:%s phone:%s wicNumber:%s ", this.getAddress(), this.getName(), this.getPhone(), this.getWicNumber());
    }

    /**
     * Compute int for fields that defines equals()
     */
    @Override
    public int hashCode() {
        return Long.valueOf(this.getId()).hashCode() + Long.valueOf(this.getAddress()).hashCode() + getStringHash(this.getName()) + getStringHash(this.getPhone()) + getStringHash(this.getWicNumber()) + getStringHash(this.getWicNumber());
    }

    private int getStringHash(String val) {
        return val == null ? 0 : val.hashCode();
    }

    @Override
    public boolean equals(Object thatObj) {
        if (thatObj == null) {
            return false;
        }
        if (!(thatObj instanceof Customer)) {
            return false;
        }
        Customer that = (Customer) thatObj;
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