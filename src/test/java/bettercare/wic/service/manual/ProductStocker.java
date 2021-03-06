package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;


public class ProductStocker extends InitSetup {

  @Test
  public void addNewProducts() {
    assert categories != null;
    for(Category category : categories) {
      int start = (int)startProductId;
      int end = (int)startProductId + numOfProductsToCreatePerCategory;
      for (int i = start; i < end; i++) {
        addProducsOnEachCategoryIfNew(category, i, true);
      }
      List<Product> products = entityService.findProductsByCategoryId(category.getId());
      wicLogger.info("Created products:" + products.size(), ProductStocker.class);
      Assert.assertFalse(products.isEmpty());
    }
  }

  private void addProducsOnEachCategoryIfNew(Category category, int i, boolean isHandling) {
    String imageName = null;
    String barcode = "barcode_0w3422932989232_" + i;
    String desc = "desc _" + i;
    String productName = "prodName_" + i;
    if (isProductEmpty(category, productName, isHandling)) {
      Product product = prepareProduct(category, barcode, desc, productName, imageName, isHandling);
      Product newProduct = entityService.saveOrUpdate(Product.class, product);
      wicLogger.info(String.format("Created product %s", newProduct.toString()), ProductStocker.class);
    }
    else {
      wicLogger.warn("Product is already in the system:" + productName, ProductStocker.class);
    }
  }

  private Product prepareProduct(Category category, String barcode, String desc, String prodName, String imageName, boolean isHandling) {
    Product product = new Product();
    product.setCategory(category);
    product.setBarcode(barcode);
    product.setDescription(desc);
    product.setImageUrl(imageName);
    product.setName(prodName);
    product.setHandling(isHandling);
    return product;
  }

  private boolean isProductEmpty(Category category, String productName, boolean isHandling) {
    List<Product> products = entityService.findProductByCategoryAndNameAndIsHandling(category, productName, isHandling);
    return products.isEmpty();
  }

}
