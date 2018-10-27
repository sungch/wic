package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import org.junit.Test;

import java.util.List;

public class ProductImageUpdater extends InitSetup {

  @Test
  public void updateProducts() {
    assert categories != null;
    for(Category category : categories) {
      List<Product> products = entityService.findProductsByCategoryId(category.getId());
      for(Product product : products) {
        updateProduct(product);
      }
    }
  }

  private void updateProduct(Product product) {
    if (product == null) {
      wicLogger.log("No product found by product id " + product);
      return;
    }
    String imageUrl = "product/" + product.getCategory().getId() + "/" + product.getName() + ".jpg";
    String old = product.getImageUrl();
    if (old != null && !old.isEmpty() && imageUrl.equals(old)) {
      wicLogger.log("New image path is same is current. No transaction. old:" + old + " new:" + imageUrl);
      return;
    }
    product.setImageUrl(imageUrl.replaceAll("[\\s]", "").toLowerCase());
    entityService.saveOrUpdate(Product.class, product);
    wicLogger.log("Replaced image file name from " + product.getImageUrl() + " to " + imageUrl);
  }

}
