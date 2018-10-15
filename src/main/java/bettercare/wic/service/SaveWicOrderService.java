package bettercare.wic.service;

import bettercare.wic.dal.WicEntityManager;
import bettercare.wic.dal.WicTransactionManager;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.service.config.TimeTrimmer;
import bettercare.wic.service.config.WicLogger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

@Service
public class SaveWicOrderService {

    @Resource
    private WicTransactionManager wicTransactionManager;
    @Resource
    private WicEntityManager wicEntityManasger;
    @Resource
    private WicLogger wicLogger;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    public TimeTrimmer timeTrimmer;

    public WicOrder saveWicOrder(JsonNode tree) {

        // Create detached objects from customer order: Customer, Voucher, product contents.
        // back-end end-point parse customer
        Customer customer = readObject(Customer.class, tree);

        if (customer == null) {
            wicLogger.log("Customer is null.");
            return null;
        }

        // back-end end-point parse voucher
        Voucher voucher = readObject(Voucher.class, tree);
        if (voucher == null) {
            wicLogger.log("Voucher is null.");
            return null;
        }

        normalizeVoucherTime(voucher);

        if (!isVoucherDateValid(voucher.getStartDate(), voucher.getExpirationDate())) {
            wicLogger.log("Voucher date is not valid: between %s and %s. Today is %s");
            return null;
        }

        // back-end end-point parse order-contents
        String products = tree.get("products").asText();
        if (products == null) {
            wicLogger.log("product content is null.");
            return null;
        }

        Customer persistedCustomer = persistNewCustomer(customer);

        Voucher newVoucher;
        if (isThisVoucherUsedBefore(voucher, persistedCustomer.getId())) {
            wicLogger.log("Cannot use the same voucher more than once: " + voucher.toString());
            return null;
        }

        newVoucher = wicTransactionManager.saveOrUpdateVoucher(voucher);
        wicLogger.log("New voucher is saved: " + newVoucher.toString());

        WicOrder wicOrder = saveWicOrderData(products, false, new Date().getTime(), newVoucher);
        wicLogger.log("Your order number is " + wicOrder.getId());

        return wicOrder;
    }

    private void normalizeVoucherTime(Voucher voucher) {
        voucher.setStartDate(timeTrimmer.adjustStartingTime(voucher.getStartDate()));
        voucher.setExpirationDate(timeTrimmer.adjustExpiringTime(voucher.getExpirationDate()));
    }

    // if this customer is valid, save vouch entity
    private boolean isThisVoucherUsedBefore(Voucher voucher, long customerId) {
        voucher.setCustomerId(customerId);
        String voucherQuery = String.format("select * from voucher where voucher_number = '%s' and customer_id = '%s' limit 1", voucher.getVoucherNumber(), voucher.getCustomerId());
        return wicEntityManasger.findByNativeQuery(voucherQuery, Voucher.class) != null;
    }

    // save customer if new
    private Customer persistNewCustomer(Customer customer) {
        String customerQuery = String.format("select * from customer where wic_number = '%s' and phone = '%s' and  address = '%s' and name = '%s' limit 1", customer.getWicNumber(), customer.getPhone(), customer.getAddress(), customer.getName());
        Customer persistedCustomer = wicEntityManasger.findByNativeQuery(customerQuery, Customer.class);
        if (persistedCustomer == null) {
            persistedCustomer = wicTransactionManager.saveOrUpdateCustomer(customer);
        }
        else {
            wicLogger.log("Same customer already exist. Using existing customer: " + persistedCustomer.toString());
        }
        return persistedCustomer;
    }

    private boolean isVoucherDateValid(long startDate, long expirationDate) {
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