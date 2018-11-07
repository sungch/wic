package bettercare.wic.service.manual;

import bettercare.wic.config.WicApplication;
import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.model.CustomerModel;
import bettercare.wic.model.VoucherModel;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.dal.WicLogger;
import bettercare.wic.service.EntityService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class InitSetup {

  @Resource protected EntityService entityService;
  @Resource protected WicLogger wicLogger;
  @Resource protected SaveWicOrderService saveWicOrderService;

  // Use this property ONLY to create categories.
  protected static final String[] categoryNames = {"Infant Cereal", "Baby Food", "Baby Food Meat", "Infant Formula",
          "Milk", "Cheese", "Yogurt", "Eggs", "Juice", "Vegetables & Fruits", "Beans & Lentils", "Peanut Butter",
          "Canned Fish", "Cereal", "Hot Cereal", "Whole Wheat Bread", "Brown Rice", "Whole Wheat Pasta", "Tortillas"};

  // Use this for all other purposes.
  protected List<Category> categories;

  private String wicNumber = "customer_wic_number_" + String.valueOf(new Random(System.currentTimeMillis()).nextInt());
  private String customerName = "customer_" + String.valueOf(new Random(System.currentTimeMillis()).nextInt());
  private String address = "5122 woodsmere lane, herriman, UT 84096";
  private String phone = "801-809-0915";
  private long aDay = (24 * 60 * 60 * 1000);
  private long now = new Date().getTime();
  private long startDate = now - aDay;
  private long expirationDate = now + aDay;
  private String voucherNumber = "voucherNum_" + String.valueOf(new Random(System.currentTimeMillis()).nextInt());

  protected String productImageName = "img001"; // to update product image name
  protected String categoryImageName = "img000"; // to update product image name

  protected long startProductId = 1; // Use this to create order and product
  protected int numOfProductsToCreatePerCategory = 2;
  protected long quantity = 10;


  @Test
  public void contextLoads() {
  }

  @Before
  public void setup() {
    Assert.assertNotNull(entityService);
    categories = entityService.findAll(Category.class);
  }

  protected WicOrderRepresentation getModel() {
    WicOrderRepresentation model = new WicOrderRepresentation();

    // Customer
    model.setCustomerModel(new CustomerModel(address, customerName, phone, wicNumber));

    // Voucher
    model.setVoucherModel(new VoucherModel(new Timestamp(startDate), new Timestamp(expirationDate), voucherNumber));

    // Product
    model.setProducts(createSimulatedProductOrders());

    return model;
  }

  private String createSimulatedProductOrders() {
    StringBuilder products = new StringBuilder();
    String ITEM_DELIMITER = "&";
    String PROD_QUANTITY_DELIMITER = ":";

    List<Product> productList = entityService.findAll(Product.class);

    for(Product product : productList) {
      String orderQuantity = String.valueOf((int)(quantity + product.getId()));
      products.append(product.getId()).append(PROD_QUANTITY_DELIMITER).append(orderQuantity).append(ITEM_DELIMITER);
    }
    products.deleteCharAt(products.lastIndexOf(ITEM_DELIMITER));
    return products.toString();
  }

}
