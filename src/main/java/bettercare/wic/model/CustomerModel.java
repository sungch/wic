package bettercare.wic.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CustomerModel implements Serializable {

  private String address;
  private String name;
  private String phone;
  private String wicNumber;

  public CustomerModel(String address, String name, String phone, String wicNumber) {
    this.address= address;
    this.name = name;
    this.phone= phone;
    this.wicNumber = wicNumber;
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
}
