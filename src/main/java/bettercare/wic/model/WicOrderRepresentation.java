package bettercare.wic.model;


import javax.persistence.Embedded;
import java.io.Serializable;

public class WicOrderRepresentation implements Serializable {

    private static final long serialVersionUID = -1437982395144640698L;
    //@JsonProperty("order-id")
    private long orderId;
    //@JsonIgnore
    private boolean isEmergency;
    private long orderedTime;
    private String products;
    private String status;

    @Embedded
    CustomerModel customerModel;

    @Embedded
    VoucherModel voucherModel;

    public WicOrderRepresentation() {}

    public WicOrderRepresentation(long orderId, boolean isEmergency, long orderedTime, String products, String status,
                                  CustomerModel customerModel, VoucherModel voucherModel) {
        this.orderId = orderId;
        this.isEmergency = isEmergency;
        this.orderedTime = orderedTime;
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

    public long getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(long orderedTime) {
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
