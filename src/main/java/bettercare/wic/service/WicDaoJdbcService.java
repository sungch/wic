package bettercare.wic.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Service
public class WicDaoJdbcService {

  @PersistenceContext(unitName = "wic") // ?? is this best way ??
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
