package bettercare.wic.service.order;

import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.exceptions.InvalidVoucherException;
import bettercare.wic.service.manual.InitSetup;
import org.junit.Test;


public class OrderSimulator extends InitSetup {

    @Test
    public void saveWicOrder() throws InvalidVoucherException {
        WicOrderRepresentation model = getModel();
        saveWicOrderService.saveWicOrder(model);
    }
}