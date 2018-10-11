package bettercare.wic.service.order;

import bettercare.wic.dal.entity.*;
import bettercare.wic.service.common.InitSetup;
import bettercare.wic.service.common.OrderStatus;
import bettercare.wic.service.common.PackageingModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrderSimulator extends InitSetup {

  public void processOrder() {
    saveCustomerData("wic-id", "customer-name", "customer-phone", "customer-address");
    Voucher voucher = saveVoucherData(new Date(), new Date(), "voucher-id");
    WicOrder wicOrder = saveWicOrderData("2:3=50;2:4=20", false, new Date(), voucher);
    wicLogger.log("Your order number is " + wicOrder.getId());
  }

  private WicOrder saveWicOrderData(String categoryProductQuantity, boolean isEmergency, Date orderTime, Voucher voucher) {
    WicOrder wicOrder = new WicOrder(isEmergency, orderTime, categoryProductQuantity, OrderStatus.ORDER_RECEIVED.name(), voucher);
    wicLogger.log("Saved a voucher info:" + wicOrder.toString());
    return wicTransactionManager.saveOrUpdateWicOrder(wicOrder);
  }

  private Voucher saveVoucherData(Date start, Date expire, String voucherId) {
    Voucher voucher = new Voucher(voucherId, start, expire);
    wicLogger.log("Saved a voucher info:" + voucher.toString());
    return wicTransactionManager.saveOrUpdateVoucher(voucher);
  }

  private Customer saveCustomerData(String wicId, String name, String phone, String address) {
    Customer customer = new Customer(wicId, name, phone, address);
    wicLogger.log("Saved a customer info:" + customer.toString());
    return wicTransactionManager.saveOrUpdateCustomer(customer);
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