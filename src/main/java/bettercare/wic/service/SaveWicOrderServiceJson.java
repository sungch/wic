package bettercare.wic.service;

import bettercare.wic.dal.em.WicEntityManager;
import bettercare.wic.dal.WicLogger;
import bettercare.wic.dal.em.WicTransactionManager;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.service.supports.OrderStatus;
import bettercare.wic.service.supports.TimeTrimmer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

@Service
public class SaveWicOrderServiceJson {

    @Resource
    private WicTransactionManager wicTransactionManager;
    @Resource
    private WicEntityManager wicEntityManasger;
    @Resource
    private WicLogger wicLogger;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private TimeTrimmer timeTrimmer;

    public WicOrder saveWicOrderJson(JsonNode tree) {

        String products = tree.get("products").asText();
        if (products == null) {
            wicLogger.error("product content is empty.", Product.class);
            return null;
        }

        Customer customer = readObject(Customer.class, tree);
        if (customer == null) {
            wicLogger.error("Customer not read.", Customer.class);
            return null;
        }
        Customer persistedCustomer = persistCustomerIfNew(customer);

        Voucher voucher = getVoucher(tree, persistedCustomer.getId());
        if (voucher == null) {
            wicLogger.error("Voucher is invalid.", Voucher.class);
            return null;
        }
        Voucher persistedVoucher = wicTransactionManager.saveOrUpdateVoucher(voucher);

        WicOrder wicOrder = saveWicOrderData(products, false, new Date().getTime(), persistedVoucher);
        wicLogger.info("Your order number is " + wicOrder.getId(), Customer.class);

        return wicOrder;
    }

    private Voucher getVoucher(JsonNode tree, long customerId) {
        Voucher voucher = readObject(Voucher.class, tree);
        if (voucher != null) {
            normalizeVoucherEffectiveDates(voucher);
            if (isVoucherDateValid(voucher.getStartDate(), voucher.getExpirationDate())) {
                if (isNewVoucher(voucher, customerId)) {
                    wicLogger.info("This is a new voucher: " + voucher.toString(), Voucher.class);
                    return voucher;
                }
                else {
                    wicLogger.error("Cannot use the same voucher more than once: " + voucher.toString(), Voucher.class);
                }
            }
            else {
                wicLogger.error("Voucher date is valid between %s and %s. Today is %s", Voucher.class);
            }
        }
        return null;
    }

    private void normalizeVoucherEffectiveDates(Voucher voucher) {
        voucher.setStartDate(timeTrimmer.adjustStartingTime(voucher.getStartDate()));
        voucher.setExpirationDate(timeTrimmer.adjustExpiringTime(voucher.getExpirationDate()));
    }

    // if this customer is valid, save vouch entity
    private boolean isNewVoucher(Voucher voucher, long customerId) {
        voucher.setCustomerId(customerId);
        String voucherQuery = String.format("select * from voucher where voucher_number = '%s' and customer_id = '%s' limit 1", voucher.getVoucherNumber(), voucher.getCustomerId());
        return wicEntityManasger.findByNativeQuery(voucherQuery, Voucher.class) == null;
    }

    // save customer if new
    private Customer persistCustomerIfNew(Customer customer) {
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
        wicLogger.info("Saving a voucher info:" + wicOrder.toString(), WicOrder.class);
        return wicTransactionManager.saveOrUpdateWicOrder(wicOrder);
    }

}