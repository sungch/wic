package bettercare.wic.service.order;

import bettercare.wic.dal.entity.*;
import bettercare.wic.service.common.InitSetup;
import bettercare.wic.service.common.OrderStatus;
import bettercare.wic.service.common.PackageingModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class OrderSimulator extends InitSetup {

  @Test
  public void processOrder() {
    Customer customer = saveCustomerData("wic-number", "customer-name", "customer-phone", "customer-address");
    Voucher voucher = saveVoucherData(new Date(), new Date(), "voucher-number", customer.getId());
    WicOrder wicOrder = saveWicOrderData("2:3=50;2:4=20", false, new Date(), voucher);
    wicLogger.log("Your order number is " + wicOrder.getId());
  }

  private WicOrder saveWicOrderData(String categoryProductQuantity, boolean isEmergency, Date orderTime, Voucher voucher) {
    WicOrder wicOrder = new WicOrder(isEmergency, orderTime, categoryProductQuantity, OrderStatus.ORDER_RECEIVED.name(), voucher);
    wicLogger.log("Saving a voucher info:" + wicOrder.toString());
    return wicTransactionManager.saveOrUpdateWicOrder(wicOrder);
  }

  // TODO need to test query about date trpe
  private Voucher saveVoucherData(Date start, Date expire, String voucherNumber, long customerId) {
    Voucher voucher = new Voucher(start, expire, voucherNumber, customerId);
    List vouchers = wicEntityManasger.findByNativeQuery(
        String.format("select * from voucher where start_date=%s expiation_date=%s voucher_number=%s, customer_id=%d",
            start, expire, voucherNumber, customerId)
    );
    if(vouchers.isEmpty()) {
      wicLogger.log("Saving a voucher info:" + voucher.toString());
      return wicTransactionManager.saveOrUpdateVoucher(voucher);
    }
    return voucher;
  }

  private Customer saveCustomerData(String wicNumber, String name, String phone, String address) {
    Customer customer = new Customer(wicNumber, name, phone, address);
    List customers = wicEntityManasger.findByNativeQuery(
        String.format("select * from customer where wic_number=%s name=%s phone=%s address=%s",
            wicNumber, name, phone, address));
    if(customers.isEmpty()) {
      wicLogger.log("Saving a customer info:" + customer.toString());
      return wicTransactionManager.saveOrUpdateCustomer(customer);
    }
    return customer;
  }

  private HashMap<Long, String> parseOrder(String catProdQtyList) {
    String[] shoppingItems = catProdQtyList.split(CATEGORY_PROD_DELIMITER);
    List<PackageingModel> idModels = createPackaingModel(shoppingItems);
    List<PackageingModel> packageingList = getPackagingList(idModels);
    return null;
  }

  private List<PackageingModel> createPackaingModel(String[] shoppingItems) {
    List<PackageingModel> idModels = new ArrayList<>(shoppingItems.length);
    for (String item : shoppingItems) {
      try {
        idModels.add(new PackageingModel(item));
      }
      catch (Exception ex) {
        wicLogger.log(ex.getMessage());
      }
      finally {
        wicLogger.log("Error processing " + item);
      }
    }
    return idModels;
  }

  // From the Idmodels, make query to database
  // This is needed for packagers to see ategory name, product name, product descriptions, and product image.
  private List<PackageingModel> getPackagingList(List<PackageingModel> idModels) {
    List<PackageingModel> packageingList = new ArrayList<>(idModels.size());
    for (PackageingModel idModel : idModels) {
      Product product = wicTransactionManager.findProductById(idModel.getProductId());
      idModel.setProduct(product);
    }
    return null;
  }

  public void processNotInStockOrder() {

  }

  public void processNotInDatabaseOrder() {

  }

  public void processMixedOrder() {

  }
}