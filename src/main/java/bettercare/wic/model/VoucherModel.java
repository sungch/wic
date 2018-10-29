package bettercare.wic.model;


import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoucherModel implements Serializable {

    private long expirationDate;
    private long startDate;
    private String voucherNumber;

    public VoucherModel() {}

    public VoucherModel(long startDate, long expirationDate, String voucherNumber) {
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.voucherNumber = voucherNumber;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }
}
