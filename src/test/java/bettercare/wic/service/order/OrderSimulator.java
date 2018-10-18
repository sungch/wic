package bettercare.wic.service.order;

import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.OrderStatus;
import bettercare.wic.service.common.InitSetup;
import bettercare.wic.service.config.WicLogger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class OrderSimulator extends InitSetup {

    @Resource private WicLogger wicLogger;

    @Test
    public void saveWicOrder() {
        WicOrderRepresentation model = getModel();
        WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
        wicLogger.log(wicOrder.toString());
        Assert.assertTrue(wicOrder != null);
        Assert.assertTrue(wicOrder.getStatus().equals(OrderStatus.ORDER_RECEIVED.name()));
    }
}