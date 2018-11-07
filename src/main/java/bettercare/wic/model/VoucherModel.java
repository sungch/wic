package bettercare.wic.model;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Embeddable
public class VoucherModel implements Serializable {

    @NotBlank
    private Timestamp expirationDate;
    @NotBlank
    private Timestamp startDate;
    @NotBlank
    private String voucherNumber;

    public VoucherModel() {}

    public VoucherModel(Timestamp startDate, Timestamp expirationDate, String voucherNumber) {
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.voucherNumber = voucherNumber;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }
}
