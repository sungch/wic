package bettercare.wic.endpoint;


import bettercare.wic.dal.entity.*;
import bettercare.wic.dal.entity.user.User;
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
    @Autowired
    private ResponseService responseService;


    // Customer Order

    /**
     * This is the only input data from Customer client.
     * All the rest of end-points are for admin pupose.
     *
     * @param model input data
     * @return order record
     * @throws InvalidCustomerDataException invalid payload
     */
    @PostMapping("/customerOrder")
    ResponseEntity<PackagingOrderedProductRepresentation> createCustomerOrder(@Valid @RequestBody WicOrderRepresentation model) throws InvalidCustomerDataException {
        return responseService.handleCustomerOrder(model);
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
        return responseService.readWicOrder(id);
    }

    // Called by customerOrder
    @PostMapping("/wicOrder")
    ResponseEntity<WicOrder> createWicOrder(@Valid @RequestBody WicOrder wicOrder) {
        return responseService.createWicOrder(wicOrder);
    }

    @PutMapping("/wicOrder")
    ResponseEntity<WicOrder> updateWicOrder(@Valid @RequestBody WicOrder wicOrder) {
        return responseService.updateWicOrder(wicOrder);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/wicOrders/{id}")
    void deleteWicOrder(@PathVariable long id) throws FailedToDeleteException {
        responseService.deleteWicOrder(id);
    }


    // Category CRUD

    @GetMapping("/categories")
    ResponseEntity<List> readCategories() {
        return new ResponseEntity<>(entityService.findAll(Category.class), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    ResponseEntity<Category> readCategory(@PathVariable long id) {
        Category category = entityService.findById(Category.class, id);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/categories/" + id));
    }

    @PostMapping("/category")
    ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        if (category.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Category.class, category), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(category);
    }

    @PutMapping("/category")
    ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
        if (entityService.isEntityExist(Category.class, category.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Category.class, category), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/categories/{id}")
    void deleteCategory(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Category> responseEntity = readCategory(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Category.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Category.class, id));
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
        if (product != null) {
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
        return responseService.getBadResponseEntity(product);
    }

    @PutMapping("/product")
    ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        if (entityService.isEntityExist(Product.class, product.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Product.class, product), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(product);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Product> responseEntity = readProduct(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Product.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Product.class, id));
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
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/customers/" + id));
    }

    @PostMapping("/customer")
    ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        if (customer.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Customer.class, customer), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(customer);
    }

    @PutMapping("/customer")
    ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        if (entityService.isEntityExist(Customer.class, customer.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Customer.class, customer), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(customer);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Customer> responseEntity = readCustomer(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Customer.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Customer.class, id));
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
        if (delivery != null) {
            return new ResponseEntity<>(delivery, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/deliveries/" + id));
    }

    @PostMapping("/delivery")
    ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        if (delivery.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Delivery.class, delivery), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(delivery);
    }

    @PutMapping("/delivery")
    ResponseEntity<Delivery> updateDelivery(@RequestBody Delivery delivery) {
        if (entityService.isEntityExist(Delivery.class, delivery.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Delivery.class, delivery), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(delivery);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deliveries/{id}")
    void deleteDelivery(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Delivery> responseEntity = readDelivery(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Delivery.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Delivery.class, id));
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
        if (missingProduct != null) {
            return new ResponseEntity<>(missingProduct, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/missingProducts/" + id));
    }

    @PostMapping("/missingProduct")
    ResponseEntity<MissingProduct> createMissingProduct(@RequestBody MissingProduct missingProduct) {
        if (missingProduct.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(MissingProduct.class, missingProduct), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(missingProduct);
    }

    @PutMapping("/missingProduct")
    ResponseEntity<MissingProduct> updateMissingProduct(@RequestBody MissingProduct missingProduct) {
        if (entityService.isEntityExist(MissingProduct.class, missingProduct.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(MissingProduct.class, missingProduct), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(missingProduct);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/missingProducts/{id}")
    void deleteMissingProduct(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<MissingProduct> responseEntity = readMissingProduct(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(MissingProduct.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(MissingProduct.class, id));
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
        if (voucher != null) {
            return new ResponseEntity<>(voucher, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/vouchers/" + id));
    }

    @PostMapping("/voucher")
    ResponseEntity<Voucher> createVoucher(@Valid @RequestBody Voucher voucher) {
        if (voucher.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Voucher.class, voucher), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(voucher);
    }

    @PutMapping("/voucher")
    ResponseEntity<Voucher> updateVoucher(@Valid @RequestBody Voucher voucher) {
        if (entityService.isEntityExist(Voucher.class, voucher.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Voucher.class, voucher), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(voucher);
    }

    //    @PreAuthorize("hasAnyRole('ADMIN')") // ("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/vouchers/{id}")
    void deleteVoucher(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Voucher> responseEntity = readVoucher(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Voucher.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Voucher.class, id));
        }
    }

    //    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users")
    ResponseEntity<List> readUsers() {
        return new ResponseEntity<>(entityService.findAll(User.class), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users/{id}")
    ResponseEntity<User> readUsers(@PathVariable long id) {
        User user = entityService.findById(User.class, id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/user/" + id));
    }

    //    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/user")
    ResponseEntity<User> createUser(@Valid @RequestBody User model){
        if(model.getId() <= 0) {
            new ResponseEntity<>(entityService.saveOrUpdate(User.class, model), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(model);
    }

    // TODO try to imple,ment adding roles here via raw sql query
    //    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/user")
    ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (entityService.isEntityExist(User.class, user.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(User.class, user), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(user);
    }

    //    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<User> responseEntity = readUsers(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(User.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(User.class, id));
        }
    }

}
