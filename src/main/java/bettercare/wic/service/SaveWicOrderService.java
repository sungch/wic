package bettercare.wic.service;

import bettercare.wic.dal.WicLogger;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.exceptions.InvalidVoucherException;
import bettercare.wic.model.WicOrderRepresentation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SaveWicOrderService {

    @Resource
    private EntityService entityService;
    @Resource
    private WicLogger wicLogger;
    @Resource
    private WicTimeUtils wicTimeUtils;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public SaveWicOrderService() {
        super();
    }

    public WicOrder saveWicOrder(WicOrderRepresentation model) throws InvalidVoucherException {
        Customer customer = persistCustomerIfNew(new Customer(model.getCustomerModel()));
        Voucher transientVoucher = new Voucher(model.getVoucherModel(), customer);
        if (isNewVoucher(transientVoucher)) {
            truncateTime(transientVoucher);
            Voucher voucher = entityService.saveOrUpdate(Voucher.class, transientVoucher);
            WicOrder wicOrder = saveWicOrderData(model.getProducts(), model.isHasMissingProduct(), voucher);
            wicLogger.info("Your order number is " + wicOrder.getId(), Customer.class);
            return wicOrder;
        }
        throw new InvalidVoucherException("There is a problem with the voucher:" + model.getVoucherModel().toString());
    }

    private void truncateTime(Voucher voucher) {
        voucher.setStartDate(wicTimeUtils.truncateHours(voucher.getStartDate()));
        voucher.setExpirationDate(wicTimeUtils.addFullHours(voucher.getExpirationDate()));
    }

    private boolean isNewVoucher(Voucher voucher) {
        List<Voucher> vouchers = entityService.findVoucherByVoucherNumber(voucher.getVoucherNumber());
        return vouchers.isEmpty();
    }

    private Customer persistCustomerIfNew(Customer customer) {
        List<Customer> list = entityService.findCustomerByWicNumberAndPhoneAndAddressAndName(customer.getWicNumber(), customer.getPhone(), customer.getAddress(), customer.getName());
        if (list.isEmpty()) {
            return entityService.saveOrUpdate(Customer.class, customer);
        }
        else {
            wicLogger.info("Same customer already exist. returning the existing customer: " + customer.toString(), this.getClass());
        }
        return list.get(0);
    }

    private WicOrder saveWicOrderData(String products, boolean hasMissingProduct, Voucher voucher) {
        WicOrder wicOrder = new WicOrder(hasMissingProduct, products, OrderStatus.ORDER_RECEIVED.name(), voucher);
        wicLogger.info("Save or update a wicOrder info:" + wicOrder.toString(), WicOrder.class);
        return entityService.saveOrUpdate(WicOrder.class, wicOrder);
    }

}