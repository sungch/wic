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

	/**
	 * Why FetchType.EAGER:
	 * 1-to-many association fetch type is eager here but in production, change this to lazy and
	 * let OpenSessionInViewFilter in web.xml handle to maintain session.
	 *
	 * Note. Unidirectional.
	 * mappedBy and @JoinColumn(name = "") are exclusive to each other.
	 * If mappedBy is used, I had to annotate from many side as well.
	 * Using @JoinColumn(name = "") I do not need to do anything on many side in 1:many relationship.
	 * It tell JPA that this a foreign key name in many side
	 * This also prevents cyclic data structure.
	 */
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

}