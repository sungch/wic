package bettercare.wic.model;


import javax.persistence.Embedded;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * This object is used to model incoming customer order and save in WicOrder, Voucher, and Customer.
 */
public class WicOrderRepresentation implements Serializable {

    /**
     * Use built-in tool serialver: 
     * serialver -classpath . bettercare.wic.model.WicOrderRepresentation
     */
    private static final long serialVersionUID = -1437982395144640698L;
    private long orderId;
    private boolean hasMissingProduct = false;

    private String status;

    @NotBlank
    private String products;

    @Embedded
    CustomerModel customerModel;

    @Embedded
    VoucherModel voucherModel;

    public WicOrderRepresentation() {}

    public WicOrderRepresentation(long orderId, boolean hasMissingProduct, String products, String status,
                                  CustomerModel customerModel, VoucherModel voucherModel) {
        this.orderId = orderId;
        this.hasMissingProduct = hasMissingProduct;
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

    public boolean isHasMissingProduct() {
        return hasMissingProduct;
    }

    public void setHasMissingProduct(boolean hasMissingProduct) {
        this.hasMissingProduct = hasMissingProduct;
    }

    @Override
    public String toString() {
        return products + hasMissingProduct + status + customerModel.toString() + voucherModel.toString();
    }
}
