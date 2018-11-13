package bettercare.wic.endpoint;

import bettercare.wic.dal.entity.*;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.SaveWicOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class WicController {

  @Autowired
  private SaveWicOrderService saveWicOrderService;
  @Autowired
  private EntityService entityService;


  // Customer Order

  @PostMapping("/customerOrder")
  ResponseEntity<WicOrderRepresentation> createCustomerOrder(@Valid @RequestBody WicOrderRepresentation model) {
    WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
    model.setOrderId(wicOrder.getId());
    model.setOrderedTime(wicOrder.getOrderedTime());
    model.setStatus(wicOrder.getStatus());
    return new ResponseEntity<>(model, HttpStatus.CREATED);
  }

  // WicOrder CRUD

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
    return new ResponseEntity<>(entityService.findById(WicOrder.class, id), HttpStatus.OK);
  }

  @PostMapping("/wicOrder")
  ResponseEntity<WicOrder> createWicOrder(@Valid @RequestBody WicOrder wicOrder) {
    if(wicOrder.getId() == 0) {
      return new ResponseEntity<>(entityService.saveOrUpdate(WicOrder.class, wicOrder), HttpStatus.CREATED);
    }
    return getBadResponseEntity(wicOrder);
  }

  @PutMapping("/wicOrder")
  ResponseEntity<WicOrder> updateWicOrder(@Valid @RequestBody WicOrder wicOrder) {
    if(entityService.isEntityExist(WicOrder.class, wicOrder.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(WicOrder.class, wicOrder), HttpStatus.OK);
    }
    return getBadResponseEntity(wicOrder);
  }

  @DeleteMapping("/wicOrder/{id}")
  void deleteWicOrder(@PathVariable long orderId) {
    entityService.deleteById(WicOrder.class, orderId);
  }


  // Category CRUD

  @GetMapping("/categories")
  ResponseEntity<List> readCategories() {
    return new ResponseEntity<>(entityService.findAll(Category.class), HttpStatus.OK);
  }

  @GetMapping("/categories/{id}")
  ResponseEntity<Category> readCategory(@PathVariable long id) {
    return new ResponseEntity<>(entityService.findById(Category.class, id), HttpStatus.OK);
  }

  @PostMapping("/category")
  ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
    if(category.getId() == 0) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Category.class, category), HttpStatus.CREATED);
    }
    return getBadResponseEntity(category);
  }

  @PutMapping("/category")
  ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
    if(entityService.isEntityExist(Category.class, category.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Category.class, category), HttpStatus.OK);
    }
    return getBadResponseEntity(category);
  }

  @DeleteMapping("/category/{id}")
  void deleteCategory(@PathVariable long id) {
    entityService.deleteById(Category.class, id);
  }


  // Product CRUD

  @GetMapping("/products")
  ResponseEntity<List> readProducts(@RequestParam(value = "isHandling", required = false, defaultValue = "true") String isHandling) {
    return new ResponseEntity<>(entityService.findProductByIsHandling(Boolean.valueOf(isHandling) ? "Y" : "N"), HttpStatus.OK);
  }

  @GetMapping("/products/{id}")
  ResponseEntity<Product> readProduct(@PathVariable long id) {
    return new ResponseEntity<>(entityService.findById(Product.class, id), HttpStatus.OK);
  }

  @GetMapping("/products/categories/{id}")
  ResponseEntity<List<Product>> readProductByCategoryId(@PathVariable long categoryId) {
    Category category = entityService.findCategoryById(categoryId);
    return new ResponseEntity<>(entityService.findProductsByCategory(category), HttpStatus.OK);
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

  @DeleteMapping("/product/{id}")
  void deleteProduct(@PathVariable long id) {
    entityService.deleteById(Product.class, id);
  }


  // Customer CRUD

  @GetMapping("/customers")
  ResponseEntity<List> readCustomers() {
    return new ResponseEntity<>(entityService.findAll(Customer.class), HttpStatus.OK);
  }

  @GetMapping("/customers/{id}")
  ResponseEntity<Customer> readCustomer(@PathVariable long id) {
    return new ResponseEntity<>(entityService.findById(Customer.class, id), HttpStatus.OK);
  }

  @PostMapping("/customer")
  ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
    if(customer.getId() == 0) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Customer.class, customer), HttpStatus.CREATED);
    }
    return getBadResponseEntity(customer);
  }

  @PutMapping("/customer")
  ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
    if(entityService.isEntityExist(Customer.class, customer.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Customer.class, customer), HttpStatus.OK);
    }
    return getBadResponseEntity(customer);
  }

  @DeleteMapping("/customer/{id}")
  void deleteCustomer(@PathVariable long id) {
    entityService.deleteById(Customer.class, id);
  }


  // Delivery CRUD

  @GetMapping("/deliveries")
  ResponseEntity<List> readDeliveries() {
    return new ResponseEntity<>(entityService.findAll(Delivery.class), HttpStatus.OK);
  }

  @GetMapping("/deliveries/{id}")
  ResponseEntity<Delivery> readDelivery(@PathVariable long id) {
    return new ResponseEntity<>(entityService.findById(Delivery.class, id), HttpStatus.OK);
  }

  @PostMapping("/delivery")
  ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
    if(delivery.getId() == 0) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Delivery.class, delivery), HttpStatus.CREATED);
    }
    return getBadResponseEntity(delivery);
  }

  @PutMapping("/delivery")
  ResponseEntity<Delivery> updateDelivery(@RequestBody Delivery delivery) {
    if(entityService.isEntityExist(Delivery.class, delivery.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Delivery.class, delivery), HttpStatus.OK);
    }
    return getBadResponseEntity(delivery);
  }

  @DeleteMapping("/delivery/{id}")
  void deleteDelivery(@PathVariable long id) {
    entityService.deleteById(Delivery.class, id);
  }


  // MissingProduct CRUD

  @GetMapping("/missingProducts")
  ResponseEntity<List> readMissingProducts() {
    return new ResponseEntity<>(entityService.findAll(MissingProduct.class), HttpStatus.OK);
  }

  @GetMapping("/missingProducts/{id}")
  ResponseEntity<MissingProduct> readMissingProduct(@PathVariable long id) {
    return new ResponseEntity<>(entityService.findById(MissingProduct.class, id), HttpStatus.OK);
  }

  @PostMapping("/missingProduct")
  ResponseEntity<MissingProduct> createMissingProduct(@RequestBody MissingProduct missingProduct) {
    if(missingProduct.getId() == 0) {
      return new ResponseEntity<>(entityService.saveOrUpdate(MissingProduct.class, missingProduct), HttpStatus.CREATED);
    }
    return getBadResponseEntity(missingProduct);
  }

  @PutMapping("/missingProduct")
  ResponseEntity<MissingProduct> updateMissingProduct(@RequestBody MissingProduct missingProduct) {
    if(entityService.isEntityExist(MissingProduct.class, missingProduct.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(MissingProduct.class, missingProduct), HttpStatus.OK);
    }
    return getBadResponseEntity(missingProduct);
  }

  @DeleteMapping("/missingProduct/{id}")
  void deleteMissingProduct(@PathVariable long id) {
    entityService.deleteById(MissingProduct.class, id);
  }


  // Voucher CRUD

  @GetMapping("/vouchers")
  ResponseEntity<List> readVouchers() {
    return new ResponseEntity<>(entityService.findAll(Voucher.class), HttpStatus.OK);
  }

  @GetMapping("/vouchers/{id}")
  ResponseEntity<Voucher> readVoucher(@PathVariable long id) {
    return new ResponseEntity<>(entityService.findById(Voucher.class, id), HttpStatus.OK);
  }

  @PostMapping("/voucher")
  ResponseEntity<Voucher> createVoucher(@Valid @RequestBody Voucher voucher) {
    if(voucher.getId() == 0) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Voucher.class, voucher), HttpStatus.CREATED);
    }
    return getBadResponseEntity(voucher);
  }

  @PutMapping("/voucher")
  ResponseEntity<Voucher> updateVoucher(@Valid @RequestBody Voucher voucher) {
    if(entityService.isEntityExist(Voucher.class, voucher.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Voucher.class, voucher), HttpStatus.OK);
    }
    return getBadResponseEntity(voucher);
  }

  @DeleteMapping("/voucher/{id}")
  void deleteVoucher(@PathVariable long id) {
    entityService.deleteById(Voucher.class, id);
  }

  private <T> ResponseEntity<T> getBadResponseEntity(T obj) {
    return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
  }

}
