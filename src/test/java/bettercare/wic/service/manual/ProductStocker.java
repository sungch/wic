package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.service.common.InitSetup;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductStocker extends InitSetup {

  @Test
  public void addNewProduct() {
    String imageName = null;
    String barcode = "barcode_0w3422932989232";
    String desc = "desc 10oz under age 6 month";
    String productName = "prodName Baby Powerder Milk";
    Category category = wicEntityManasger.find(Category.class, categoryId);
    if (category == null) {
      wicLogger.log("ERROR Category ID not found:" + categoryId + " No transaction");
      return;
    }
    Map<String, Object> where = new HashMap<>();
    where.put("category_id", categoryId);
    where.put("name", productName);
    String query = composeQuery(Product.class, where, " limit 1 ");
    if (isEmpty(query)) {
      Product product = prepareProduct(category, barcode, desc, productName, imageName);
      product = wicTransactionManager.saveOrUpdateProduct(product);
      wicLogger.log(String.format("Created product %s", product.toString()));
    }
    else {
      wicLogger.log("Product is already in the system by query:" + query);
    }
    List<Product> products = wicTransactionManager.findProductsByCategoryId(categoryId);
    for(Product product : products) {
      wicLogger.log(String.format("category:%s product:%s prodName:%s imagename:%s",
          product.getCategory().getId(), product.getId(), product.getName(), product.getImageName()));
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

  private boolean isEmpty(String query) {
    List list = wicEntityManasger.findByNativeQuery(query);
    return list.isEmpty();
  }

}
