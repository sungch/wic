package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import org.junit.Test;

import java.util.List;

public class ProductUpdater extends InitSetup {

  @Test
  public void updateNameAndImageUrl() {
    assert categories != null;
    for(Category category : categories) {
      if(category == null) {
        wicLogger.error("Cannot update null category. ", ProductUpdater.class);
        continue;
      }
      List<Product> products = entityService.findProductsByCategoryId(category.getId());
      for(Product product : products) {
        if(product == null) {
          wicLogger.error("Failed to update product url for category id: " + category.getImageUrl(), ProductUpdater.class);
          continue;
        }
        updateImage(product);
        updateName(product);
        entityService.saveOrUpdate(Product.class, product);
      }
    }
  }

  private void updateImage(Product product) {
    String imageUrl = "./assets/images/products/" + product.getId() + "/" + product.getName() + ".jpg";
    String old = product.getImageUrl();
    if (old != null && !old.isEmpty() && imageUrl.equals(old)) {
      wicLogger.warn("New image path is same is current. No transaction. old:" + old + " new:" + imageUrl, ProductUpdater.class);
      return;
    }
    product.setImageUrl(imageUrl.replaceAll("[\\s]", "").toLowerCase());
    wicLogger.info("Replacing image file name from " + product.getImageUrl() + " to " + imageUrl, ProductUpdater.class);
  }

  private void updateName(Product product) {
    String name = "prodname_" + product.getId();
    String old = product.getName();
    if (old != null && !old.isEmpty() && name.equals(old)) {
      wicLogger.warn("New image path is same is current. No transaction. old:" + old + " new:" + name, ProductUpdater.class);
      return;
    }
    product.setName(name);
    wicLogger.info("Replacing name from " + product.getName() + " to " + name, ProductUpdater.class);
  }

}
