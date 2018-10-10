package bettercare.wic.service;

import bettercare.wic.app.WicApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class DeliverySimulator {

  @Resource private WicTransactionManager wicTransactionManager;
  @Resource private WicEntityManager wicEntityManasger;

  @Test
  public void contextLoads() {
  }

  @Before
  public void setup() {
    Assert.assertNotNull(wicTransactionManager);
    Assert.assertNotNull(wicEntityManasger);
  }
  
}
