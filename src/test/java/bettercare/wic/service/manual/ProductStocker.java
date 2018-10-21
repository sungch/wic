package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import bettercare.wic.service.common.InitSetup;
import bettercare.wic.service.supports.FetchService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductStocker extends InitSetup {

  @Resource
  private FetchService fetchService;

  @Test
  public void addNewProducts() {
    assert categories != null;
    for(Category category : categories) {
      long categoryId = category.getId();
      Map<String, Object> where = new HashMap<>();
      where.put("category_id", categoryId);
      int start = (int)startProductId;
      int end = (int)startProductId + numOfProductsToCreatePerCategory;
      for (int i = start; i < end; i++) {
        addProducsOnEachCategoryIfNew(category, where, i);
      }
      List<Product> products = fetchService.findProductsByCategoryId(categoryId);
      wicLogger.log("Created products:" + products.size());
      Assert.assertFalse(products.isEmpty());
    }
  }

  private void addProducsOnEachCategoryIfNew(Category category, Map<String, Object> where, int i) {
    String imageName = null;
    String barcode = "barcode_0w3422932989232_" + i;
    String desc = "desc _" + i;
    String productName = "prodName_" + i;
    where.put("name", productName);
    String query = composeQuery(Product.class, where, " limit 1 ");

    if (isEmpty(query, Product.class)) {
      Product product = prepareProduct(category, barcode, desc, productName, imageName);
      Product newProduct = fetchService.saveOrUpdateProduct(product);
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
    product.setImageUrl(imageName);
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
    return fetchService.findListByNativeQuery(query, clz).isEmpty();
  }

}
