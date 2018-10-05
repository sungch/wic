package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCT_ID_GENERATOR", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_ID_GENERATOR")
	private long id;

	private String barcode;

	private String description;

	@Column(name="image_id")
	private String imageId;

	private String name;

	//bi-directional many-to-one association to MissingProduct
	@OneToMany(mappedBy="product")
	private List<MissingProduct> missingProducts;

	//bi-directional many-to-one association to Category
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

	public String getImageId() {
		return this.imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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
		missingProduct.setProduct(this);

		return missingProduct;
	}

	public MissingProduct removeMissingProduct(MissingProduct missingProduct) {
		getMissingProducts().remove(missingProduct);
		missingProduct.setProduct(null);

		return missingProduct;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}