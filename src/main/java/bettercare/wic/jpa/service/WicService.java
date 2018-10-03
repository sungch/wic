package bettercare.wic.jpa.service;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class WicService {

  @PersistenceContext(unitName = "wicpu")
  private EntityManager em;

  public <T> void create(T obj) {
    em.persist(obj);
  }

  public <T> T find(Class<T> type, Long id) {
    return em.find(type, id);
  }

  public <T> T merge(T obj) {
    return em.merge(obj);
  }

  public <T> void remove(T obj) {
    em.remove(obj);
  }

  @PrePersist
  @PreUpdate
  private void validate() {


  }
}
