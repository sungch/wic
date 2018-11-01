package bettercare.wic.dal.em;

import bettercare.wic.dal.WicLogger;
import bettercare.wic.dal.dao.*;
import bettercare.wic.dal.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WicTransactionManager {

  // requires @EnableJpaRepositories("bettercare.wic.dal") in somewhere configuration
  @Resource
  private CategoryDao categoryDao;
  @Resource
  private ProductDao productDao;
  @Resource
  private CustomerDao customerDao;
  @Resource
  private VoucherDao voucherDao;
  @Resource
  private WicOrderDao wicOrderDao;
  @Resource
  private WicLogger wicLogger;
  @Resource
  private MissingProductDao missingProductDao;
  @Resource
  private DeliveryDao deliveryDao;

  public <T> List<T> findAll(Class<T> clz) {
    return getDao(clz).findAll();
  }

  public <T> T saveOrUpdate(Class<T> clz, T obj) {
    return (T) getDao(clz).saveAndFlush(obj);
  }

  public <T> void deleteById(Class<T> clz, long id) {
    getDao(clz).deleteById(id);
  }

  // Category

  public List<Category> findCategoryByName(String name) {
    return categoryDao.findByName(name);
  }


  // Product

  public boolean isProductExist(long id) {
    return productDao.findById(id).isPresent();
  }

//  public List<Product> findProductByCategoryIdAndNameAndIsHandling(long categoryId, String name, String isHandling) {
//    return productDao.findByCategoryIdAndNameAndIsHandling(categoryId, name, isHandling);
//  }

  public List<Product> findProductByIsHandling(String isHandling) {
    return productDao.findByIsHandling(isHandling);
  }

//  public List<Product> findProductByCategoryId(long categoryId) {
//    return productDao.findByCategoryId(categoryId);
//  }


  // WicOrder

  public List<WicOrder> findWicOrderByStatus(String status) {
    return wicOrderDao.findByStatus(status);
  }


  // Voucher

  public List<Voucher> findVoucherByVoucherNumberAndCustomerId(String voucherNumber, long customerId) {
    return voucherDao.findByVoucherNumberAndCustomerId(voucherNumber, customerId);
  }


  // Customer

  public List<Customer> findCustomerByWicNumberAndPhoneAndAddressAndName(String wicNumber, String phone, String address, String name) {
    return customerDao.findByWicNumberAndPhoneAndAddressAndName(wicNumber, phone, address, name);
  }


  private <T> JpaRepository getDao(Class<T> repo) {
    if (repo.equals(Category.class)) {
      return categoryDao;
    }
    if (repo.equals(Customer.class)) {
      return customerDao;
    }
    if (repo.equals(Delivery.class)) {
      return deliveryDao;
    }
    if (repo.equals(MissingProduct.class)) {
      return missingProductDao;
    }
    if (repo.equals(Product.class)) {
      return productDao;
    }
    if (repo.equals(Voucher.class)) {
      return voucherDao;
    }
    if (repo.equals(WicOrder.class)) {
      return wicOrderDao;
    }
    return null;
  }

}