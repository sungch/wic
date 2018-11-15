package bettercare.wic.service;

import bettercare.wic.exceptions.InvalidProductDataException;
import bettercare.wic.model.PackagingOrderedProductRepresentation;
import bettercare.wic.model.OrderedProductModel;
import javax.annotation.Resource;

import com.sun.jersey.api.NotFoundException;
import org.springframework.stereotype.Component;
import bettercare.wic.dal.entity.Product;

@Component
public class ProductsParser {

    @Resource private EntityService entityService;

    private static final String PROD_SEPARATOR = "&";
    private static final String ITEM_COUNT_SEPARATOR = ":";

    public PackagingOrderedProductRepresentation parseProducts(String products) throws InvalidProductDataException {

        if(!products.contains(PROD_SEPARATOR)) {
            throw new InvalidProductDataException("PROD_SEPARATOR is missing from this order:" + products);
        }

        PackagingOrderedProductRepresentation orderModel = new PackagingOrderedProductRepresentation();

        for(String order : products.split(PROD_SEPARATOR)) {

            if(!order.contains(ITEM_COUNT_SEPARATOR)) {
                throw new InvalidProductDataException("ITEM_COUNT_SEPARATOR is missing from this order-item:" + order + " of " + products);
            }

            String[] kv = order.split(ITEM_COUNT_SEPARATOR);

            if(kv.length != 2) {
                throw new InvalidProductDataException("Either product id or order counter is not valid:" + order + " of " + products);
            }

            long productId = Long.valueOf(kv[0]);
            int orderCount = Integer.valueOf(kv[1]);
            Product product = entityService.findById(Product.class, productId);

            if(product == null) {
                throw new NotFoundException("Ordered product item not found. productId:" + productId + " orderCount:" + orderCount + " of " + products);
            }

            OrderedProductModel orderedProduct = new OrderedProductModel(product, orderCount);
            orderModel.addOrderedProduct(orderedProduct);
        }
        
        return orderModel;
    }
}
