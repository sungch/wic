package bettercare.wic.service;

import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.dal.entity.*;
import bettercare.wic.dal.entity.user.User;

import javax.annotation.Resource;
import java.util.List;

public class EntityService {

  @Resource
  private WicTransactionManager wicTransactionManager;
  @Resource
  private WicEntityManager wicEntityManager;


  // Generics via wicEntityManager

  public <T> T findById(Class<T> clz, long id) {
    return wicEntityManager.find(clz, id);
  }

  public List findPendingOrders() {
    return wicEntityManager.findPendingOrders();
  }

  // Generics via wicTransactionManager

  public <T> T saveOrUpdate(Class<T> clz, T obj) {
    return wicTransactionManager.saveOrUpdate(clz, obj);
  }

  public <T> List<T> findAll(Class<T> clz) {
    return wicTransactionManager.findAll(clz);
  }

  public <T> void deleteById(Class<T> T, long id) {
    wicTransactionManager.deleteById(T, id);
  }

  public <T> void delete(Class<T> clz, T entity) {
    wicTransactionManager.delete(clz, entity);
  }


  // Utils

  public <T> boolean isEntityExist(Class<T> clz, long id) {
    return wicTransactionManager.isEntityExist(clz, id);
  }


  // Query by JPA patterns

  public List findOrderByStatus(String status ) {
    return wicTransactionManager.findWicOrderByStatus(status);
  }

  public List findProductByIsHandling(boolean isHandling) {
    return wicTransactionManager.findProductByIsHandling(isHandling);
  }

  public List<Product> findProductsByCategoryId(long id) {
    return wicTransactionManager.findProductByCategoryId(id);
  }

  public List<Voucher> findVoucherByVoucherNumber(String voucherNumber) {
    return wicTransactionManager.findVoucherByVoucherNumber(voucherNumber);
  }

  public List<Customer> findCustomerByWicNumberAndPhoneAndAddressAndName(String wicNumber, String phone, String address, String name) {
    return wicTransactionManager.findCustomerByWicNumberAndPhoneAndAddressAndName(wicNumber, phone, address, name);
  }

  public List<Product> findProductByCategoryAndNameAndIsHandling(Category category, String productName, boolean isHandling) {
    return wicTransactionManager.findProductByCategoryAndNameAndIsHandling(category, productName, isHandling);
  }

  public List<Category> findCategoryByName(String name) {
    return wicTransactionManager.findCategoryByName(name);
  }

  public Category findCategoryById(long categoryId) {
    return wicTransactionManager.findCategoryById(categoryId);

  }

  public Delivery findDeliveryByWicOrder(WicOrder wicOrder) {
    return wicTransactionManager.findDeliveryByWicOrder(wicOrder);
  }

    public User findUserByName(String username) {
      return wicTransactionManager.findUserByName(username);
    }
}
