package bettercare.wic.dal.em;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.*;


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


  @PrePersist
  @PreUpdate
  private void validate() {

  }
}
