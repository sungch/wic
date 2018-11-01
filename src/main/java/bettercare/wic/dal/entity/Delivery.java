package bettercare.wic.dal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the delivery database table.
 */
@Entity
@Table(name = "delivery")
@NamedQuery(name = "Delivery.findAll", query = "SELECT d FROM Delivery d")
public class Delivery implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "deliverer_name")
  private String delivererName;

  @Column(name = "delivery_completion_time")
  private long deliveryCompletionTime;

  @Column(name = "store_id")
  private int storeId;

  @JsonBackReference
  @OneToOne
  @JoinColumn(name = "wicOrder_id")
  private WicOrder wicOrder;

  public Delivery(int storeId, WicOrder wicOrder) {
    this.storeId = storeId;
    this.wicOrder = wicOrder;
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

  public long getDeliveryCompletionTime() {
    return this.deliveryCompletionTime;
  }

  public void setDeliveryCompletionTime(long deliveryCompletionTime) {
    this.deliveryCompletionTime = deliveryCompletionTime;
  }

  public int getStoreId() {
    return this.storeId;
  }

  public void setStoreId(byte storeId) {
    this.storeId = storeId;
  }

  public WicOrder getWicOrder() {
    return wicOrder;
  }

  public void setWicOOrder(WicOrder wicOOrderId) {
    this.wicOrder = wicOOrderId;
  }

  @Override
  public String toString() {
    return String.format("nanme:%s storeId:%s completionTime:%s id:%s", this.getDelivererName().hashCode() + this.getStoreId() + this.getDeliveryCompletionTime() + this.getId());
  }

  @Override
  public boolean equals(Object that_) {
    if (that_ == null) {
      return false;
    }
    if (!(that_ instanceof Delivery)) {
      return false;
    }
    Delivery that = (Delivery) that_;
    return isSame(that.toString(), this.toString());
  }

  @Override
  public int hashCode() {
    return super.hashCode() + getStringHash(this.toString());
  }

  private int getStringHash(String val) {
    return val == null ? 0 : val.hashCode();
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