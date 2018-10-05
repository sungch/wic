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
	@SequenceGenerator(name="CATEGORY_ID_GENERATOR", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORY_ID_GENERATOR")
	private long id;

	private String name;

	/**
	 * bi-directional many-to-one association to Product
	 * fetch type is eager but in production, change this to lazy and
	 * let OpenSessionInViewFilter in web.xml handle to maintain session.
	 */
	@OneToMany(mappedBy="category", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<bettercare.wic.dal.entity.Product> products;

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

	public List<bettercare.wic.dal.entity.Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<bettercare.wic.dal.entity.Product> products) {
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

}