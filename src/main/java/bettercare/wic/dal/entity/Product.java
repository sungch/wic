package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

  private String description;

  @Column(name = "image_url")
  private String imageUrl;

  private String name;

  @Column(name = "is_handling")
  private String isHandling;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private List<MissingProduct> missingProducts;

  @ManyToOne
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

  public List<MissingProduct> getMissingProducts() {
    return this.missingProducts;
  }

  public void setMissingProducts(List<MissingProduct> missingProducts) {
    this.missingProducts = missingProducts;
  }

  public MissingProduct addMissingProduct(MissingProduct missingProduct) {
    getMissingProducts().add(missingProduct);
    missingProduct.setProductId(this.getId());
    return missingProduct;
  }

  public MissingProduct removeMissingProduct(MissingProduct missingProduct) {
    getMissingProducts().remove(missingProduct);
    missingProduct.setProductId(null);
    return missingProduct;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String isHandling() {
    return isHandling;
  }

  public void setHandling(String is_handling) {
    isHandling = is_handling;
  }

  @Override
  public String toString() {
    return String.format("productId:%s cateoryId:%s imageId:%s barcode:%s description:%s productName:%s isHandling:%s ",
        this.getId(), this.getCategory().getId(), this.getImageUrl(),
        this.getBarcode(), this.getDescription(), this.getName(), this.isHandling );
  }

  /**
   * Compute int for fields that defines equals()
   */
  @Override
  public int hashCode() {
    return Long.valueOf(this.getId()).hashCode()
        + Long.valueOf(this.getCategory().getId()).hashCode()
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
