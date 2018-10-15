package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Product;
import bettercare.wic.service.common.InitSetup;
import org.junit.Test;

public class ProductUpdater extends InitSetup {

  @Test
  public void updateProducts() {

    for(int i = 0; i < productIds.length; i++) {
      updateProduct(productIds[i]);
    }

  }

  private void updateProduct(long productId) {
    Product product = wicTransactionManager.findProductById(productId);
    if (product == null) {
      wicLogger.log("No product found by product id " + product);
      return;
    }
    String newImgName = imageName + productId;
    String old = product.getImageName();
    if (old != null && !old.isEmpty()) {
      if (newImgName.equals(old)) {
        wicLogger.log("New image path is same is current. No transaction. old:" + old + " new:" + newImgName);
        return;
      }
      else {
        wicLogger.log("Replacing image file name from " + product.getImageName() + " to " + newImgName);
      }
    }
    product.setImageName(newImgName);
    wicTransactionManager.saveOrUpdateProduct(product);
    wicLogger.log(String.format("Product updated successfully. %s", product.toString()));
  }

}