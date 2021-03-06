package bettercare.wic.dal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;
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

  /**
   * At run time, Time in this property is assumed to be an UTC time.
   * Make sur htat client sends date value in UTC.
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "delivery_start_time")
  private Timestamp deliveryStartTime;

  /**
   * At run time, Time in this property is assumed to be an UTC time.
   * Make sur htat client sends date value in UTC.
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "delivery_completion_time")
  private Timestamp deliveryCompletionTime;

  @Column(name = "store_id")
  private int storeId;

  @JsonBackReference
  @OneToOne(fetch = FetchType.EAGER)
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

  public Timestamp getDeliveryStartTime() {
    return deliveryStartTime;
  }

  public void setDeliveryStartTime(Timestamp deliveryStartTime) {
    this.deliveryStartTime = deliveryStartTime;
  }

  public Timestamp getDeliveryCompletionTime() {
    return this.deliveryCompletionTime;
  }

  public void setDeliveryCompletionTime(Timestamp deliveryCompletionTime) {
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

  public void setWicOrder(WicOrder wicOOrderId) {
    this.wicOrder = wicOOrderId;
  }

  @Override
  public String toString() {
    return String.format("nanme:%s start:%s storeId:%s completionTime:%s id:%s",
        this.getDelivererName() == null ? "" : this.getDelivererName(),
        this.getDeliveryStartTime() == null ? "" : this.getDeliveryStartTime(),
        this.getStoreId(),
        this.getDeliveryCompletionTime() == null ? "" : this.getDeliveryCompletionTime(),
        this.getId());
  }

  @Override
  public boolean equals(Object that_) {
    if (that_ == null) {
      return false;
    }
    if (!(that_ instanceof Delivery)) {
      return false;
    }
    return this.toString().equals((that_).toString());
  }

  @Override
  public int hashCode() {
    return super.hashCode() + getStringHash(this.toString());
  }

  private int getStringHash(String val) {
    return val == null ? 0 : val.hashCode();
  }
}