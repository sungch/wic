package bettercare.wic.service.order;

import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.exceptions.InvalidVoucherException;
import bettercare.wic.service.OrderStatus;
import bettercare.wic.service.manual.InitSetup;
import org.junit.Assert;
import org.junit.Test;


public class OrderSimulator extends InitSetup {

    /**
     * Postman
     *
     * Create an order:
     *
     * POST http://localhost:8080/customerOrder
     *
     * {
     *     // Save in wic_order
     *     "products": "1:11&2:12&3:13&4:14&5:15",
     *
     *     // Save in Customer
     *     "customerModel" : {
     *         "address": "1-1234 my address 567",
     *         "name": "2-Johny",
     *         "phone": "801-111-1111",
     *         "wicNumber": "3-my-wic-number-1234"
     *     },
     *
     *     // Save in Voucher
     *     "voucherModel": {
     *     "expirationDate": "2018-11-23 00:29:50.774",
     *     "startDate": "2018-11-11 11:0:0.0",
     *     "voucherNumber": "7-1234-voucher-num-567"
     *     }
     * }
     *
     *
     * Edit wic_order
     *
     * GET http://localhost:8080/wicOrders
     * PUT http://localhost:8080/wicOrder
     *
     * {
     *     "id": 2,
     *     "orderedTime": "2018-11-15T19:00:13.000+0000",
     *     "products": "1:11&2:12&3:13&4:14&5:15&6:16&7:17&8:18&9:19&10:20&11:21&12:22&13:23&14:24&15:25&16:26&17:27&18:28&19:29&20:30&21:31&22:32&23:33&24:34&25:35&26:36&27:37&28:38&29:39&30:40&31:41&32:42&33:43&34:44&35:45&36:46&37:47&38:48",
     *     "status": "PACKAGING",
     *     "emergency": true
     * }
     *
     */
    @Test
    public void saveWicOrder() throws InvalidVoucherException {
        WicOrderRepresentation model = getModel();
        WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);

        wicLogger.log(wicOrder.toString());
        Assert.assertEquals(wicOrder.getStatus(), OrderStatus.ORDER_RECEIVED.name());
        wicLogger.log(" Order:" + wicOrder.toString());
    }
}