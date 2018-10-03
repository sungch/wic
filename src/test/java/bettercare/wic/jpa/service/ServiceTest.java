package bettercare.wic.jpa.service;

import bettercare.wic.jpa.model.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ServiceTest {

  @Test
  public void testCategoryCreate() {
    CategoryService service = new CategoryService();
    service.create(createProducts(), "milk", "0");
  }

  private List<Product> createProducts() {
    List<Product> products = new ArrayList<>(10);
    Product p = new Product();
    p.setBarcode("1234");
    p.setDescription("description here");
    p.setImageId("img1");
    p.setName("whole milk");
    products.add(p);
    return products;
  }
}
