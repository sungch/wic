package bettercare.wic.jpa.service;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class WicService {

  @PersistenceContext(unitName = "wicpu")
  private EntityManager entityManager;

  public <T> void create(T obj) {
    entityManager.persist(obj);
  }

  public <T> T find(Class<T> type, Long id) {
    return entityManager.find(type, id);
  }

  public <T> T merge(T obj) {
    return entityManager.merge(obj);
  }

  public <T> void remove(T obj) {
    entityManager.remove(obj);
  }

  @PrePersist
  @PreUpdate
  private void validate() {


  }
}
