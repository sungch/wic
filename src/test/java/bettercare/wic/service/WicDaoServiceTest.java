package bettercare.wic.service;

import bettercare.wic.app.WicApplication;
import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import org.junit.Assert;
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

    @Test
    public void testCategoryCreate() {
        Assert.assertNotNull(wicDaoService);
        Category category = wicDaoService.saveAndFlushCategory(createCategory());
    }

    private Category createCategory() {
        Category category = new Category();
        category.setImageId("categoryImageId");
        category.setName("category name");
        category.setProducts(createProducts());
        return category;
    }

    private List<Product> createProducts() {
        List<Product> products = new ArrayList<>(10);
        Product p = new Product();
        p.setBarcode("1234");
        p.setDescription("description here");
        p.setImageId("img1");
        p.setName("whole milk");
        products.add(p);
        return products;
    }
}
