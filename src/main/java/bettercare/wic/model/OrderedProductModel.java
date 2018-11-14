package bettercare.wic.model;

import bettercare.wic.dal.entity.Product;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


public class OrderedProductModel implements Serializable {

    /**
     * serialver -classpath . bettercare.wic.model.OrderedProduct
     */
    private static final long serialVersionUID = -2059597609962677987L;

    @Embedded
    private Product product;
    private int count;

    public OrderedProductModel() {

    }

    public OrderedProductModel(Product p, int count) {
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
