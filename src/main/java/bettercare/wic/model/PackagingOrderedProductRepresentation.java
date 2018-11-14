package bettercare.wic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;


/**
 * This object is used to model outgoing response to customer to update order status.
 */
public class PackagingOrderedProductRepresentation implements Serializable {

    /**
     * serialver -classpath . bettercare.wic.model.PackagingOrderedProductRepresentation
     */
    private static final long serialVersionUID = 1425377164454743913L;

    private long orderId;
    private List<OrderedProductModel> orderedProducts = new ArrayList<>();


    public List<OrderedProductModel> getOrderedProducts() {
        return this.orderedProducts;
    }

    public void addOrderedProduct(OrderedProductModel orderedProduct) {
        this.orderedProducts.add(orderedProduct);
    }

    public void removeOrderedProduct(OrderedProductModel orderedProduct) {
        if(this.orderedProducts.contains(orderedProduct)) {
            this.orderedProducts.remove(orderedProduct);
        }
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
