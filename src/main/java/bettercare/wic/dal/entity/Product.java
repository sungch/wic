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
  @SequenceGenerator(name = "PRODUCT_ID_GENERATOR", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_GENERATOR")
  private long id;

  private String barcode;

  private String description;

  @Column(name = "image_name")
  private String imageName;

  private String name;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private List<MissingProduct> missingProducts;

  @Column(name = "category_id")
  private long categoryId;

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

  public String getImageName() {
    return this.imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
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

  public long getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public String toString() {
    return String.format("productId:%s cateoryId:%s imageId:%s barcode:%s description:%s productName:%s ",
        this.getId(), this.getCategoryId(), this.getImageName(),
        this.getBarcode(), this.getDescription(), this.getName() );
  }

  /**
   * Compute int for fields that defines equals()
   */
  @Override
  public int hashCode() {
    return Long.valueOf(this.getId()).hashCode()
        + Long.valueOf(this.getCategoryId()).hashCode()
        + getStringHash(this.getImageName())
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
    return !isDifferent(that.toString(), this.toString());
  }

  private boolean isDifferent(String that, String me) {
    if(that == null) {
      return me == null;
    }
    else {
      return that.equals(me);
    }
  }
}