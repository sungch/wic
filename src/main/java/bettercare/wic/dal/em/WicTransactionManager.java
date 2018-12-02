package bettercare.wic.dal.em;

import bettercare.wic.dal.entity.User;
import bettercare.wic.utils.WicLogger;
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
  @Resource
  private UserDao userDao;

  public <T> List<T> findAll(Class<T> clz) {
    return getDao(clz).findAll();
  }

  public <T> T saveOrUpdate(Class<T> clz, T obj) {
    return (T) getDao(clz).saveAndFlush(obj);
  }

  public <T> void deleteById(Class<T> clz, long id) {
    getDao(clz).deleteById(id);
  }

  public <T> void delete(Class<T> clz, T entity) {
    getDao(clz).delete(entity);
  }


  // Category

  public List<Category> findCategoryByName(String name) {
    return categoryDao.findByName(name);
  }

  public Category findCategoryById(long id) {
    return categoryDao.findById(id).orElse(null);
  }

  // Product

  public List<Product> findProductByCategoryAndNameAndIsHandling(Category category, String productName, boolean isHandling) {
    return productDao.findByCategoryAndNameAndIsHandling(category, productName, isHandling);
  }

  public List<Product> findProductByIsHandling(boolean isHandling) {
    return productDao.findByIsHandling(isHandling);
  }

  public List<Product> findProductByCategoryId(long id) {
    return productDao.findByCategoryId(id);
  }

  // WicOrder

  public List<WicOrder> findWicOrderByStatus(String status) {
    return wicOrderDao.findByStatus(status);
  }


  // Voucher

  public List<Voucher> findVoucherByVoucherNumber(String voucherNumber) {
    return voucherDao.findByVoucherNumber(voucherNumber);
  }


  // Customer

  public List<Customer> findCustomerByWicNumberAndPhoneAndAddressAndName(String wicNumber, String phone, String address, String name) {
    return customerDao.findByWicNumberAndPhoneAndAddressAndName(wicNumber, phone, address, name);
  }


  // Delivery

  public Delivery findDeliveryByWicOrder(WicOrder wicOrder) {
    return deliveryDao.findByWicOrder(wicOrder);
  }


  // Utils

  public <T> boolean isEntityExist(Class<T> clz, long id) {
    return getDao(clz).findById(id).isPresent();
  }

  private <T> JpaRepository getDao(Class<T> entityClaz) {
    if (entityClaz.equals(Category.class)) {
      return categoryDao;
    }
    if (entityClaz.equals(Customer.class)) {
      return customerDao;
    }
    if (entityClaz.equals(Delivery.class)) {
      return deliveryDao;
    }
    if (entityClaz.equals(MissingProduct.class)) {
      return missingProductDao;
    }
    if (entityClaz.equals(Product.class)) {
      return productDao;
    }
    if (entityClaz.equals(Voucher.class)) {
      return voucherDao;
    }
    if (entityClaz.equals(WicOrder.class)) {
      return wicOrderDao;
    }
    if(entityClaz.equals(User.class)) {
      return userDao;
    }
    return null;
  }

  public User findUserByName(String username) {
    return userDao.findByUsername(username);
  }
}