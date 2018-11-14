package bettercare.wic.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
public class CustomerModel implements Serializable {

  /**
   * Use built-in tool serialver: 
   * serialver -classpath . bettercare.wic.model.CustomerModel
   */
  private static final long serialVersionUID = -8307727533285363981L;

  @NotBlank
  private String address;
  @NotBlank
  private String name;
  @NotBlank
  private String phone;
  @NotBlank
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

  @Override
  public String toString() {
    return this.address + this.name + this.phone + this.wicNumber;
  }
}
