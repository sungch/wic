package bettercare.wic.service.common;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.service.WicEntityManager;
import bettercare.wic.service.WicTransactionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.io.IOException;
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
  protected long[] productIds = {10,11,12,13,14,15}; // Use this to update product of this Id
  protected int[] quantitys = {1, 2, 3, 4, 5, 6}; // quantity of productId 10 is 1, 11 is 2, ...
  protected long categoryId = 1; // Use this to create a product
  protected String imageName = "img001"; // this is image name for above product

  protected ObjectMapper objectMapper;

  @Test
  public void contextLoads() {
  }


  @Before
  public void setup() {
    Assert.assertNotNull(wicTransactionManager);
    Assert.assertNotNull(wicEntityManasger);
    objectMapper = new ObjectMapper();
  }

  protected String createOrderString() {
    // customer info from incoming order
    ObjectNode customerNode = objectMapper.createObjectNode();
    customerNode.put("wicNumber", "12hwewekh2323");
    customerNode.put("name", "customer_1");
    customerNode.put("address", "5122 woodsmere lane, herriman, UT 84096");
    customerNode.put("phone", "801-809-0915");

    // voucher info from the incoming order
    ObjectNode voucherObject = objectMapper.createObjectNode();
    voucherObject.put("startDate", new Date().toString());
    voucherObject.put("expirationDate", new Date().toString());
    voucherObject.put("voucherNumber", "hifh23heiuh23hredfi");

    // product and quantity info from the incoming order
    // ':' separates product-id,quantity.
    // '&' separates one product-order from another
    // This is done by front-end
    ObjectNode pqNode = objectMapper.createObjectNode();
    pqNode.put("pqs", createSimulatedProductOrders());

    ObjectNode rootNode = this.objectMapper.createObjectNode();

    rootNode.set(getFieldName(Customer.class), customerNode);
    rootNode.set(getFieldName(Voucher.class), voucherObject);
    rootNode.set("categoryProductQuantity", pqNode);

    try {
      return this.objectMapper.writeValueAsString(rootNode);
    }
    catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected JsonNode getRootNode(String orderJsonString) {
    try {
      return this.objectMapper.readTree(orderJsonString);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected JsonNode getBranchNode(JsonNode tree, String key) {
    return tree.get(key);
  }

  private String createSimulatedProductOrders() {
    StringBuilder products = new StringBuilder();
    for(int i = 0; i < productIds.length; i++) {
      products.append(productIds[i]).append(PROD_QUANTITY_DELIMITER).append(quantitys[i]).append(ITEM_DELIMITER);
    }
    products.deleteCharAt(products.lastIndexOf(ITEM_DELIMITER));
    return products.toString();
  }

  private String getFieldName(Class clz) {
    return clz.getSimpleName().toLowerCase();
  }


}
