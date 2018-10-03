package bettercare.wic.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class WicService {

  @PersistenceContext(unitName = "wicpu")
  private EntityManager em;

  public <T> void create(T obj) {
    em.persist(obj);
  }

  public <T> T  find(Class<T> type, T obj) {
    return em.find(type, obj);
  }

  public <T> T  update(T obj) {
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
