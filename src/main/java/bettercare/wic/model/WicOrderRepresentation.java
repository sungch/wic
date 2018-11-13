package bettercare.wic.model;


import javax.persistence.Embedded;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This object is a representation of WicOrder, Voucher, and Customer to communicate 
 * from a customer.
 */
public class WicOrderRepresentation implements Serializable {

    /**
     * Use built-in tool serialver: 
     * serialver -classpath . bettercare.wic.model.WicOrderRepresentation
     */
    private static final long serialVersionUID = -1437982395144640698L;
    private long orderId;
    private boolean isEmergency;
    private Timestamp orderedTime;
    private String status;

    @NotBlank
    private String products;

    @Embedded
    CustomerModel customerModel;

    @Embedded
    VoucherModel voucherModel;

    public WicOrderRepresentation() {}

    public WicOrderRepresentation(long orderId, boolean isEmergency, long orderedTime, String products, String status,
                                  CustomerModel customerModel, VoucherModel voucherModel) {
        this.orderId = orderId;
        this.isEmergency = isEmergency;
        this.orderedTime = new Timestamp(orderedTime);
        this.products = products;
        this.status = status;
        this.customerModel = customerModel;
        this.voucherModel = voucherModel;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }

    public Timestamp getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(Timestamp orderedTime) {
        this.orderedTime = orderedTime;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public VoucherModel getVoucherModel() {
        return voucherModel;
    }

    public void setVoucherModel(VoucherModel voucherModel) {
        this.voucherModel = voucherModel;
    }
}
