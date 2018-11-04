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

/**
 * @Controller + @ResponseBody (converter of string to json) === @RestController
 * <p>
 * Somehow, client has to assume that POST Content-Type is application/json via @RequestBody
 * GET Accept via @RestController
 * <p>
 * Workflow: Client -> DispatcherServlet -> @RequestBody JSON to Java -> @Controller java format
 * Workflow Controller -> @ResponseBody Java to JSON -> DispatcherServlet -> client
 * <p>
 * mvn spring-boot:run
 * @RequestParameter -- method parameter
 * @RequestBody -- payload
 * @PathParameter -- path
 */

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

  @PostMapping("/wicOrder")
  WicOrder createWicOrder(@Valid @RequestBody WicOrder wicOrder) {
    return entityService.saveOrUpdate(WicOrder.class, wicOrder);
  }

  @GetMapping("/wicOrders/{id}")
  WicOrder readWicOrder(@PathVariable long id) {
    return entityService.findById(WicOrder.class, id);
  }

  @PutMapping("/wicOrder")
  WicOrder updateWicOrder(@RequestBody WicOrder wicOrder) {
    return entityService.saveOrUpdate(WicOrder.class, wicOrder);
  }

  @DeleteMapping("/wicOrder/{id}")
  void deleteWicOrder(@PathVariable long orderId) {
    entityService.deleteById(WicOrder.class, orderId);
  }

  @GetMapping("/wicOrders/status")
  List readOrdersByStatus(@RequestParam(value = "status", required = false, defaultValue = "ORDER_RECEIVED") String status) {
    return entityService.findOrderByStatus(status);
  }

  @GetMapping("/wicOrders/pending")
  List readPendingOrders() {
    return entityService.findPendingOrders();
  }

  @GetMapping("/wicOrders")
  List readWicOrders() {
    return entityService.findAll(WicOrder.class);
  }


  // Category CRUD

  @PostMapping("/category")
  Category createCategory(@Valid @RequestBody Category category) {
    return entityService.saveOrUpdate(Category.class, category);
  }

  @GetMapping("/category/{id}")
  Category readCategory(@PathVariable long id) {
    return entityService.findById(Category.class, id);
  }

  @PutMapping("/category")
  Category updateCategory(@RequestBody Category category) {
    return entityService.saveOrUpdate(Category.class, category);
  }

  @DeleteMapping("/category/{id}")
  void deleteCategory(@PathVariable long id) {
    entityService.deleteById(Category.class, id);
  }

  @GetMapping("/categories")
  List readCategories() {
    return entityService.findAll(Category.class);
  }


  // Product CRUD

  @PostMapping("/product")
  Product createProduct(@Valid @RequestBody Product product) {
    return entityService.saveOrUpdate(Product.class, product);
  }

  @GetMapping("/product/{id}")
  Product readProduct(@PathVariable long id) {
    return entityService.findById(Product.class, id);
  }

  @PutMapping("/product")
  Product updateProduct(@RequestBody Product product) {
    return entityService.saveOrUpdate(Product.class, product);
  }

  @PutMapping("/product_")
  ResponseEntity<Product> updateProduct_(@Valid @RequestBody Product product) throws ValidationException {
    if (entityService.isProductExist(product.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Product.class, product), HttpStatus.OK);
    }
    throw new ValidationException("Invalid Payload:" + product.toString(), "400");
  }

  @DeleteMapping("/product/{id}")
  void deleteProduct(@PathVariable long id) {
    entityService.deleteById(Product.class, id);
  }

  @GetMapping("/products")
  List readProducts(@RequestParam(value = "isHandling", required = false, defaultValue = "true") String isHandling) {
    String isHandling_ = Boolean.valueOf(isHandling) ? "Y" : "N";
    return entityService.findProductByIsHandling(isHandling_);
  }

  @GetMapping("/product/category/{id}")
  List<Product> readProductByCategoryId(@PathVariable long categoryId) {
    Category category = entityService.findCategoryById(categoryId);
    return entityService.findProductsByCategory(category);
  }


  // Customer CRUD

  @PostMapping("/customer")
  Customer createCustomer(@Valid @RequestBody Customer customer) {
    return entityService.saveOrUpdate(Customer.class, customer);
  }

  @GetMapping("/customer/{id}")
  Customer readCustomer(@PathVariable long id) {
    return entityService.findById(Customer.class, id);
  }

  @PutMapping("/customer")
  Customer updateCustomer(@RequestBody Customer customer) {
    return entityService.saveOrUpdate(Customer.class, customer);
  }

  @DeleteMapping("/customer/{id}")
  void deleteCustomer(@PathVariable long id) {
    entityService.deleteById(Customer.class, id);
  }

  @GetMapping("/customers")
  List readCustomers() {
    return entityService.findAll(Customer.class);
  }


  // Delivery CRUD

  @PostMapping("/delivery")
  Delivery createDelivery(@Valid @RequestBody Delivery delivery) {
    return entityService.saveOrUpdate(Delivery.class, delivery);
  }

  @GetMapping("/delivery/{id}")
  Delivery readDelivery(@PathVariable long id) {
    return entityService.findById(Delivery.class, id);
  }

  @PutMapping("/delivery")
  Delivery updateDelivery(@RequestBody Delivery delivery) {
    return entityService.saveOrUpdate(Delivery.class, delivery);
  }

  @DeleteMapping("/delivery/{id}")
  void deleteDelivery(@PathVariable long id) {
    entityService.deleteById(Delivery.class, id);
  }

  @GetMapping("/deliveries")
  List readDeliveries() {
    return entityService.findAll(Delivery.class);
  }

  // MissingProduct CRUD

  @PostMapping("/missingProduct")
  MissingProduct createMissingProduct(@Valid @RequestBody MissingProduct missingProduct) {
    return entityService.saveOrUpdate(MissingProduct.class, missingProduct);
  }

  @GetMapping("/missingProduct/{id}")
  MissingProduct readMissingProduct(@PathVariable long id) {
    return entityService.findById(MissingProduct.class, id);
  }

  @PutMapping("/missingProduct")
  MissingProduct updateMissingProduct(@RequestBody MissingProduct missingProduct) {
    return entityService.saveOrUpdate(MissingProduct.class, missingProduct);
  }

  @DeleteMapping("/missingProduct/{id}")
  void deleteMissingProduct(@PathVariable long id) {
    entityService.deleteById(MissingProduct.class, id);
  }

  @GetMapping("/missingProducts")
  List readMissingProducts() {
    return entityService.findAll(MissingProduct.class);
  }


  // Voucher CRUD

  @PostMapping("/voucher")
  Voucher createVoucher(@Valid @RequestBody Voucher voucher) {
    return entityService.saveOrUpdate(Voucher.class, voucher);
  }

  @GetMapping("/voucher/{id}")
  Voucher readVoucher(@PathVariable long id) {
    return entityService.findById(Voucher.class, id);
  }

  @PutMapping("/voucher")
  Voucher updateVoucher(@RequestBody Voucher voucher) {
    return entityService.saveOrUpdate(Voucher.class, voucher);
  }

  @DeleteMapping("/voucher/{id}")
  void deleteVoucher(@PathVariable long id) {
    entityService.deleteById(Voucher.class, id);
  }

  @GetMapping("/vouchers")
  List readVouchers() {
    return entityService.findAll(Voucher.class);
  }

}
