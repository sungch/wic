package bettercare.wic.dal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class  Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	private String name;

	@Column(name = "image_url")
	private String imageUrl;

	@OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Product> products;

	public Category() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCategory(this);
		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategory(null);
		return product;
	}

	@Override
	public String toString() {
		return String.format("categoryId:%s cateoryName:%s ", this.getId(), this.getName());
	}

	/**
	 * Compute int for fields that defines equals()
	 */
	@Override
	public int hashCode() {
		return Long.valueOf(this.getId()).hashCode() + getStringHash(this.getName());
	}

	private int getStringHash(String val) {
		return val == null ? 0 : val.hashCode();
	}

	@Override
	public boolean equals(Object thatObj) {
		if(thatObj == null) {
			return false;
		}
		if(!(thatObj instanceof Category)) {
			return false;
		}
		Category that = (Category) thatObj;
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