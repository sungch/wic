package bettercare.wic.endpoint;

import bettercare.wic.dal.entity.*;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.FieldErrorMessage;
import bettercare.wic.service.SaveWicOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

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

  @PostMapping("/wic/customerOrder")
  WicOrderRepresentation createCustomerOrder(@Valid @RequestBody WicOrderRepresentation model) {
    WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
    model.setOrderId(wicOrder.getId());
    return model;
  }

  // WicOrder CRUD

  @PostMapping("wic/wicOrder")
  WicOrder createWicOrder(@Valid @RequestBody WicOrder wicOrder) {
    return entityService.saveOrUpdate(WicOrder.class, wicOrder);
  }

  @GetMapping("/wic/wicOrder/{id}")
  WicOrder readWicOrder(@PathVariable long id) {
    return entityService.findById(WicOrder.class, id);
  }

  @PutMapping("/wic/wicOrder")
  WicOrder updateWicOrder(@RequestBody WicOrder wicOrder) {
    return entityService.saveOrUpdate(WicOrder.class, wicOrder);
  }

  @DeleteMapping("/wic/wicOrder/{id}")
  void deleteWicOrder(@PathVariable long orderId) {
    entityService.deleteById(WicOrder.class, orderId);
  }

  @GetMapping("/wic/wicOrder/pending")
  List readPendingOrders(@RequestParam(value = "status", required = true) String status) {
    return entityService.findOrderByStatus(status);
  }

  @GetMapping("/wic/wicOrders")
  List readWicOrders() {
    return entityService.findAll(WicOrder.class);
  }


  // Category CRUD

  @PostMapping("wic/category")
  Category createCategory(@Valid @RequestBody Category category) {
    return entityService.saveOrUpdate(Category.class, category);
  }

  @GetMapping("/wic/category/{id}")
  Category readCategory(@PathVariable long id) {
    return entityService.findById(Category.class, id);
  }

  @PutMapping("/wic/category")
  Category updateCategory(@RequestBody Category category) {
    return entityService.saveOrUpdate(Category.class, category);
  }

  @DeleteMapping("/wic/category/{id}")
  void deleteCategory(@PathVariable long id) {
    entityService.deleteById(Category.class, id);
  }

  @GetMapping("/wic/categories")
  List readCategories() {
    return entityService.findAll(Category.class);
  }


  // Product CRUD

  @PostMapping("wic/product")
  Product createProduct(@Valid @RequestBody Product product) {
    return entityService.saveOrUpdate(Product.class, product);
  }

  @GetMapping("/wic/product/{id}")
  Product readProduct(@PathVariable long id) {
    return entityService.findById(Product.class, id);
  }

  @PutMapping("/wic/product")
  Product updateProduct(@RequestBody Product product) {
    return entityService.saveOrUpdate(Product.class, product);
  }

  @PutMapping("/wic/product_")
  ResponseEntity<Product> updateProduct_(@Valid @RequestBody Product product) throws ValidationException {
    if (entityService.isProductExist(product.getId())) {
      return new ResponseEntity<>(entityService.saveOrUpdate(Product.class, product), HttpStatus.OK);
    }
    throw new ValidationException("Invalid Payload:" + product.toString(), "400");
  }

  @DeleteMapping("/wic/product/{id}")
  void deleteProduct(@PathVariable long id) {
    entityService.deleteById(Product.class, id);
  }

  @GetMapping("/wic/products")
  List readProducts() {
    return entityService.findAll(Product.class);
  }
  @GetMapping("/wic/product/isHandling")
  List readHandlingProducts(@RequestParam(value = "isHandling", required = true) String isHandling) {
    return entityService.findProductByIsHandling(isHandling);
  }

//  @GetMapping("/wic/product/category/{id}")
//  List<Product> readProductByCategoryId(@PathVariable long categoryId) {
//    return entityService.findProductsByCategoryId(categoryId);
//  }


  // Customer CRUD

  @PostMapping("wic/customer")
  Customer createCustomer(@Valid @RequestBody Customer customer) {
    return entityService.saveOrUpdate(Customer.class, customer);
  }

  @GetMapping("/wic/customer/{id}")
  Customer readCustomer(@PathVariable long id) {
    return entityService.findById(Customer.class, id);
  }

  @PutMapping("/wic/customer")
  Customer updateCustomer(@RequestBody Customer customer) {
    return entityService.saveOrUpdate(Customer.class, customer);
  }

  @DeleteMapping("/wic/customer/{id}")
  void deleteCustomer(@PathVariable long id) {
    entityService.deleteById(Customer.class, id);
  }

  @GetMapping("/wic/customers")
  List readCustomers() {
    return entityService.findAll(Customer.class);
  }


  // Delivery CRUD

  @PostMapping("wic/delivery")
  Delivery createDelivery(@Valid @RequestBody Delivery delivery) {
    return entityService.saveOrUpdate(Delivery.class, delivery);
  }

  @GetMapping("/wic/delivery/{id}")
  Delivery readDelivery(@PathVariable long id) {
    return entityService.findById(Delivery.class, id);
  }

  @PutMapping("/wic/delivery")
  Delivery updateDelivery(@RequestBody Delivery delivery) {
    return entityService.saveOrUpdate(Delivery.class, delivery);
  }

  @DeleteMapping("/wic/delivery/{id}")
  void deleteDelivery(@PathVariable long id) {
    entityService.deleteById(Delivery.class, id);
  }

  @GetMapping("/wic/deliveries")
  List readDeliveries() {
    return entityService.findAll(Delivery.class);
  }

  // MissingProduct CRUD

  @PostMapping("wic/missingProduct")
  MissingProduct createMissingProduct(@Valid @RequestBody MissingProduct missingProduct) {
    return entityService.saveOrUpdate(MissingProduct.class, missingProduct);
  }

  @GetMapping("/wic/missingProduct/{id}")
  MissingProduct readMissingProduct(@PathVariable long id) {
    return entityService.findById(MissingProduct.class, id);
  }

  @PutMapping("/wic/missingProduct")
  MissingProduct updateMissingProduct(@RequestBody MissingProduct missingProduct) {
    return entityService.saveOrUpdate(MissingProduct.class, missingProduct);
  }

  @DeleteMapping("/wic/missingProduct/{id}")
  void deleteMissingProduct(@PathVariable long id) {
    entityService.deleteById(MissingProduct.class, id);
  }

  @GetMapping("/wic/missingProducts")
  List readMissingProducts() {
    return entityService.findAll(MissingProduct.class);
  }


  // Voucher CRUD

  @PostMapping("wic/voucher")
  Voucher createVoucher(@Valid @RequestBody Voucher voucher) {
    return entityService.saveOrUpdate(Voucher.class, voucher);
  }

  @GetMapping("/wic/voucher/{id}")
  Voucher readVoucher(@PathVariable long id) {
    return entityService.findById(Voucher.class, id);
  }

  @PutMapping("/wic/voucher")
  Voucher updateVoucher(@RequestBody Voucher voucher) {
    return entityService.saveOrUpdate(Voucher.class, voucher);
  }

  @DeleteMapping("/wic/voucher/{id}")
  void deleteVoucher(@PathVariable long id) {
    entityService.deleteById(Voucher.class, id);
  }

  @GetMapping("/wic/vouchers")
  List readVouchers() {
    return entityService.findAll(Voucher.class);
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException ex) {
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream()
        .map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
        .collect(Collectors.toList());
    return fieldErrorMessages;
  }

}
