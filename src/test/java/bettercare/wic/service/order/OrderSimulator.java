package bettercare.wic.service.order;

import bettercare.wic.dal.entity.*;
import bettercare.wic.service.common.InitSetup;
import bettercare.wic.service.common.OrderStatus;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

public class OrderSimulator extends InitSetup {

  @Test
  public void processOrder() {

    // Customer sends in string format
    String orderResponse = createOrderString();

    // front-end compose all orders in jspn and sends it to server back-end
    JsonNode tree = getRootNode(orderResponse);

    // Create detached objects: Customer, Voucher, product contents.

    // back-end end-point parse customer
    Customer customer = readObject(Customer.class, tree);
    if(customer == null) {
      wicLogger.log("Customer is null.");
      return;
    }

    // back-end end-point parse voucher
    Voucher voucher = readObject(Voucher.class, tree);
    if(voucher == null) {
      wicLogger.log("Voucher is null.");
      return;
    }

    // TODO validate this
    if(!isVoucherValid(voucher.getStartDate(), voucher.getExpirationDate())) {
      wicLogger.log("Voucher is valie between %s and %s. Today is %s");
      return;
    }

    // back-end end-point parse order-contents
    String products = tree.get("products").asText();
    wicLogger.log("product content is null.");
    if(products == null) {
      return;
    }

    // save only new customer
    String customerQuery = String.format("select * from customer where wic_number = '%s' and phone = '%s' and  address = '%s' and name = '%s' limit 1",
        customer.getWicNumber(), customer.getPhone(), customer.getAddress(), customer.getName());
    customer = wicEntityManasger.findByNativeQuery(customerQuery, Customer.class);
    if(customer == null) {
      customer = wicTransactionManager.saveOrUpdateCustomer(customer);
    }
    else{
      wicLogger.log("Same customer already exist. Using existing customer: " + customer.toString());
    }

    // if this customer is valid, save vouch entity
    if(customer.getId() > 0) {
      voucher.setCustomerId(customer.getId());
      String voucherQuery = String.format("select * from voucher where voucher_number = '%s' and customer_id = '%s'",
          voucher.getVoucherNumber(), voucher.getCustomerId());
      voucher = wicEntityManasger.findByNativeQuery(voucherQuery, Voucher.class);
      if(voucher == null) {
        voucher = wicTransactionManager.saveOrUpdateVoucher(voucher);
      }
      else {
        wicLogger.log("this voucher is already used by the same customer: " + voucher.toString());
      }
    }

    // if voucher is valid, save order
    if(voucher.getId() > 0) {
      WicOrder wicOrder = saveWicOrderData(products, false, new Date().getTime(), voucher);
      wicLogger.log("Your order number is " + wicOrder.getId());
    }
  }

  private boolean isVoucherValid(long startDate, long expirationDate) {
    long today = new Date().getTime();
    return today >= startDate && today <= expirationDate;
  }

  private <T> T readObject(Class<T> claz, JsonNode tree) {
    JsonNode node = tree.get(claz.getSimpleName().toLowerCase());
    try {
      return objectMapper.readerFor(claz).readValue(node);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private WicOrder saveWicOrderData(String products, boolean isEmergency, long orderTime, Voucher voucher) {
    WicOrder wicOrder = new WicOrder(isEmergency, orderTime, products, OrderStatus.ORDER_RECEIVED.name(), voucher);
    wicLogger.log("Saving a voucher info:" + wicOrder.toString());
    return wicTransactionManager.saveOrUpdateWicOrder(wicOrder);
  }
}