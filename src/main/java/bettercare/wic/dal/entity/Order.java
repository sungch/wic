package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name="order")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ORDER_ID_GENERATOR", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_ID_GENERATOR")
	private long id;

	@Column(name="is_emergency")
	private String isEmergency;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ordered_time")
	private Date orderedTime;

	@Column(name="product_and_quantity")
	private String productAndQuantity;

	private String status;

	/**
	 * Note. Unidirectional
	 * mappedBy and @JoinColumn(name = "") are exclusive to each other.
	 * If mappedBy is used, I had to annotate from many side as well.
	 * Using @JoinColumn(name = "") I do not need to do anything on many side in 1:many relationship.
	 * It tell JPA that this a foreign key name in many side
	 * This also prevents cyclic data structure.
	 */
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id") // Tell JPA that this a foreign key name in many side
	private List<MissingProduct> missingProducts;

	@OneToOne
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;

	public Order() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIsEmergency() {
		return this.isEmergency;
	}

	public void setIsEmergency(String isEmergency) {
		this.isEmergency = isEmergency;
	}

	public Date getOrderedTime() {
		return this.orderedTime;
	}

	public void setOrderedTime(Date orderedTime) {
		this.orderedTime = orderedTime;
	}

	public String getProductAndQuantity() {
		return this.productAndQuantity;
	}

	public void setProductAndQuantity(String productAndQuantity) {
		this.productAndQuantity = productAndQuantity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MissingProduct> getMissingProducts() {
		return this.missingProducts;
	}

	public void setMissingProducts(List<MissingProduct> missingProducts) {
		this.missingProducts = missingProducts;
	}

	public MissingProduct addMissingProduct(MissingProduct missingProduct) {
		getMissingProducts().add(missingProduct);
		missingProduct.setOrderId(this.getId());
		return missingProduct;
	}

	public MissingProduct removeMissingProduct(MissingProduct missingProduct) {
		getMissingProducts().remove(missingProduct);
		missingProduct.setOrderId(null);
		return missingProduct;
	}

	public Voucher getVoucher() {
		return this.voucher;
	}

	public void setVoucherId(Voucher voucher) {
		this.voucher = voucher;
	}

}