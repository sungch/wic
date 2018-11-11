package bettercare.wic.dal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


/**
 * The persistent class for the product database table.
 */
@Entity
@Table(name = "product")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  private String barcode;

  @NotBlank
  private String description;

  @NotBlank
  @Column(name = "image_url")
  private String imageUrl;

  @NotBlank
  private String name;

  @NotBlank
  @Column(name = "is_handling")
  private String isHandling;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  public Product() {
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getBarcode() {
    return this.barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getIsHandling() {
    return isHandling;
  }

  public void setIsHandling(String isHandling) {
    this.isHandling = isHandling;
  }

  @Override
  public String toString() {
    return String.format("productId:%s cateoryId:%s imageUrl:%s barcode:%s description:%s productName:%s isHandling:%s ",
        this.getId(), this.getCategory().getId(), this.getImageUrl(),
        this.getBarcode(), this.getDescription(), this.getName(), this.isHandling );
  }

  /**
   * Compute int for fields that defines equals()
   */
  @Override
  public int hashCode() {
    return Long.valueOf(this.getId()).hashCode()
        + getStringHash(this.getImageUrl())
        + getStringHash(this.getBarcode())
        + getStringHash(this.getDescription())
        + getStringHash(this.getName());
  }

  private int getStringHash(String val) {
    return val == null ? 0 : val.hashCode();
  }

  @Override
  public boolean equals(Object thatObj) {
    if(thatObj == null) {
      return false;
    }
    if(!(thatObj instanceof Product)) {
      return false;
    }
    Product that = (Product)thatObj;
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
