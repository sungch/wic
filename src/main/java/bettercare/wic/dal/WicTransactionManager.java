package bettercare.wic.dal;

import bettercare.wic.dal.dao.*;
import bettercare.wic.dal.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WicTransactionManager {

    // requires @EnableJpaRepositories("bettercare.wic.dal") in somewhere configuration
    @Resource private CategoryDao categoryDao;
    @Resource private ProductDao productDao;
    @Resource private CustomerDao customerDao;
    @Resource private VoucherDao voucherDao;
    @Resource private WicOrderDao wicOrderDao;
    @Resource private WicLogger wicLogger;


    public List<Category> findAllCategories() {
        return categoryDao.findAll();
    }

    public Category saveOrUpdateCategory(Category category) {
        return categoryDao.saveAndFlush(category);
    }

    public Product saveOrUpdateProduct(Product product) {
        return productDao.saveAndFlush(product);
    }

    public List<Product> findProductsByCategoryId(long categoryId) {
        Optional<Category> categoryOptional = categoryDao.findById(categoryId);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category.getProducts();
        }
        return Collections.EMPTY_LIST;
    }

    public List<Product> findAllProducts() {
        return productDao.findAll();
    }

    public Product findProductById(long productId) {
        Optional<Product> productOptional = productDao.findById(productId);
        return productOptional.orElse(null);
    }

    public Customer saveOrUpdateCustomer(Customer customer) {
        wicLogger.debug("Saving customer: " + customer.toString(), Customer.class);
        return customerDao.saveAndFlush(customer);
    }

    public Voucher saveOrUpdateVoucher(Voucher voucher) {
        wicLogger.debug("Saving voucher: " + voucher.toString(), Voucher.class);
        return voucherDao.saveAndFlush(voucher);
    }

    public WicOrder saveOrUpdateWicOrder(WicOrder wicOrder) {
        wicLogger.debug("Saving wic order: " + wicOrder.toString(), WicOrder.class);
        return wicOrderDao.saveAndFlush(wicOrder);
    }

}