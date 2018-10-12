package bettercare.wic.service.common;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.service.WicEntityManager;
import bettercare.wic.service.WicTransactionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

  @Resource
  protected WicTransactionManager wicTransactionManager;
  @Resource protected WicEntityManager wicEntityManasger;
  protected String PROD_QUANTITY_DELIMITER = ":";
  protected String ITEM_DELIMITER = "&";
  protected WicLogger wicLogger = new WicLogger();

  // I am stocking products under this category name and category id
  protected String categoryName = "category_milk";

  // Change this according to real IDs in respective table
  protected long productId = 1; // Use this to update product of this Id
  protected long categoryId = 1; // Use this to create a product
  protected String imageName = "img001"; // this is image name for above product

  private ObjectMapper objectMapper;

  @Test
  public void contextLoads() {
  }


  @Before
  public void setup() {
    Assert.assertNotNull(wicTransactionManager);
    Assert.assertNotNull(wicEntityManasger);
    objectMapper = new ObjectMapper();
  }

  protected String createOrderSimulationDataFromBrowser() {
    // customer info from incoming order
    ObjectNode customerNode = objectMapper.createObjectNode();
    customerNode.put("wicNumber", "12hwewekh2323");
    customerNode.put("name", "customer_1");
    customerNode.put("address", "5122 woodsmere lane, herriman, UT 84096");
    customerNode.put("phone", "801-809-0915");

    // product and quantity info from the incoming order
    // ':' separates product-id,quantity.
    // '&' separates one product-order from another
    // This is done by front-end
    ObjectNode categoryProductQuantityNode = objectMapper.createObjectNode();
    categoryProductQuantityNode.put("categoryProductQuantity", "2:100&1:50&3:20&4:1&5:10");

    // voucher info from the incoming order
    ObjectNode voucherObject = objectMapper.createObjectNode();
    voucherObject.put("startDate", new Date().toString());
    voucherObject.put("exoirationDate", new Date().toString());
    voucherObject.put("voucherNumber", "hifh23heiuh23hredfi");

    ObjectNode rootNode = objectMapper.createObjectNode();

    rootNode.set(getFieldName(Customer.class), customerNode);
    rootNode.set(getFieldName(Voucher.class), voucherObject);
    rootNode.set("categoryProductQuantity", categoryProductQuantityNode);

    try {
      return objectMapper.writeValueAsString(rootNode);
    }
    catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getFieldName(Class clz) {
    return clz.getSimpleName().toLowerCase();
  }


}
