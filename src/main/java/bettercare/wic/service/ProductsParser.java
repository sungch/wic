package bettercare.wic.service;

import bettercare.wic.model.PackagingOrderedProductRepresentation;
import bettercare.wic.model.OrderedProductModel;
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

    public PackagingOrderedProductRepresentation parseProducts(String products) {

        PackagingOrderedProductRepresentation orderModel = new PackagingOrderedProductRepresentation();

        if(!products.contains(PROD_SEPARATOR)) {
            wicLogger.error("PROD_SEPARATOR is missing from this order:" + products, PackagingOrderedProductRepresentation.class);
            return orderModel;
        }

        String[] orders = products.split(PROD_SEPARATOR);

        for(String order : orders) {

            if(!order.contains(ITEM_COUNT_SEPARATOR)) {
                wicLogger.error("ITEM_COUNT_SEPARATOR is missing from this order-item", PackagingOrderedProductRepresentation.class);
                continue;
            }

            String[] kv = order.split(ITEM_COUNT_SEPARATOR);

            if(kv.length != 2) {
                wicLogger.error("Either product id or order counter is not valid:" + order, PackagingOrderedProductRepresentation.class);
                continue;
            }

            long productId = Long.valueOf(kv[0]);
            int orderCount = Integer.valueOf(kv[1]);
            Product product = entityService.findById(Product.class, productId);

            if(product == null) {
                wicLogger.error("Ordered product item not found:" + product.toString(), ProductsParser.class);
                continue;
            }
            OrderedProductModel orderedProduct = new OrderedProductModel(product, orderCount);
            orderModel.addOrderedProduct(orderedProduct);
        }
        
        return orderModel;
    }
}
