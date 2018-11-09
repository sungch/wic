package bettercare.wic.service.order;

import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Delivery;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.service.OrderStatus;
import bettercare.wic.service.manual.InitSetup;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;


/**
 * In Packaging area,
 * Upon completion of packaging, Orders statis changes to PACKAGING_COMPLETED.
 *
 * In Delivery area, On regular basis rad wic_order.
 * In wic_order, if PACKAGING_COMPLETED, get voucher_id.
 * From the voucher_id, read voucher and get customer_id from voucher.
 * From customer_id, rad customer to print delivery address.
 *
 */
public class PackagingSimulator extends InitSetup {

    @Test
    public void packaging() throws InterruptedException {
        List<WicOrder> orders = entityService.findOrderByStatus(OrderStatus.ORDER_RECEIVED.name());
        for(WicOrder order : orders) {
            // Started Packaging
            order.setStatus(OrderStatus.PACKAGING.name());
            entityService.saveOrUpdate(WicOrder.class, order);
            Thread.sleep(5000);
            order.setStatus(OrderStatus.PACKAGING_COMPLETED.name());
            entityService.saveOrUpdate(WicOrder.class, order);
            // Ended Packaging. It took 5 seconds.

            // Prepare for delivery
            entityService.saveOrUpdate(Delivery.class, new Delivery(1, order));
        }
    }
}