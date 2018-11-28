package bettercare.wic.endpoint;


import bettercare.wic.dal.entity.*;
import bettercare.wic.exceptions.InvalidCustomerDataException;
import bettercare.wic.exceptions.FailedToDeleteException;
import bettercare.wic.model.PackagingOrderedProductRepresentation;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.*;
import com.sun.jersey.api.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// NOTE:
// Client must send date value in UTC time. JVM assume that incoming date is in UTC format.

@Validated
@RequestMapping("/")
@RestController
public class WicController {

    @Autowired
    private SaveWicOrderService saveWicOrderService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private ProductsParser productsParser;


    // Customer Order

    /**
     * This is the only input data from Customer client.
     * All the rest of end-points are for admin pupose.
     * @param model input data
     * @return order record
     * @throws InvalidCustomerDataException invalid payload
     */
    @PostMapping("/customerOrder")
    ResponseEntity<PackagingOrderedProductRepresentation> createCustomerOrder(@Valid @RequestBody WicOrderRepresentation model) throws InvalidCustomerDataException {
        WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
        if (wicOrder != null) {
            model.setOrderId(wicOrder.getId());
            model.setStatus(wicOrder.getStatus());
            PackagingOrderedProductRepresentation representation;
            try {
                representation = productsParser.parseProducts(model.getProducts());
            }
            catch (Exception ex) {
                entityService.deleteById(WicOrder.class, wicOrder.getId());
                throw new InvalidCustomerDataException("There is a problem with the customer data:" + model.toString());
            }
            representation.setOrderId(wicOrder.getId());
            return new ResponseEntity<>(representation, HttpStatus.CREATED);
        }
        return getBadResponseEntity(new PackagingOrderedProductRepresentation());
    }

    // WicOrder CRUD

    // @PreAuthorize("hasAnyRole('ADMIN')") <- Allow anyone with ADMIN role to access this method
    @GetMapping("/wicOrders")
    ResponseEntity<List> readWicOrders() {
        return new ResponseEntity<>(entityService.findAll(WicOrder.class), HttpStatus.OK);
    }

    @GetMapping("/wicOrders/status")
    ResponseEntity<List> readOrdersByStatus(@RequestParam(value = "status", required = false, defaultValue = "ORDER_RECEIVED") String status) {
        return new ResponseEntity<>(entityService.findOrderByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/wicOrders/pending")
    ResponseEntity<List> readPendingOrders() {
        return new ResponseEntity<>(entityService.findPendingOrders(), HttpStatus.OK);
    }

    @GetMapping("/wicOrders/{id}")
    ResponseEntity<WicOrder> readWicOrder(@PathVariable long id) {
        WicOrder wicOrder = entityService.findById(WicOrder.class, id);
        if(wicOrder != null) {
            return new ResponseEntity<>(wicOrder, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/wicOrders/" + id));
    }

    // Called by customerOrder
    @PostMapping("/wicOrder")
    ResponseEntity<WicOrder> createWicOrder(@Valid @RequestBody WicOrder wicOrder) {
        if (wicOrder.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(WicOrder.class, wicOrder), HttpStatus.CREATED);
        }
        return getBadResponseEntity(wicOrder);
    }

    @PutMapping("/wicOrder")
    ResponseEntity<WicOrder> updateWicOrder(@Valid @RequestBody WicOrder wicOrder) {
        if (entityService.isEntityExist(WicOrder.class, wicOrder.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(WicOrder.class, wicOrder), HttpStatus.OK);
        }
        return getBadResponseEntity(wicOrder);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/wicOrders/{id}")
    void deleteWicOrder(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<WicOrder> responseEntity = readWicOrder(id);
        if(isResponseEntityValid(responseEntity)) {
            entityService.deleteById(WicOrder.class, id);
        }
        else {
            throw new FailedToDeleteException(composeDeleteFailureMessage(WicOrder.class, id));
        }
    }


    // Category CRUD

    @GetMapping("/categories")
    ResponseEntity<List> readCategories() {
        return new ResponseEntity<>(entityService.findAll(Category.class), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    ResponseEntity<Category> readCategory(@PathVariable long id) {
        Category category = entityService.findById(Category.class, id);
        if(category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/categories/" + id));
    }

    @PostMapping("/category")
    ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        if (category.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Category.class, category), HttpStatus.CREATED);
        }
        return getBadResponseEntity(category);
    }

    @PutMapping("/category")
    ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
        if (entityService.isEntityExist(Category.class, category.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Category.class, category), HttpStatus.OK);
        }
        return getBadResponseEntity(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/categories/{id}")
    void deleteCategory(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Category> responseEntity = readCategory(id);
        if(isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Category.class, id);
        }
        else {
            throw new FailedToDeleteException(composeDeleteFailureMessage(Category.class, id));
        }
    }


    // Product CRUD

    @GetMapping("/products")
    ResponseEntity<List> readProducts(@RequestParam(value = "isHandling", required = false, defaultValue = "true") String isHandling) {
        return new ResponseEntity<>(entityService.findProductByIsHandling(Boolean.valueOf(isHandling)), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<Product> readProduct(@PathVariable long id) {
        Product product = entityService.findById(Product.class, id);
        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/products/" + id));
    }

    @GetMapping("/products/category/{id}")
    ResponseEntity<List<Product>> readProductsByCatgoryId(@PathVariable long id) {
        return new ResponseEntity<>(entityService.findProductsByCategoryId(id), HttpStatus.OK);
    }

    @PostMapping("/product")
    ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        if (product.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Product.class, product), HttpStatus.CREATED);
        }
        return getBadResponseEntity(product);
    }

    @PutMapping("/product")
    ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        if (entityService.isEntityExist(Product.class, product.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Product.class, product), HttpStatus.OK);
        }
        return getBadResponseEntity(product);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Product> responseEntity = readProduct(id);
        if(isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Product.class, id);
        }
        else {
            throw new FailedToDeleteException(composeDeleteFailureMessage(Product.class, id));
        }
    }


    // Customer CRUD

    @GetMapping("/customers")
    ResponseEntity<List> readCustomers() {
        return new ResponseEntity<>(entityService.findAll(Customer.class), HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    ResponseEntity<Customer> readCustomer(@PathVariable long id) {
        Customer customer = entityService.findById(Customer.class, id);
        if(customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/customers/" + id));
    }

    @PostMapping("/customer")
    ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        if (customer.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Customer.class, customer), HttpStatus.CREATED);
        }
        return getBadResponseEntity(customer);
    }

    @PutMapping("/customer")
    ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        if (entityService.isEntityExist(Customer.class, customer.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Customer.class, customer), HttpStatus.OK);
        }
        return getBadResponseEntity(customer);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Customer> responseEntity = readCustomer(id);
        if(isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Customer.class, id);
        }
        else {
            throw new FailedToDeleteException(composeDeleteFailureMessage(Customer.class, id));
        }
    }


    // Delivery CRUD

    @GetMapping("/deliveries")
    ResponseEntity<List> readDeliveries() {
        return new ResponseEntity<>(entityService.findAll(Delivery.class), HttpStatus.OK);
    }

    @GetMapping("/deliveries/{id}")
    ResponseEntity<Delivery> readDelivery(@PathVariable long id) {
        Delivery delivery = entityService.findById(Delivery.class, id);
        if(delivery != null) {
            return new ResponseEntity<>(delivery, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/deliveries/" + id));
    }

    @PostMapping("/delivery")
    ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        if (delivery.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Delivery.class, delivery), HttpStatus.CREATED);
        }
        return getBadResponseEntity(delivery);
    }

    @PutMapping("/delivery")
    ResponseEntity<Delivery> updateDelivery(@RequestBody Delivery delivery) {
        if (entityService.isEntityExist(Delivery.class, delivery.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Delivery.class, delivery), HttpStatus.OK);
        }
        return getBadResponseEntity(delivery);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deliveries/{id}")
    void deleteDelivery(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Delivery> responseEntity = readDelivery(id);
        if(isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Delivery.class, id);
        }
        else {
            throw new FailedToDeleteException(composeDeleteFailureMessage(Delivery.class, id));
        }
    }


    // MissingProduct CRUD

    @GetMapping("/missingProducts")
    ResponseEntity<List> readMissingProducts() {
        return new ResponseEntity<>(entityService.findAll(MissingProduct.class), HttpStatus.OK);
    }

    @GetMapping("/missingProducts/{id}")
    ResponseEntity<MissingProduct> readMissingProduct(@PathVariable long id) {
        MissingProduct missingProduct = entityService.findById(MissingProduct.class, id);
        if(missingProduct != null) {
            return new ResponseEntity<>(missingProduct, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/missingProducts/" + id));
    }

    @PostMapping("/missingProduct")
    ResponseEntity<MissingProduct> createMissingProduct(@RequestBody MissingProduct missingProduct) {
        if (missingProduct.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(MissingProduct.class, missingProduct), HttpStatus.CREATED);
        }
        return getBadResponseEntity(missingProduct);
    }

    @PutMapping("/missingProduct")
    ResponseEntity<MissingProduct> updateMissingProduct(@RequestBody MissingProduct missingProduct) {
        if (entityService.isEntityExist(MissingProduct.class, missingProduct.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(MissingProduct.class, missingProduct), HttpStatus.OK);
        }
        return getBadResponseEntity(missingProduct);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/missingProducts/{id}")
    void deleteMissingProduct(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<MissingProduct> responseEntity = readMissingProduct(id);
        if(isResponseEntityValid(responseEntity)) {
            entityService.deleteById(MissingProduct.class, id);
        }
        else {
            throw new FailedToDeleteException(composeDeleteFailureMessage(MissingProduct.class, id));
        }
    }


    // Voucher CRUD

    @GetMapping("/vouchers")
    ResponseEntity<List> readVouchers() {
        return new ResponseEntity<>(entityService.findAll(Voucher.class), HttpStatus.OK);
    }

    @GetMapping("/vouchers/{id}")
    ResponseEntity<Voucher> readVoucher(@PathVariable long id) {
        Voucher voucher = entityService.findById(Voucher.class, id);
        if(voucher != null) {
            return new ResponseEntity<>(voucher, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/vouchers/" + id));
    }

    @PostMapping("/voucher")
    ResponseEntity<Voucher> createVoucher(@Valid @RequestBody Voucher voucher) {
        if (voucher.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Voucher.class, voucher), HttpStatus.CREATED);
        }
        return getBadResponseEntity(voucher);
    }

    @PutMapping("/voucher")
    ResponseEntity<Voucher> updateVoucher(@Valid @RequestBody Voucher voucher) {
        if (entityService.isEntityExist(Voucher.class, voucher.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Voucher.class, voucher), HttpStatus.OK);
        }
        return getBadResponseEntity(voucher);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/vouchers/{id}")
    void deleteVoucher(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Voucher> responseEntity = readVoucher(id);
        if(isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Voucher.class, id);
        }
        else {
            throw new FailedToDeleteException(composeDeleteFailureMessage(Voucher.class, id));
        }
    }

    private <T> ResponseEntity<T> getBadResponseEntity(T obj) {
        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }

    private <T> String composeDeleteFailureMessage(Class<T> clz, long id) {
        return String.format("Failed to find %s by id:%d for deletion.", clz.getName(), id);

    }

    private <T> boolean isResponseEntityValid(ResponseEntity<T> responseEntity) {
        return responseEntity != null && responseEntity.hasBody();
    }


}
