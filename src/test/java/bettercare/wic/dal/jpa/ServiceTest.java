package bettercare.wic.dal.jpa;

import bettercare.wic.dal.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:/config.properties")
@ComponentScan("bettercare.wic")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

  @Test
  public void testCategoryCreate() {
    CategoryJpaService service = new CategoryJpaService();
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
