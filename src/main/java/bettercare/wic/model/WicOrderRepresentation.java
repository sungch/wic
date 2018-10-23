package bettercare.wic.model;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

public class WicOrderRepresentation implements Serializable {

    private static final long serialVersionUID = -1437982395144640698L;

    private long orderId;
    private boolean isEmergency;
    private long orderedTime;
    private String products;
    private String status;

    private String address;
    private String name;
    private String phone;
    private String wicNumber;

    private long expirationDate;
    private long startDate;
    private String voucherNumber;

    public WicOrderRepresentation() {}

    public WicOrderRepresentation(long orderId, boolean isEmergency, long orderedTime, String products, String status, String address,
                                  String name, String phone, String wicNumber, long startDate, long expirationDate,
                                  String voucherNumber) {
        this.orderId = orderId;
        this.isEmergency = isEmergency;
        this.orderedTime = orderedTime;
        this.products = products;
        this.status = status;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.wicNumber = wicNumber;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.voucherNumber = voucherNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWicNumber() {
        return wicNumber;
    }

    public void setWicNumber(String wicNumber) {
        this.wicNumber = wicNumber;
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
