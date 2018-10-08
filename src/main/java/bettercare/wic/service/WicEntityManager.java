package bettercare.wic.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.List;
import java.util.Map;


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

  public <T> List findByNativeQuery(String query) {
//    String qry = getNativeQueryString(type, where, otherClause);
    return entityManager.createNativeQuery(query).getResultList();
  }

//  private <T> String getNativeQueryString(T type, Map<String, String> where, String otherClause) {
//    String alias = "o";
//    StringBuilder whereClause = new StringBuilder();
//    where.forEach((columnName, value) -> {
//      if (whereClause.length() > 0) {
//        whereClause.append(" and ");
//      }
//      whereClause.append(alias).append(".").append(columnName).append("='").append(value).append("'");
//    });
//    String others = otherClause == null || otherClause.isEmpty() ? "" : otherClause;
//    return String.format("select * from %s as %s where %s %s",
//        ((Class) type).getSimpleName().toLowerCase(), alias, whereClause.toString(), others);
//  }

//  public <T> boolean exists(String query) {
//    return !(entityManager.createQuery(query).getResultList().isEmpty());
//  }

  public <T> void remove(T obj) {
    entityManager.remove(obj);
    entityManager.flush();
  }

  @PrePersist
  @PreUpdate
  private void validate() {


  }
}
