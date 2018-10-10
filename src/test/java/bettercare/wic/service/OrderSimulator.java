package bettercare.wic.service;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.entity.*;
import bettercare.wic.service.utils.OrderStatus;
import bettercare.wic.service.utils.PackageingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * This is a similitute of receiving an order from a customer
 * <p>
 * In real environment, each order will be handled by a different thread.
 * When multiple orders arrive at the same time, the object class which
 * implements createOrder method must be thread safe: a new instance or immutable.
 * <p>
 * Processes:
 * Input:
 * wic_order(is_emergency, order_time, [categoryId:productId=quantity]+)
 * voucher (start_date, expiration_date, voucher_id),
 * customer (address, name, phone)
 * Output: Order Number
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class OrderSimulator {

  @Resource private WicTransactionManager wicTransactionManager;
  @Resource private WicEntityManager wicEntityManasger;

  private String CATEGORY_PROD_DELIMITER = ":";

  @Test
  public void contextLoads() {
  }

  @Before
  public void setup() {
    Assert.assertNotNull(wicTransactionManager);
    Assert.assertNotNull(wicEntityManasger);
  }

  /**
   * All these customer data used in this method are from customer order front page
   * parseOrder(categoryProductQuantityList); This is for packager later use.
   */
  public void saveDataFromCustomer() {

    // customer data
    String wicId = null;
    String name = null;
    String phone = null;
    String address = null;
    Customer customer = new Customer(wicId, name, phone, address);
    wicTransactionManager.saveOrUpdateCustomer(customer);

    // -----------------------------------

    // voucher data from a customer
    Date voucherStartDate = null;
    Date voucherExpirationDate = null;
    String voucherId = null;
    Voucher voucher = new Voucher(voucherId, voucherStartDate, voucherExpirationDate);
    wicTransactionManager.saveOrUpdateVoucher(voucher);

    // --------------------------------------
    // Order data from a customer.
    // = "2:3=50;2:4=20"; // products 3,4 and in database product list and has in stock.)
    String categoryProductQuantity = null;
    boolean isEmergency = false;
    Date orderTime = null;
    WicOrder wicOrder = new WicOrder(isEmergency, orderTime, categoryProductQuantity, OrderStatus.ORDER_RECEIVED.name(), voucher, null);
    wicTransactionManager.saveOrUpdateWicOrder(wicOrder);

  }

  private HashMap<Long, String> parseOrder(String catProdQtyList) {
    String[] shoppingItems = catProdQtyList.split(CATEGORY_PROD_DELIMITER);
    List<PackageingModel> idModels = createPackaingModel(shoppingItems);
    List<PackageingModel> packageingList = getPackagingList(idModels);
    return null;
  }

  /**
   * turn categoryId, productId, quantity from string to Long and pack them as IdModel
   * if shoppingItems.length > 0
   * convert all string DSi to long IDS via model
   */
  private List<PackageingModel> createPackaingModel(String[] shoppingItems) {
    List<PackageingModel> idModels = new ArrayList<>(shoppingItems.length);
    for (String item : shoppingItems) {
      try {
        idModels.add(new PackageingModel(item));
      }
      catch (Exception ex) {
        log(ex.getMessage());
      }
      finally {
        log("Error processing " + item);
      }
    }
    return idModels;
  }
  /**
   *  From the Idmodels, make query to database
   *  This is needed for packagers to see ategory name, product name, product descriptions, and product image.
   */
  private List<PackageingModel> getPackagingList(List<PackageingModel> idModels) {
    // From the Idmodels, make query to database
    // This is needed for packagers to see ategory name, product name, product descriptions, and product image.
    List<PackageingModel> packageingList = new ArrayList<>(idModels.size());
    for (PackageingModel idModel : idModels) {
      Product product = wicTransactionManager.findProductById(idModel.getProductId());
      idModel.setProduct(product);
    }
    return null;
  }

  private void log(String mesg) {
    System.out.println("***********************************");
    System.out.print("*");
    System.out.print(mesg);
    System.out.println("*");
    System.out.println("***********************************");
  }

  public void processNotInStockOrder() {

  }

  public void processNotInDatabaseOrder() {

  }

  public void processMixedOrder() {

  }


// -- EntityManager Operations
//
//  @Test
//  public void testEntityManager() {
//    Category category = wicEntityManasger.find(Category.class, categoryId);
//    log(category.getName() + " is found entityManager");
//  }

}
