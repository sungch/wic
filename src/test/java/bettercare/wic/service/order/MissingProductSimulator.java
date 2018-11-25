package bettercare.wic.service.order;

import bettercare.wic.dal.entity.MissingProduct;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.service.manual.InitSetup;
import org.junit.Test;
import java.util.List;

public class MissingProductSimulator extends InitSetup {

    @Test
    public void saveWicOrder() {
        long missingProductId = getMissingProductId();
        List<WicOrder> wicOrders = entityService.findAll(WicOrder.class);
        if(wicOrders.isEmpty()) {
            wicLogger.warn("No outstanding orders.", MissingProduct.class);
        }
        else {
            for (WicOrder wicOrder : wicOrders) { // Add a missing product to each order
                MissingProduct model = createMissingProductModel(wicOrder, missingProductId, 2);
                MissingProduct missingProduct = entityService.saveOrUpdate(MissingProduct.class, model);
                wicLogger.info("Missing products:" + missingProduct.toString(), MissingProduct.class);
            }
        }
    }

    private MissingProduct createMissingProductModel(WicOrder wicOrder, long missingProductId, int count) {
        WicOrder order = new WicOrder();
        order.setId(wicOrder.getId()); // I can send the whole wicOrder or just set id.
        return new MissingProduct(order, missingProductId, count);
    }

    private long getMissingProductId() {
        List<Product> products = entityService.findAll(Product.class);
        return products.get(0).getId();
    }
}