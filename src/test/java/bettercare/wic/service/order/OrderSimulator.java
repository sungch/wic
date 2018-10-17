package bettercare.wic.service.order;

import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.service.OrderStatus;
import bettercare.wic.service.common.InitSetup;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Assert;
import org.junit.Test;

public class OrderSimulator extends InitSetup {

  @Test
  public void saveWicOrder() {
    String orderResponse = createOrderString(); // front end compose this way
    JsonNode tree = getRootNode(orderResponse); // compose json from the response data
    WicOrder wicOrder = saveWicOrderServiceJson.saveWicOrderJson(tree);
    Assert.assertTrue(wicOrder != null);
    Assert.assertTrue(wicOrder.getStatus().equals(OrderStatus.ORDER_RECEIVED.name()));
  }
}