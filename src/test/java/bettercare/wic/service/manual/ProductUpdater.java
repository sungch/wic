package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Product;
import bettercare.wic.service.common.InitSetup;
import org.junit.Test;

public class ProductUpdater extends InitSetup {

  @Test
  public void updateProduct() {
    Product product = wicTransactionManager.findProductById(productId);
    if (product == null) {
      wicLogger.log("No product found by product id " + product);
      return;
    }
    String old = product.getImageName();
    if (old != null && !old.isEmpty()) {
      if (imageName.equals(old)) {
        wicLogger.log("New image path is same is current. No transaction. old:" + old + " new:" + imageName);
        return;
      }
      else {
        wicLogger.log("Replacing image file name from " + product.getImageName() + " to " + imageName);
      }
    }
    product.setImageName(imageName);
    wicTransactionManager.saveOrUpdateProduct(product);
    wicLogger.log(String.format("Product updated successfully. %s", product.toString()));
  }

}
