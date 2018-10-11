package bettercare.wic.dal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the delivery database table.
 * 
 */
@Entity
@Table(name="delivery")
@NamedQuery(name="Delivery.findAll", query="SELECT d FROM Delivery d")
public class Delivery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELIVERY_ID_GENERATOR", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELIVERY_ID_GENERATOR")
	private long id;

	@Column(name="deliverer_name")
	private String delivererName;

	@Column(name="delivery_completion_time")
	private Date deliveryCompletionTime;

	@Column(name="store_id")
	private int storeId;

	@Column(name="order_id")
	private long orderId;

	public Delivery(int storeId, long orderId) {
		this.storeId = storeId;
		this.orderId = orderId;
	}

	public Delivery() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDelivererName() {
		return this.delivererName;
	}

	public void setDelivererName(String delivererName) {
		this.delivererName = delivererName;
	}

	public Date getDeliveryCompletionTime() {
		return this.deliveryCompletionTime;
	}

	public void setDeliveryCompletionTime(Date deliveryCompletionTime) {
		this.deliveryCompletionTime = deliveryCompletionTime;
	}

	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreId(byte storeId) {
		this.storeId = storeId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(byte orderId) {
		this.orderId = orderId;
	}
}