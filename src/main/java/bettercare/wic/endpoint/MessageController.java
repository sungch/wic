package bettercare.wic.endpoint;

import bettercare.wic.dal.entity.Product;
import bettercare.wic.dal.entity.WicOrder;
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
public class MessageController {

  @Autowired
  private SaveWicOrderService saveWicOrderService;
  @Autowired
  private EntityService entityService;

  @PostMapping("/wic/order")
  WicOrderRepresentation createOrder(@Valid @RequestBody WicOrderRepresentation model) {
    WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
    model.setOrderId(wicOrder.getId());
    return model;
  }

  @GetMapping("/wic/order/{id}")
  WicOrder readOrder(@PathVariable long orderId) {
    return entityService.findById(WicOrder.class, orderId);
  }

  @GetMapping("/wic/product/isHandling")
  List readHandlingProducts(@RequestParam(value = "isHandling", required = true) String isHandling) {
    return entityService.findProductByIsHandling(isHandling);
  }

  @GetMapping("/wic/order/pending")
  List readPendingOrders(@RequestParam(value = "status", required = true) String status) {
    return entityService.findOrderByStatus(status);
  }

  @GetMapping("/wic/orders")
  List readOrders() {
    return entityService.findAll(WicOrder.class);
  }

  @GetMapping("/wic/product/category/{id}")
  List<Product> readProductByCategoryId(@PathVariable long categoryId) {
    return entityService.findProductsByCategoryId(categoryId);
  }

  @PutMapping("/wic/order")
  ResponseEntity<Product> updateProductInCategory(@Valid @RequestBody Product product) throws ValidationException {
    if (entityService.isProductExist(product.getId())) {
      return new ResponseEntity<Product>(entityService.saveOrUpdate(Product.class, product), HttpStatus.OK);
    }
    throw new ValidationException("Invalid Payload:" + product.toString(), "400");
  }

  @DeleteMapping("/wic/order/{id}")
  void delete(@PathVariable long orderId) {
    entityService.deleteOrderById(orderId);
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
