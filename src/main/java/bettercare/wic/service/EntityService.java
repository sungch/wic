package bettercare.wic.service;

import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.dal.entity.*;
import bettercare.wic.service.supports.OrderStatus;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class EntityService {

  @Resource
  private WicTransactionManager wicTransactionManager;
  @Resource
  private WicEntityManager wicEntityManager;

  public List<Product> fetchProducts() {
    return wicTransactionManager.findProducts();
  }

  public List<Category> fetchCategories() {
    return wicTransactionManager.findCategories();
  }

  public Category saveOrUpdateCategory(Category category) {
    return wicTransactionManager.saveOrUpdateCategory(category);
  }

  public Voucher saveOrUpdateVoucher(Voucher voucher) {
    return wicTransactionManager.saveOrUpdateVoucher(voucher);
  }

  public Customer saveOrUpdateCustomer(Customer customer) {
    return wicTransactionManager.saveOrUpdateCustomer(customer);
  }

  public WicOrder saveOrUpdateWicOrder(WicOrder wicOrder) {
    return wicTransactionManager.saveOrUpdateWicOrder(wicOrder);
  }

  public List<Product> findProductsByCategoryId(long id) {
    return wicTransactionManager.findProductsByCategoryId(id);
  }


  public Product saveOrUpdateProduct(Product product) {
    return wicTransactionManager.saveOrUpdateProduct(product);
  }

  // --

  public <T> T findByNativeQuery(String voucherQuery, Class<T> clz) {
    return wicEntityManager.findByNativeQuery(voucherQuery, clz);
  }

  public List fetchOrders() {
    return wicTransactionManager.findOrders();
  }

  public Optional<WicOrder> fetchOrder(long id) {
    return wicTransactionManager.findOrder(id);
  }

  public List fetchPendingOrders() {
    String qry = "select * from wic_order where status is not " + OrderStatus.DELIVERY_COMPLETED.name();
    return wicEntityManager.findListByNativeQuery(qry, WicOrder.class);
  }

  public <T> List<T> findListByNativeQuery(String query, Class<T> clz) {
    return wicEntityManager.findListByNativeQuery(query, clz);
  }

  public void deleteOrderById(long id) {
    wicTransactionManager.deleteOrderById(id);
  }
}
