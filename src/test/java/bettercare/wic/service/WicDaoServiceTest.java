package bettercare.wic.service;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WicApplication.class)
public class WicDaoServiceTest {

    @Resource private WicDaoService wicDaoService;

    @Test
    @Transactional
    public void contextLoads() {
    }

    @Before
    public void setup() {
        Assert.assertNotNull(wicDaoService);
    }

    /**
     * This has no dependency to add.
     */
    @Test
    public void testCreateUpdateCategory() {
        for(int i = 0; i < 2; i++) {
            Category category = createCategory("categoryImageId_" + i, "categoryName:" + i);
            wicDaoService.saveAndFlushCategory(category);
        }
    }

    /**
     * Add category before products.
     * Product need category id. This is a manual work.
     */
    @Test
    public void testAddProduct() {
        long[] categoryIds = {1, 2};
        List<Product> products = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Category category = wicDaoService.findCategoryById(categoryIds[i % categoryIds.length]);
            Product p = createProduct(category, "barcode:" + i, "desc:" + i, "prodImgId:" + i, "prodName:" + i);
            products.add(p);
        }
        wicDaoService.saveAll(products);
    }


    // HELPER METHODS

    private Category createCategory(String categoryImageId, String categoryName) {
        Category category = new Category();
        category.setImageId(categoryImageId);
        category.setName(categoryName);
        return category;
    }

    private Product createProduct(Category category, String barcode, String desc, String prodImgId,  String prodName) {
        Product product = new Product();
        product.setCategory(category);
        product.setBarcode(barcode);
        product.setDescription(desc);
        product.setImageId(prodImgId);
        product.setName(prodName);
        return product;
    }
}
