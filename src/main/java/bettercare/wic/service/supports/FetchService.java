package bettercare.wic.service.supports;

import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.dal.entity.*;

import javax.annotation.Resource;
import java.util.List;

public class FetchService {

  @Resource
  private WicTransactionManager wicTransactionManager;
  @Resource
  private WicEntityManager wicEntityManager;

  public List<Product> fetchAllProducts() {
    return wicTransactionManager.findAllProducts();
  }

  public List<Category> fetchAllCategories() {
    return wicTransactionManager.findAllCategories();
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

  // --

  public <T> T findByNativeQuery(String voucherQuery, Class<T> clz) {
    return wicEntityManager.findByNativeQuery(voucherQuery, clz);
  }

}
