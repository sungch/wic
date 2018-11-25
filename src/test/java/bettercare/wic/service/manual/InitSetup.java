package bettercare.wic.service.manual;

import bettercare.wic.config.WicApplication;
import bettercare.wic.dal.entity.Category;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.utils.WicLogger;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.ProductsParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class InitSetup {

  @Resource protected EntityService entityService;
  @Resource protected WicLogger wicLogger;
  @Resource protected SaveWicOrderService saveWicOrderService;
  @Resource protected ProductsParser productsParser;

  // Use this property ONLY to create categories.
  static final String[] categoryNames = {"Infant Cereal", "Baby Food", "Baby Food Meat", "Infant Formula",
          "Milk", "Cheese", "Yogurt", "Eggs", "Juice", "Vegetables & Fruits", "Beans & Lentils", "Peanut Butter",
          "Canned Fish", "Cereal", "Hot Cereal", "Whole Wheat Bread", "Brown Rice", "Whole Wheat Pasta", "Tortillas"};

  // Use this for all other purposes.
  List<Category> categories;

  long startProductId = 1; // Use this to create order and product
  int numOfProductsToCreatePerCategory = 2;
  protected long quantity = 3;


  @Test
  public void contextLoads() {
  }

  @Before
  public void setup() {
    Assert.assertNotNull(entityService);
    categories = entityService.findAll(Category.class);
  }

}
