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
    String newImgUrl = imageName + productId;
    String old = product.getImageUrl();
    if (old != null && !old.isEmpty()) {
      if (newImgUrl.equals(old)) {
        wicLogger.log("New image path is same is current. No transaction. old:" + old + " new:" + newImgUrl);
        return;
      }
      else {
        wicLogger.log("Replacing image file name from " + product.getImageUrl() + " to " + newImgUrl);
      }
    }
    product.setImageUrl(newImgUrl);
    wicTransactionManager.saveOrUpdateProduct(product);
    wicLogger.log(String.format("Product updated successfully. %s", product.toString()));
  }

}
