package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.service.common.InitSetup;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductStocker extends InitSetup {

  @Test
  public void addNewProducts() {

    Category category = wicEntityManasger.find(Category.class, categoryId);
    if (category == null) {
      wicLogger.log("ERROR Category ID not found:" + categoryId + " No transaction");
      Assert.fail();
    }

    Map<String, Object> where = new HashMap<>();
    where.put("category_id", categoryId);

    for(int i = 0; i < 5; i++) {
      addNewProduct(category, where, i);
    }

    List<Product> products = wicTransactionManager.findProductsByCategoryId(categoryId);
    Assert.assertFalse(products.isEmpty());
    Assert.assertTrue(products.size() >= 5);
  }

  private void addNewProduct(Category category, Map<String, Object> where, int i) {
    String imageName = null;
    String barcode = "barcode_0w3422932989232" + i;
    String desc = "desc 10oz under age 6 month" + i;
    String productName = "prodName Baby Powerder Milk" + i;
    where.put("name", productName);
    String query = composeQuery(Product.class, where, " limit 1 ");

    if (isEmpty(query, Product.class)) {
      Product product = prepareProduct(category, barcode, desc, productName, imageName);
      Product newProduct = wicTransactionManager.saveOrUpdateProduct(product);
      wicLogger.log(String.format("Created product %s", newProduct.toString()));
    }
    else {
      wicLogger.log("Product is already in the system:" + query);
    }
  }

  private Product prepareProduct(Category category, String barcode, String desc, String prodName, String imageName) {
    Product product = new Product();
    product.setCategory(category);
    product.setBarcode(barcode);
    product.setDescription(desc);
    product.setImageName(imageName);
    product.setName(prodName);
    return product;
  }

  private String composeQuery(Class claz, Map<String, Object> whereSource, String otherClause) {
    String alias = "o";
    String whereClause = composeWhereClause(whereSource, alias);
    otherClause = otherClause == null ? "" : otherClause;
    return String.format("select * from %s as %s where %s %s",
        claz.getSimpleName().toLowerCase(), alias, whereClause, otherClause);
  }

  private String composeWhereClause(Map<String, Object> whereSource, String alias) {
    StringBuilder whereClause = new StringBuilder();
    whereSource.forEach((columnName, value) -> {
      if (whereClause.length() > 0) {
        whereClause.append(" and ");
      }
      whereClause.append(alias).append(".").append(columnName).append("='").append(value).append("'");
    });
    return whereClause.toString();
  }

  private boolean isEmpty(String query, Class clz) {
    return wicEntityManasger.findListByNativeQuery(query, clz).isEmpty();
  }

}
