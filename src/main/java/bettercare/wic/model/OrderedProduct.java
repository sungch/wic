package bettercare.wic.model;

import bettercare.wic.dal.entity.Product;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class OrderedProduct implements Serializable {

    /**
     * serialver -classpath . bettercare.wic.model.OrderedProductsModel
     */
    // private static final long serialVersionUID = 1425377164454743913L;

    private Product product;
    private int count;

    public OrderedProduct() {

    }

    public OrderedProduct(Product p, int count) {
        this.product = p;
        this.count = count;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
