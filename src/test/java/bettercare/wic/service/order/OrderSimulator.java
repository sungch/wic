package bettercare.wic.service.order;

import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.supports.OrderStatus;
import bettercare.wic.service.common.InitSetup;
import org.junit.Assert;
import org.junit.Test;


public class OrderSimulator extends InitSetup {

    @Test
    public void saveWicOrder() {
        WicOrderRepresentation model = getModel();
        WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
        wicLogger.log(wicOrder.toString());
        Assert.assertEquals(wicOrder.getStatus(), OrderStatus.ORDER_RECEIVED.name());
        wicLogger.log(" Order:" + wicOrder.toString());
    }
}