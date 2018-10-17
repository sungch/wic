package bettercare.wic.service.common;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.WicEntityManager;
import bettercare.wic.dal.WicTransactionManager;
import bettercare.wic.service.SaveWicOrderServiceJson;
import bettercare.wic.service.config.WicLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

  @Resource protected WicTransactionManager wicTransactionManager;
  @Resource protected WicEntityManager wicEntityManasger;
  @Resource protected WicLogger wicLogger;
  @Resource protected ObjectMapper objectMapper;
  @Resource protected SaveWicOrderServiceJson saveWicOrderServiceJson;


  protected String categoryName = "category_milk";
  protected long[] productIds = {2,3,4,5,6}; // Use this to update product of this Id
  private int[] quantitys = {20, 30, 40, 50, 60}; // quantity of products
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

  protected String createOrderString() {
    // customer info from incoming order
    ObjectNode customerNode = objectMapper.createObjectNode();
    customerNode.put("wicNumber", "12hwewekh2323");
    customerNode.put("name", "customer_1");
    customerNode.put("address", "5122 woodsmere lane, herriman, UT 84096");
    customerNode.put("phone", "801-809-0915");

    // voucher info from the incoming order
    ObjectNode voucherNode = objectMapper.createObjectNode();
    long aDay = (24 * 60 * 60 * 1000);
    long now = new Date().getTime();
    voucherNode.put("startDate", now - aDay);
    voucherNode.put("expirationDate", now + aDay); // 1 day ahead
    voucherNode.put("voucherNumber", "hifh23heiuh23hredfi");

    // return string from object node

    ObjectNode rootNode = this.objectMapper.createObjectNode();

    rootNode.set(getFieldName(Customer.class), customerNode);
    rootNode.set(getFieldName(Voucher.class), voucherNode);
    rootNode.put("products", createSimulatedProductOrders());

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

  private String getFieldName(Class clz) {
    return clz.getSimpleName().toLowerCase();
  }

}
