package bettercare.wic.service;

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

  public <T> T findByNativeQuery(String qry, Class<T> clz) {
    Query nativeQuery = entityManager.createNativeQuery(qry, clz);
    return (T)nativeQuery.getSingleResult();
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
