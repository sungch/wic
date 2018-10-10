package bettercare.wic.service;

import bettercare.wic.dal.dao.*;
import bettercare.wic.dal.entity.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
    @Resource private CustomerDao customerDao;
    @Resource private VoucherDao voucherDao;
    @Resource private WicOrderDao wicOrderDao;

    public Category saveOrUpdateCategory(Category category) {
        return categoryDao.saveAndFlush(category);
    }

    public Category findCategoryById(long id) {
        Optional<Category> category = categoryDao.findById(id);
        return category.get();
    }

    public Product saveOrUpdateProduct(Product product) {
        return productDao.saveAndFlush(product);
    }

    public List<Product> saveOrUpdateProducts(List<Product> products) {
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

    public Product findProductById(long productId) {
        return productDao.getOne(productId);
    }

    public Customer saveOrUpdateCustomer(Customer customer) {
        return customerDao.saveAndFlush(customer);
    }

    public Voucher saveOrUpdateVoucher(Voucher voucher) {
        return voucherDao.saveAndFlush(voucher);
    }

    public WicOrder saveOrUpdateWicOrder(WicOrder wicOrder) {
        return wicOrderDao.saveAndFlush(wicOrder);
    }
}