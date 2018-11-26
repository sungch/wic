package bettercare.wic.service.order;

import bettercare.wic.dal.entity.Product;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.CustomerModel;
import bettercare.wic.model.VoucherModel;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.exceptions.InvalidVoucherException;
import bettercare.wic.service.manual.InitSetup;
import bettercare.wic.utils.WicTimeUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderSimulator extends InitSetup {

    @Resource private WicTimeUtils wicTimeUtils;

    private String wicNumber = "customer_wic_number_" + String.valueOf(new Random(System.currentTimeMillis()).nextInt());
    private String customerName = "customer_" + String.valueOf(new Random(System.currentTimeMillis()).nextInt());

    @Test
    public void saveWicOrder() throws InvalidVoucherException {

        // browser simulation
        WicOrderRepresentation payload = getCustomerOrder();

        //  run time
        WicOrder wicOrder = saveWicOrderService.saveWicOrder(payload);

        // Display result to Customer
        System.out.println(wicOrder.toString());
    }

    private WicOrderRepresentation getCustomerOrder() {

        WicOrderRepresentation payload = new WicOrderRepresentation();

        // Customer
        String address = "5122 woodsmere lane, herriman, UT 84096";
        String phone = "801-809-0915";
        payload.setCustomerModel(new CustomerModel(address, customerName, phone, wicNumber));

        // Voucher:
        payload.setVoucherModel(new VoucherModel(getStartTime(), getExpirationTime(), getVoucherNumber()));

        // Product
        payload.setProducts(createSimulatedProductOrders());

        return payload;

    }

    private String getVoucherNumber() {
        return "voucherNum_" + String.valueOf(new Random(System.currentTimeMillis()).nextInt());
    }

    // In lcient, apply UTC conversion.
    // In test, its timezone is same as server. Skip conversion.
    private Timestamp getStartTime() {
        return new Timestamp((new Date().getTime()) - oneHour());
//        return wicTimeUtils.toUtcTime();
    }

    private long oneHour() {
        return 1000 * 60 * 60;
    }

    private Timestamp getExpirationTime() {
        return new Timestamp((new Date().getTime()) + oneHour());
//        return wicTimeUtils.toUtcTime(new Timestamp((new Date().getTime()) + oneHour()));
    }

    private String createSimulatedProductOrders() {
        StringBuilder products = new StringBuilder();
        String ITEM_DELIMITER = "&";
        String PROD_QUANTITY_DELIMITER = ":";

        List<Product> productList = entityService.findAll(Product.class);

        for (Product product : productList) {
            String orderQuantity = String.valueOf((int) (quantity + product.getId()));
            products.append(product.getId()).append(PROD_QUANTITY_DELIMITER).append(orderQuantity).append(ITEM_DELIMITER);
        }
        products.deleteCharAt(products.lastIndexOf(ITEM_DELIMITER));
        return products.toString();
    }


}