package bettercare.wic.service;

import bettercare.wic.dal.WicLogger;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.WicOrderRepresentation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SaveWicOrderService {

    @Resource
    private EntityService entityService;
    @Resource
    private WicLogger wicLogger;
    @Resource
    private TimeTrimmer timeTrimmer;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public SaveWicOrderService() {
        super();
    }

    public WicOrder saveWicOrder(WicOrderRepresentation model) {

        String products = model.getProducts();
        if (isBlank(products)) {
            wicLogger.error("product content is empty.", Product.class);
            return null;
        }

        if(isBlank(model.getWicNumber(), model.getName(), model.getPhone(), model.getAddress())) {
            wicLogger.error("Customer information is misssing.", Customer.class);
            return null;
        }
        Customer customer = persistCustomerIfNew(new Customer(model.getWicNumber(), model.getName(), model.getPhone(), model.getAddress()));

        if(isBlank(model.getVoucherNumber())) {
            wicLogger.error("Voucher number is blank.", Voucher.class);
            return null;
        }

        Voucher voucher_ = new Voucher(model.getStartDate(), model.getExpirationDate(), model.getVoucherNumber(), customer.getId());
        if(isNewVoucher(voucher_, customer.getId())) {
            normalizeVoucherEffectiveDates(voucher_);
            if(isVoucherDateValid(voucher_.getStartDate(), voucher_.getExpirationDate())) {
                Voucher voucher = entityService.saveOrUpdate(Voucher.class, voucher_);
                WicOrder wicOrder = saveWicOrderData(products, false, new Date().getTime(), voucher);
                wicLogger.info("Your order number is " + wicOrder.getId(), Customer.class);
                return wicOrder;
            }
            else {
                wicLogger.error("Voucher date is invalid. ", Voucher.class);
            }
        }
        else {
            wicLogger.info("The same voucher cannot be used again.", Voucher.class);
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

    private boolean isNewVoucher(Voucher voucher, long customerId) {
        voucher.setCustomerId(customerId);
        List<Voucher> vouchers = entityService.findVoucherByVoucherNumberAndCustomerId(voucher.getVoucherNumber(), voucher.getCustomerId());
        return vouchers.isEmpty();
    }

    private Customer persistCustomerIfNew(Customer customer) {
        List<Customer> list = entityService.findCustomerByWicNumberAndPhoneAndAddressAndName(
            customer.getWicNumber(), customer.getPhone(), customer.getAddress(), customer.getName());
        if (list.isEmpty()) {
            return entityService.saveOrUpdate(Customer.class, customer);
        }
        else {
            wicLogger.log("Same customer already exist. I am using the existing customer: " + customer.toString());
        }
        return null;
    }

    private boolean isVoucherDateValid(long startDate, long expirationDate) {
        long today = new Date().getTime();
        return today >= startDate && today <= expirationDate;
    }

    private WicOrder saveWicOrderData(String products, boolean isEmergency, long orderTime, Voucher voucher) {
        WicOrder wicOrder = new WicOrder(isEmergency, orderTime, products, OrderStatus.ORDER_RECEIVED.name(), voucher);
        wicLogger.info("Saving a voucher info:" + wicOrder.toString(), WicOrder.class);
        return entityService.saveOrUpdate(WicOrder.class, wicOrder);
    }

}