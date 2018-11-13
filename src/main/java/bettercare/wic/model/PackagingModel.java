package bettercare.wic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;

public class PackagingModel implements Serializable {

    /**
     * serialver -classpath . bettercare.wic.model.OrderedProductsModel
     */
    private static final long serialVersionUID = 1425377164454743913L;

    @Embedded
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    @Embedded
    public List<OrderedProduct> getOrderedProducts() {
        return this.orderedProducts;
    }

    public void addOrderedProduct(OrderedProduct orderedProduct) {
        this.orderedProducts.add(orderedProduct);
    }

    public void removeOrderedProduct(OrderedProduct orderedProduct) {
        if(this.orderedProducts.contains(orderedProduct)) {
            this.orderedProducts.remove(orderedProduct);
        }
    }

}
