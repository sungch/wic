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


  // Generics via wicEntityManager

  public <T> T findById(Class<T> clz, long id) {
    return wicEntityManager.find(clz, id);
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


  // Utils

  public boolean isProductExist(long id) {
    return wicTransactionManager.isProductExist(id);
  }


  // Query by JPA patterns

  public List findOrderByStatus(String status ) {
    return wicTransactionManager.findWicOrderByStatus(status);
  }

  public List findProductByIsHandling(String isHandling) {
    return wicTransactionManager.findProductByIsHandling(isHandling);
  }

  public List<Product> findProductsByCategory(Category category) {
    return wicTransactionManager.findProductByCategory(category);
  }

  public List<Voucher> findVoucherByVoucherNumberAndCustomerId(String voucherNumber, long customerId) {
    return wicTransactionManager.findVoucherByVoucherNumberAndCustomerId(voucherNumber, customerId);
  }

  public List<Customer> findCustomerByWicNumberAndPhoneAndAddressAndName(String wicNumber, String phone, String address, String name) {
    return wicTransactionManager.findCustomerByWicNumberAndPhoneAndAddressAndName(wicNumber, phone, address, name);
  }

  public List<Product> findProductByCategoryAndNameAndIsHandling(Category category, String productName, String isHandling) {
    return wicTransactionManager.findProductByCategoryAndNameAndIsHandling(category, productName, isHandling);
  }

  public List<Category> findCategoryByName(String name) {
    return wicTransactionManager.findCategoryByName(name);
  }

  public Category findCategoryById(long categoryId) {
    return wicTransactionManager.findCategoryById(categoryId);

  }
}
