package bettercare.wic.service;

import bettercare.wic.dal.WicEntityManager;
import bettercare.wic.dal.WicLogger;
import bettercare.wic.dal.WicTransactionManager;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.WicOrderRepresentation;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private TimeTrimmer timeTrimmer;

    public WicOrder saveWicOrder(WicOrderRepresentation model) {

        String products = model.getProducts();
        if (isBlank(products)) {
            wicLogger.error("product content is empty.", Product.class);
            return null;
        }

        if(isBlank(model.getWicNumber(), model.getName(), model.getPhone(), model.getAddress())) {
            wicLogger.error("Customer not read.", Customer.class);
            return null;
        }
        Customer customer = persistCustomerIfNew(new Customer(model.getWicNumber(), model.getName(), model.getPhone(), model.getAddress()));

        if(isBlank(model.getVoucherNumber()) || !isVoucherDateValid(model.getStartDate(), model.getExpirationDate())) {
            wicLogger.error("Voucher is invalid.", Voucher.class);
            return null;
        }

        Voucher voucher_ = new Voucher(model.getStartDate(), model.getExpirationDate(), model.getVoucherNumber(), customer.getId());
        if(isNewVoucher(voucher_, customer.getId())) {
            normalizeVoucherEffectiveDates(voucher_);
            if(isVoucherDateValid(voucher_.getStartDate(), voucher_.getExpirationDate())) {
                Voucher voucher = wicTransactionManager.saveOrUpdateVoucher(voucher_);
                WicOrder wicOrder = saveWicOrderData(products, false, new Date().getTime(), voucher);
                wicLogger.info("Your order number is " + wicOrder.getId(), Customer.class);
                return wicOrder;
            }
            else {
                wicLogger.error("Voucher date is invalid. ", Voucher.class);
            }
        }
        else {
            wicLogger.info("The same voucher cannot be sued again.", Voucher.class);
        }
        return null;
    }

    private boolean isBlank(String... data) {
        for(String d : data) {
            if(d == null || d.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void normalizeVoucherEffectiveDates(Voucher voucher) {
        voucher.setStartDate(timeTrimmer.adjustStartingTime(voucher.getStartDate()));
        voucher.setExpirationDate(timeTrimmer.adjustExpiringTime(voucher.getExpirationDate()));
    }

    // if this customer is valid, save vouch entity
    private boolean isNewVoucher(Voucher voucher, long customerId) {
        if(voucher.getId() == 0) {
           return true;
        }
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

    private WicOrder saveWicOrderData(String products, boolean isEmergency, long orderTime, Voucher voucher) {
        WicOrder wicOrder = new WicOrder(isEmergency, orderTime, products, OrderStatus.ORDER_RECEIVED.name(), voucher);
        wicLogger.info("Saving a voucher info:" + wicOrder.toString(), WicOrder.class);
        return wicTransactionManager.saveOrUpdateWicOrder(wicOrder);
    }

}