package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class  Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORY_ID_GENERATOR", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORY_ID_GENERATOR")
	private long id;

	private String name;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
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
		product.setCategoryId(this.getId());
		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategoryId(null);
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