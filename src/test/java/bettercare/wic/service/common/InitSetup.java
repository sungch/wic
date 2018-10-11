package bettercare.wic.service.common;

import bettercare.wic.app.WicApplication;
import bettercare.wic.service.WicEntityManager;
import bettercare.wic.service.WicTransactionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

// Use @Transactional to roll back at the end of the test.

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class InitSetup {

  @Resource
  protected WicTransactionManager wicTransactionManager;
  @Resource protected WicEntityManager wicEntityManasger;
  protected String CATEGORY_PROD_DELIMITER = ":";
  protected WicLogger wicLogger = new WicLogger();

  // I am stocking products under this category name and category id
  protected String categoryName = "category_milk";

  // Change this according to real IDs in respective table
  protected long productId = 2; // Use this to update product of this Id
  protected long categoryId = 1; // Use this to create a product

  // this is image name for above product
  protected String imageName = "img011";


  @Test
  public void contextLoads() {
  }

  @Before
  public void setup() {
    Assert.assertNotNull(wicTransactionManager);
    Assert.assertNotNull(wicEntityManasger);
  }


}
