package bettercare.wic.dal.em;

import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.service.OrderStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.List;


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
    Query query = entityManager.createNativeQuery("select * from wic_order where status != '" + OrderStatus.DELIVERY_COMPLETED.name() + "'");
    System.out.println(query.toString());
    return query.getResultList();
  }

  @PrePersist
  @PreUpdate
  private void validate() {

  }
}
