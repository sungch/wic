package bettercare.wic.service;

import bettercare.wic.dal.dao.CategoryDao;
import bettercare.wic.dal.dao.ProductDao;
import bettercare.wic.dal.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WicTransactionManager {

    @Resource private CategoryDao categoryDao; // requires @EnableJpaRepositories("bettercare.wic.dal") in somewhere configuration
    @Resource private ProductDao productDao;

    public Category saveAndFlushCategory(Category category) {
        return categoryDao.saveAndFlush(category);
    }

    public Category findCategoryById(long id) {
        Optional<Category> category = categoryDao.findById(id);
        return category.get();
    }

    public Product saveOrUpdateAndFlushProduct(Product product) {
        return productDao.saveAndFlush(product);
    }

    public List<Product> saveAllProducts(List<Product> products) {
        return productDao.saveAll(products);
    }

    public List<Product> findProductsByCategoryId(long categoryId) {
        Optional<Category> categoryOptional = categoryDao.findById(categoryId);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category.getProducts();
        }
        return Collections.EMPTY_LIST;
    }
}