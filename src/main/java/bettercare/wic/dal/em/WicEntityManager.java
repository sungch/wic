package bettercare.wic.dal.em;

import bettercare.wic.dal.WicLogger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.List;


@Service
public class WicEntityManager {

  @Resource
  private EntityManager entityManager;
  @Resource
  private WicLogger wicLogger;

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
    try {
    return (T)nativeQuery.getSingleResult();
    }
    catch(NoResultException nre) {
      return null;
    }
    catch (Exception ex) {
      wicLogger.log("Error:" + ex.getMessage());
      throw new RuntimeException(ex);
    }
  }

  public <T> List findListByNativeQuery(String qry, Class<T> clz) {
    Query nativeQuery = entityManager.createNativeQuery(qry, clz);
    return nativeQuery.getResultList();
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
