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
        addProducsOnEachCategoryIfNew(category, i, "Y");
      }
      List<Product> products = entityService.findProductsByCategory(category);
      wicLogger.log("Created products:" + products.size());
      Assert.assertFalse(products.isEmpty());
    }
  }

  private void addProducsOnEachCategoryIfNew(Category category, int i, String isHandling) {
    String imageName = null;
    String barcode = "barcode_0w3422932989232_" + i;
    String desc = "desc _" + i;
    String productName = "prodName_" + i;
    if (isProductEmpty(category, productName, isHandling)) {
      Product product = prepareProduct(category, barcode, desc, productName, imageName, isHandling);
      Product newProduct = entityService.saveOrUpdate(Product.class, product);
      wicLogger.log(String.format("Created product %s", newProduct.toString()));
    }
    else {
      wicLogger.log("Product is already in the system:" + productName);
    }
  }

  private Product prepareProduct(Category category, String barcode, String desc, String prodName, String imageName, String isHandling) {
    Product product = new Product();
    product.setCategory(category);
    product.setBarcode(barcode);
    product.setDescription(desc);
    product.setImageUrl(imageName);
    product.setName(prodName);
    product.setHandling(isHandling);
    return product;
  }

  private boolean isProductEmpty(Category category, String productName, String isHandling) {
    List<Product> products = entityService.findProductByCategoryAndNameAndIsHandling(category, productName, isHandling);
    return products.isEmpty();
  }

}
