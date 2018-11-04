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
import javax.xml.bind.ValidationException;
import java.util.List;


@RestController
public class WicController {

  @Autowired
  private SaveWicOrderService saveWicOrderService;
  @Autowired
  private EntityService entityService;


  // Customer Order

  @PostMapping("/customerOrder")
  WicOrderRepresentation createCustomerOrder(@Valid @RequestBody WicOrderRepresentation model) {
    WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
    model.setOrderId(wicOrder.getId());
    return model;
  }

  // WicOrder CRUD

  @GetMapping("/wicOrders")
  List readWicOrders() {
    return entityService.findAll(WicOrder.class);
  }

  @GetMapping("/wicOrders/status")
  List readOrdersByStatus(@RequestParam(value = "status", required = false, defaultValue = "ORDER_RECEIVED") String status) {
    return entityService.findOrderByStatus(status);
  }

  @GetMapping("/wicOrders/pending")
  List readPendingOrders() {
    return entityService.findPendingOrders();
  }

  @GetMapping("/wicOrders/{id}")
  WicOrder readWicOrder(@PathVariable long id) {
    return entityService.findById(WicOrder.class, id);
  }

  @PostMapping("/wicOrder")
  WicOrder createWicOrder(@Valid @RequestBody WicOrder wicOrder) {
    return entityService.saveOrUpdate(WicOrder.class, wicOrder);
  }

  @PutMapping("/wicOrder")
  WicOrder updateWicOrder(@RequestBody WicOrder wicOrder) {
    return entityService.saveOrUpdate(WicOrder.class, wicOrder);
  }

  @DeleteMapping("/wicOrder/{id}")
  void deleteWicOrder(@PathVariable long orderId) {
    entityService.deleteById(WicOrder.class, orderId);
  }


  // Category CRUD

  @GetMapping("/categories")
  List readCategories() {
    return entityService.findAll(Category.class);
  }

  @GetMapping("/categories/{id}")
  Category readCategory(@PathVariable long id) {
    return entityService.findById(Category.class, id);
  }

  @PostMapping("/category")
  Category createCategory(@Valid @RequestBody Category category) {
    return entityService.saveOrUpdate(Category.class, category);
  }

  @PutMapping("/category")
  Category updateCategory(@RequestBody Category category) {
    return entityService.saveOrUpdate(Category.class, category);
  }

  @DeleteMapping("/category/{id}")
  void deleteCategory(@PathVariable long id) {
    entityService.deleteById(Category.class, id);
  }


  // Product CRUD

  @GetMapping("/products")
  List readProducts(@RequestParam(value = "isHandling", required = false, defaultValue = "true") String isHandling) {
    return entityService.findProductByIsHandling(Boolean.valueOf(isHandling) ? "Y" : "N");
  }

  @GetMapping("/products/{id}")
  Product readProduct(@PathVariable long id) {
    return entityService.findById(Product.class, id);
  }

  @GetMapping("/products/categories/{id}")
  List<Product> readProductByCategoryId(@PathVariable long categoryId) {
    Category category = entityService.findCategoryById(categoryId);
    return entityService.findProductsByCategory(category);
  }

  @PostMapping("/product")
  Product createProduct(@Valid @RequestBody Product product) {
    return entityService.saveOrUpdate(Product.class, product);
  }

  @PutMapping("/product")
  ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
    if (entityService.isProductExist(product.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Product.class, product), HttpStatus.OK);
    }
    return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping("/product/{id}")
  void deleteProduct(@PathVariable long id) {
    entityService.deleteById(Product.class, id);
  }


  // Customer CRUD

  @GetMapping("/customers")
  List readCustomers() {
    return entityService.findAll(Customer.class);
  }

  @GetMapping("/customers/{id}")
  Customer readCustomer(@PathVariable long id) {
    return entityService.findById(Customer.class, id);
  }

  @PostMapping("/customer")
  Customer createCustomer(@Valid @RequestBody Customer customer) {
    return entityService.saveOrUpdate(Customer.class, customer);
  }

  @PutMapping("/customer")
  Customer updateCustomer(@RequestBody Customer customer) {
    return entityService.saveOrUpdate(Customer.class, customer);
  }

  @DeleteMapping("/customer/{id}")
  void deleteCustomer(@PathVariable long id) {
    entityService.deleteById(Customer.class, id);
  }


  // Delivery CRUD

  @GetMapping("/deliveries")
  List readDeliveries() {
    return entityService.findAll(Delivery.class);
  }

  @GetMapping("/deliveries/{id}")
  Delivery readDelivery(@PathVariable long id) {
    return entityService.findById(Delivery.class, id);
  }

  @PostMapping("/delivery")
  Delivery createDelivery(@Valid @RequestBody Delivery delivery) {
    return entityService.saveOrUpdate(Delivery.class, delivery);
  }

  @PutMapping("/delivery")
  Delivery updateDelivery(@RequestBody Delivery delivery) {
    return entityService.saveOrUpdate(Delivery.class, delivery);
  }

  @DeleteMapping("/delivery/{id}")
  void deleteDelivery(@PathVariable long id) {
    entityService.deleteById(Delivery.class, id);
  }


  // MissingProduct CRUD

  @GetMapping("/missingProducts")
  List readMissingProducts() {
    return entityService.findAll(MissingProduct.class);
  }

  @GetMapping("/missingProducts/{id}")
  MissingProduct readMissingProduct(@PathVariable long id) {
    return entityService.findById(MissingProduct.class, id);
  }

  @PostMapping("/missingProduct")
  MissingProduct createMissingProduct(@Valid @RequestBody MissingProduct missingProduct) {
    return entityService.saveOrUpdate(MissingProduct.class, missingProduct);
  }

  @PutMapping("/missingProduct")
  MissingProduct updateMissingProduct(@RequestBody MissingProduct missingProduct) {
    return entityService.saveOrUpdate(MissingProduct.class, missingProduct);
  }

  @DeleteMapping("/missingProduct/{id}")
  void deleteMissingProduct(@PathVariable long id) {
    entityService.deleteById(MissingProduct.class, id);
  }


  // Voucher CRUD

  @GetMapping("/vouchers")
  List readVouchers() {
    return entityService.findAll(Voucher.class);
  }

  @GetMapping("/vouchers/{id}")
  Voucher readVoucher(@PathVariable long id) {
    return entityService.findById(Voucher.class, id);
  }

  @PostMapping("/voucher")
  Voucher createVoucher(@Valid @RequestBody Voucher voucher) {
    return entityService.saveOrUpdate(Voucher.class, voucher);
  }

  @PutMapping("/voucher")
  Voucher updateVoucher(@RequestBody Voucher voucher) {
    return entityService.saveOrUpdate(Voucher.class, voucher);
  }

  @DeleteMapping("/voucher/{id}")
  void deleteVoucher(@PathVariable long id) {
    entityService.deleteById(Voucher.class, id);
  }

}
