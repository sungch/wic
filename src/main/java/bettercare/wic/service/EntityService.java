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

  public <T> List<T> findAll(Class<T> clz) {
    return wicTransactionManager.findAll(clz);
  }

  public List findOrderByStatus(String status ) {
    return wicTransactionManager.findOrderByStatus(status);
  }

  public List findProductByIsHandling(String isHandling) {
    return wicTransactionManager.findProductByIsHandling(isHandling);
  }

  public <T> void deleteOrderById(T obj) {
    wicEntityManager.remove(obj);
  }

  public List<Product> findProductsByCategoryId(long id) {
    return wicTransactionManager.findproductByCategoryId(id);
  }

  public List<Voucher> findVoucherByVoucherNumberAndCustomerId(String voucherNumber, long customerId) {
    return wicTransactionManager.findVoucherByVoucherNumberAndCustomerId(voucherNumber, customerId);
  }

  public List<Customer> findCustomerByWicNumberAndPhoneAndAddressAndName(String wicNumber, String phone, String address, String name) {
    return wicTransactionManager.findCustomerByWicNumberAndPhoneAndAddressAndName(wicNumber, phone, address, name);
  }

  public List<Product> findProductByCategoryIdAndNameAndIsHandling(long categoryId, String name, String isHandling) {
    return wicTransactionManager.findProductByCategoryIdAndNameAndIsHandling(categoryId, name, isHandling);
  }

  public List<Category> findCategoryByName(String name) {
    return wicTransactionManager.findCategoryByName(name);
  }
}
