package bettercare.wic.service;

import bettercare.wic.model.PackagingModel;
import bettercare.wic.model.OrderedProduct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import bettercare.wic.dal.WicLogger;
import bettercare.wic.dal.entity.Product;

@Component
public class ProductsParser {

    @Resource private WicLogger wicLogger;
    @Resource private EntityService entityService;

    private static final String PROD_SEPARATOR = "&";
    private static final String ITEM_COUNT_SEPARATOR = ":";

    public PackagingModel parseProducts(String products) {

        PackagingModel model = new PackagingModel();

        if(products.indexOf(PROD_SEPARATOR) > 0) {
            wicLogger.error("PROD_SEPARATOR is missing from this order:" + products, PackagingModel.class);
            return model;
        }

        String[] orders = products.split(PROD_SEPARATOR);

        for(String order : orders) {

            if(order.indexOf(ITEM_COUNT_SEPARATOR) < 0) {
                wicLogger.error("ITEM_COUNT_SEPARATOR is missing from this order-item", PackagingModel.class);
                continue;
            }

            String[] kv = order.split(ITEM_COUNT_SEPARATOR);

            if(kv == null || kv.length!= 2) {
                wicLogger.error("Either product id or order counter is not valid:" + order, PackagingModel.class);
                continue;
            }

            long productId = Long.valueOf(kv[0]);
            int orderCount = Integer.valueOf(kv[1]);
            Product product = entityService.findById(Product.class, Long.valueOf(productId));
            OrderedProduct orderedProduct = new OrderedProduct(product, orderCount);

            model.addOrderedProduct(orderedProduct);

        }
        
        return model;
    }
}
