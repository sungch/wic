package bettercare.wic.service;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.dal.entity.Voucher;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.service.utils.OrderStatus;
import bettercare.wic.service.utils.PackageingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class PackageSimulator {

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
