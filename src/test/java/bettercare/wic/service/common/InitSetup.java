package bettercare.wic.service.common;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.WicEntityManager;
import bettercare.wic.dal.WicTransactionManager;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.service.config.WicLogger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.util.Date;

// Use @Transactional to roll back at the end of the test.

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class InitSetup {

  @Resource protected WicTransactionManager wicTransactionManager;
  @Resource protected WicEntityManager wicEntityManasger;
  @Resource protected WicLogger wicLogger;
  @Resource protected SaveWicOrderService saveWicOrderService;

  protected String categoryName = "category_milk";
  protected long[] productIds = {1,2,3,4,5}; // Use this to update product of this Id
  private int[] quantitys = {10, 20, 30, 40, 50}; // quantity of products
  protected long categoryId = 1; // to create a product
  protected String imageName = "img001"; // to update product image name

  @Test
  public void contextLoads() {
  }

  @Before
  public void setup() {
    Assert.assertNotNull(wicTransactionManager);
    Assert.assertNotNull(wicEntityManasger);
  }

  private String wicNumber = "12hwewekh2323";
  private String customerName = "customer_1";
  private String address = "5122 woodsmere lane, herriman, UT 84096";
  private String phone = "801-809-0915";
  private long aDay = (24 * 60 * 60 * 1000);
  private long now = new Date().getTime();

  private long startDate = now - aDay;
  private long expirationDate = now + aDay;
  private String voucherNumber = "hifh23heiuh23hredfi";

  protected WicOrderRepresentation getModel() {
    WicOrderRepresentation model = new WicOrderRepresentation();

    // Customer
    model.setWicNumber(wicNumber);
    model.setName(customerName);
    model.setAddress(address);
    model.setPhone(phone);

    // Voucher
    model.setStartDate(startDate);
    model.setExpirationDate(expirationDate);
    model.setVoucherNumber(voucherNumber);

    // Product
    model.setProducts(createSimulatedProductOrders());

    return model;
  }

  private String createSimulatedProductOrders() {
    StringBuilder products = new StringBuilder();
    String ITEM_DELIMITER = "&";
    for(int i = 0; i < productIds.length; i++) {
      String PROD_QUANTITY_DELIMITER = ":";
      products.append(productIds[i]).append(PROD_QUANTITY_DELIMITER).append(quantitys[i]).append(ITEM_DELIMITER);
    }
    products.deleteCharAt(products.lastIndexOf(ITEM_DELIMITER));
    return products.toString();
  }

}
