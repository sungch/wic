package bettercare.wic.service.order;

import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Delivery;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.OrderStatus;
import bettercare.wic.service.manual.InitSetup;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
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
public class DeliverySimulator extends InitSetup {

    @Test
    public void delivery() throws InterruptedException {
        List<WicOrder> orders = entityService.findOrderByStatus(OrderStatus.PACKAGING_COMPLETED.name());
        for(WicOrder order : orders) {

            // print address
            Voucher voucher = order.getVoucher();
            Customer customer = voucher.getCustomer();
            wicLogger.info("\nDelivery Address;" + customer.toString(), DeliverySimulator.class);

            // Saving deliverer name, delievery start time, status to delivering.
            // User may check the order status. Update statue before delivery starts
            Delivery delivery = order.getDelivery();
            delivery.setDelivererName("Chulkee Sung");
            delivery.setDeliveryStartTime(new Timestamp(System.currentTimeMillis()));
            order.setStatus(OrderStatus.DELIVERY_ON_THE_WAY.name());
            entityService.saveOrUpdate(WicOrder.class, order);

            // Start Delivery. Dellivery takes 5 secods
            WicOrder order2 = entityService.findById(WicOrder.class, order.getId());
            Thread.sleep(5000);
            Delivery delivery2 = order2.getDelivery();
            delivery2.setDeliveryCompletionTime(new Timestamp(System.currentTimeMillis()));
            order2.setStatus(OrderStatus.DELIVERY_COMPLETED.name());
            order2.setDelivery(delivery2);
            entityService.saveOrUpdate(WicOrder.class, order2);
            // Ended Delivery
        }
    }
}