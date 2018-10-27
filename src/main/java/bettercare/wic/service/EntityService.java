package bettercare.wic.service;

import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.dal.entity.*;

import javax.annotation.Resource;
import java.util.List;

public class EntityService {

  @Resource
  private WicTransactionManager wicTransactionManager;
  @Resource
  private WicEntityManager wicEntityManager;


  public <T> T saveOrUpdate(Class<T> clz, T obj) {
    return wicTransactionManager.saveOrUpdate(clz, obj);
  }

  public <T> T findById(Class<T> clz, long id) {
    return wicEntityManager.find(clz, id);
  }

  public <T> T findByNativeQuery(Class<T> clz, String voucherQuery) {
    return wicEntityManager.findByNativeQuery(voucherQuery, clz);
  }

  public <T> List findListByNativeQuery(Class<T> clz, String query) {
    return wicEntityManager.findListByNativeQuery(query, clz);
  }

  public <T> List<T> findAll(Class<T> clz) {
    return wicTransactionManager.findAll(clz);
  }

  public List findPendingOrders() {
    String qry = "select * from wic_order where status is not " + OrderStatus.DELIVERY_COMPLETED.name();
    return wicEntityManager.findListByNativeQuery(qry, WicOrder.class);
  }

  public List findSupportedProducts() {
    String qry = "select * from product where is_handling = 'Y'";
    return wicEntityManager.findListByNativeQuery(qry, Product.class);
  }

  public void findNotSupportedProducts() {
    String qry = "select * from product where is_handling != 'Y'";
    wicEntityManager.findListByNativeQuery(qry, Product.class);
  }

  public <T> void deleteOrderById(T obj) {
    wicEntityManager.remove(obj);
  }

  public List<Product> findProductsByCategoryId(long id) {
    return wicEntityManager.findProductsByCategoryId(id);
  }
}
