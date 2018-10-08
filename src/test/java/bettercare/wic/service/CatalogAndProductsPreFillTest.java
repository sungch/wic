package bettercare.wic.service;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Note:
 * A. Category and Product tables are pre-filled by an administrator.
 * B. When a customer opnes a web page, the content is provided by Product and Category tables.
 * C. When an order is made, WicOrder, Voucher, Customer, Delivery tables are being filled.
 * D. During the package, MissingProduct table is filled as needed.
 * E. Product Images are saved as ${categoryId}/${productId}/pictureNumber.jpg file name.
 * <p>
 * Work WicOrder:
 * 1. Add Category
 * 2. Add Product
 * 3. Copy all pictures into the file system according to Note E above.
 * 4. Update Product Image Names in Product table.
 * <p>
 * Test Category and Product
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class CatalogAndProductsPreFillTest {

  // Use @Transactional to roll back at the end of the test.

  @Resource
  private WicTransactionManager wicTransactionManager;

  @Resource
  private WicEntityManager wicEntityManasger;

  private long categoryId = 2;
  private String categoryName = "category_273";

  @Test
  public void contextLoads() {
  }

  @Before
  public void setup() {
    Assert.assertNotNull(wicTransactionManager);
    Assert.assertNotNull(wicEntityManasger);
  }

  /**
   * 1. Add Categories
   * In composing the map, key = column name, val = value of the column. Thee entries are constituting where clause.
   */
  @Test
  public void addNewCategory() {
    Map<String, Object> where = new HashMap<>();
    where.put("name", categoryName);
    String query = composeQuery(Category.class, where, " limit 1 ");
    if (isEmpty(query)) {
      Category category = prepareCategory(categoryName);
      category = wicTransactionManager.saveOrUpdateCategory(category);
      log(String.format("Created a Category %s", category.toString()));
    }
    else {
      log("The same Category name already found in the system. No transactions:" + categoryName);
    }
  }

  private boolean isEmpty(String query) {
    List list = wicEntityManasger.findByNativeQuery(query);
    return list.isEmpty();
  }

  /**
   * 2. Add Products with Category id but blank image ids for the first entry.
   */
  @Test
  @DependsOn("addNewCategory")
  public void addNewProduct() {
    String imagePath = null;
    String barcode = "barcode_0";
    String desc = "desc_0";
    String productName = "prodName_7";
    Category category = wicEntityManasger.find(Category.class, categoryId);
    if (category == null) {
      log("ERROR Category ID not found:" + categoryId + " No transaction");
      return;
    }
    Map<String, Object> where = new HashMap<>();
    where.put("category_id", categoryId);
    where.put("name", productName);
    String query = composeQuery(Product.class, where, " limit 1 ");
    if (isEmpty(query)) {
      Product product = prepareProduct(category, barcode, desc, productName, imagePath);
      product = wicTransactionManager.saveOrUpdateProduct(product);
      log(String.format("Created product %s", product.toString()));
    }
    else {
      log("Product is already in the system by query:" + query);
    }
  }

  /**
   * 3. Take pictures and save images. See Note E above.
   */
  @Test
  public void addImages() {
    log("addd images under category_id/product_id/productImageNumber");
  }

  /**
   * 4. Update Product Image Names
   * When all images are ready, Update product.
   * Now categoryId, productId, imagePathName are all available.
   */
  @Test
  @DependsOn({"addNewProduct"})
  public void updateProduct() {
    List<Product> products = wicTransactionManager.findProductsByCategoryId(categoryId);
    if (products.isEmpty()) {
      log("No product found to update by category id " + categoryId);
      return;
    }
    int imgId = 3;

    for (Iterator<Product> I = products.listIterator(); I.hasNext();) {
      Product product = I.next();
      String imagePath = product.getCategoryId() + "/" + product.getId() + "/imageFile_" + imgId++;
      String old = product.getImagePath();
      if(old != null && !old.isEmpty()) {
        if(imagePath.equals(old)) {
          log("New image path is same is current. No transaction. old:" + old + " new:" + imagePath);
          I.remove();
          continue;
        }
        else {
          log("Replacing image file name from " + product.getImagePath() + " to " + imagePath);
        }
      }
      product.setImagePath(imagePath);
    }
    if(!products.isEmpty()) {
      products = wicTransactionManager.saveOrUpdateProducts(products);
      for(Product product : products) {
        log(String.format("Product updated successfully. %s", product.toString()));
      }
    }
  }

  private Category prepareCategory(String categoryName) {
    Category category = new Category();
    category.setName(categoryName);
    return category;
  }

  private Product prepareProduct(Category category, String barcode, String desc, String prodName, String imagePath) {
    Product product = new Product();
    product.setCategoryId(category.getId());
    product.setBarcode(barcode);
    product.setDescription(desc);
    product.setImagePath(imagePath);
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

  private void log(String mesg) {
    System.out.println("***********************************");
    System.out.print("*");
    System.out.print(mesg);
    System.out.println("*");
    System.out.println("***********************************");
  }

  // -- EntityManager Operations

  @Test
  public void testEntityManager() {
    Category category = wicEntityManasger.find(Category.class, categoryId);
    log(category.getName() + " is found entityManager");
  }

}
