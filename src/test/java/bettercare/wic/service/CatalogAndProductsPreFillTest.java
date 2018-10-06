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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Note:
 * A. Category and Product tables are pre-filled by an administrator.
 * B. When a customer opnes a web page, the content is provided by Product and Category tables.
 * C. When an order is made, Order, Voucher, Customer, Delivery tables are being filled.
 * D. During the package, MissingProduct table is filled as needed.
 * E. Product Images are saved as ${categoryId}/${productId}/pictureNumber.jpg file name.
 * <p>
 * Work Order:
 * 1. Add Category
 * 2. Add Product
 * 3. Copy all pictures into the file system according to Note E above.
 * 4. Update Product Image Names in Product table.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class CatalogAndProductsPreFillTest {

  // Use @Transactional to roll back at the end of the test.

  @Resource
  private WicTransactionManager wicTransactionManager;

  @Resource
  private WicEntityManager wicEntityManasger;

  private long[] categoryIds = {1, 2};

  @Test
  public void contextLoads() {
  }

  @Before
  public void setup() {
    Assert.assertNotNull(wicTransactionManager);
  }

  /**
   * 1. Add Categories
   */
  @Test
  public void add2Categories() {
    for (int i = 0; i < categoryIds.length; i++) {
      Category category = createCategory("categoryName_" + i);
      wicTransactionManager.saveAndFlushCategory(category);
    }
  }

  /**
   * 2. Add Products with Category id but blank image ids for the first entry.
   */
  @Test
  @DependsOn("add2Categories")
  public void add10Products2Categories() {
    List<Product> products = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Category category = wicTransactionManager.findCategoryById(categoryIds[i % categoryIds.length]);
      Product p = createProductWithNullImagePath(category, "barcode_" + i, "desc_" + i, "prodName_" + i);
      products.add(p);
    }
    wicTransactionManager.saveAllProducts(products);
  }

  /**
   * 3. Take pictures and save images. See Note E above.
   */
  @Test
  public void addImages() {

  }

  /**
   * 4. Update Product Image Names
   * When all images are ready, Update product.
   * Now categoryId, productId, imagePathName are all available.
   */
  @Test
  @DependsOn({"add10Products2Categories"})
  public void updateProductImageNamesForced() {
    boolean isForce = true;
    for (long categoryId : categoryIds) {
      updateProductImagePath(categoryId, isForce);
    }
  }

  @Test
  @DependsOn({"add10Products2Categories"})
  public void updateProductImageNamesNotForced() {
    boolean isForce = false;
    for (long categoryId : categoryIds) {
      updateProductImagePath(categoryId, isForce);
    }
  }

  private boolean productHasImagePath(Product product) {
    return product.getImagePath() != null;
  }

  private void updateProductImagePath(long categoryId, boolean isForce) {
    List<Product> products = wicTransactionManager.findProductsByCategoryId(categoryId);
    if (products.isEmpty()) {
      System.out.println("**** No product by category id " + categoryId);
      return;
    }
    if (!isForce) {
      products.removeIf(product -> productHasImagePath((Product) product));
    }
    if (products.isEmpty()) {
      return;
    }
    Random random = new Random();
    for (Product product : products) {
      int imageFileName = abs(random.nextInt());
      String imagePath = getImagePath(categoryId, product.getId(), imageFileName);
      updateProductImage(product, imagePath, isForce);
    }
    wicTransactionManager.saveAllProducts(products);
  }

  private void updateProductImage(Product product, String imagePath, boolean isForce) {
    if (isForce) {
      product.setImagePath(imagePath);
    }
    else {
      if (product.getImagePath() == null) {
        product.setImagePath(imagePath);
      }
    }
  }

  private String getImagePath(long categoryId, long productId, int imageFileName) {
    return categoryId + "/" + productId + "/" + imageFileName;
  }

  private Category createCategory(String categoryName) {
    Category category = new Category();
    category.setName(categoryName);
    return category;
  }

  private Product createProductWithNullImagePath(Category category, String barcode, String desc, String prodName) {
    Product product = new Product();
    product.setCategoryId(category.getId());
    product.setBarcode(barcode);
    product.setDescription(desc);
    product.setImagePath(null);
    product.setName(prodName);
    return product;
  }

  // -- EntityManager Operations

  @Test
  public void testEntityManager() {
    Category category = wicEntityManasger.find(Category.class, categoryIds[0]);
    System.out.println(category.getName() + " is found entityManager");
  }

}
