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

  @Column(name = "image_path")
  private String imagePath;

  private String name;

  /**
   * Note.
   * mappedBy and @JoinColumn(name = "") are exclusive to each other.
   * If mappedBy is used, I had to annotate from many side as well.
   * Using @JoinColumn(name = "") I do not need to do anything on many side in 1:many relationship.
   * It tell JPA that this a foreign key name in many side
   */
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

  public String getImagePath() {
    return this.imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
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

}