package bettercare.wic.dal.em;

import bettercare.wic.service.OrderStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.List;

/**
 * Use this object if a query cannot be handled by WicTransactionManager.
 */
@Service
public class WicEntityManager {

  @Resource
  private EntityManager entityManager;

  public <T> T create(T obj) {
    entityManager.persist(obj);
    entityManager.flush();
    return obj;
  }

  public <T> T merge(T obj) {
    entityManager.merge(obj);
    entityManager.flush();
    return obj;
  }

  public <T> T find(Class<T> type, Long id) {
    return entityManager.find(type, id);
  }

  public <T> void remove(T obj) {
    entityManager.remove(obj);
    entityManager.flush();
  }

  public List findPendingOrders() {
    return entityManager.createNativeQuery("select * from wic_order where status != '" +
        OrderStatus.DELIVERY_COMPLETED.name() + "'").getResultList();
  }

  @PrePersist
  @PreUpdate
  private void validate() {

  }
}
