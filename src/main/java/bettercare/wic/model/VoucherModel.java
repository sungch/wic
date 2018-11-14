package bettercare.wic.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Embeddable;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.sql.Timestamp;

@Embeddable
public class VoucherModel implements Serializable {

    /**
     * Use built-in tool serialver: 
     * serialver -classpath . bettercare.wic.model.VoucherModel
     */
    private static final long serialVersionUID = -7031762038088179417L;

    @NotBlank
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")//, timezone = "MST") When not set, UTC is used.
    private Timestamp expirationDate;

    @NotBlank
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
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

    @Override
    public String toString() {
        return this.voucherNumber + this.startDate + this.expirationDate;
    }
}
