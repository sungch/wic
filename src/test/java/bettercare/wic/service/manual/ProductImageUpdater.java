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
      if(category == null) {
        wicLogger.error("Cannot update null category. ", ProductImageUpdater.class);
        continue;
      }
      List<Product> products = entityService.findProductsByCategoryId(category.getId());
      for(Product product : products) {
        if(product == null) {
          wicLogger.error("Failed to update product url for category id: " + category.getImageUrl(), ProductImageUpdater.class);
          continue;
        }
        updateProduct(product);
      }
    }
  }

  private void updateProduct(Product product) {
    String imageUrl = "product/" + product.getCategory().getId() + "/" + product.getName() + ".jpg";
    String old = product.getImageUrl();
    if (old != null && !old.isEmpty() && imageUrl.equals(old)) {
      wicLogger.warn("New image path is same is current. No transaction. old:" + old + " new:" + imageUrl, ProductImageUpdater.class);
      return;
    }
    product.setImageUrl(imageUrl.replaceAll("[\\s]", "").toLowerCase());
    entityService.saveOrUpdate(Product.class, product);
    wicLogger.info("Replaced image file name from " + product.getImageUrl() + " to " + imageUrl, ProductImageUpdater.class);
  }

}
