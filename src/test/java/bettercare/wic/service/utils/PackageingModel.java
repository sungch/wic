package bettercare.wic.service.utils;

import bettercare.wic.dal.entity.Product;

import static net.minidev.asm.DefaultConverter.convertToInt;
import static net.minidev.asm.DefaultConverter.convertToLong;

public class PackageingModel {

  private final String QUANTITY_DELIMITER = "=";
  private final String CATEGORY_PROD_DELIMITER = ":";

  private long categoryId;
  private long productId;
  private int quantity;
  private Product product;

  public PackageingModel(String item_) {
    String catProd_ = item_.substring(0, item_.indexOf(QUANTITY_DELIMITER));
    String[] catProd = catProd_.split(CATEGORY_PROD_DELIMITER);
    if (validateItemFormat(catProd)) {
      categoryId = convertToLong(catProd[0]);
      productId = convertToLong(catProd[1]);
      String qnty = item_.substring(item_.indexOf(QUANTITY_DELIMITER));
      quantity = convertToInt(qnty);
    }
  }

  private boolean validateItemFormat(String[] catProds) {
    return catProds != null && catProds.length == 2;
  }

  public long getCategoryId() {
    return categoryId;
  }

  public long getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
