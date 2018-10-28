package bettercare.wic.endpoint;

import bettercare.wic.dal.entity.Product;
import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.SaveWicOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Controller + @ResponseBody (converter of string to json) === @RestController
 *
 * Somehow, client has to assume that POST Content-Type is application/json via @RequestBody
 * GET Accept via @RestController
 *
 * Workflow: Client -> DispatcherServlet -> @RequestBody JSON to Java -> @Controller java format
 * Workflow Controller -> @ResponseBody Java to JSON -> DispatcherServlet -> client
 *
 * mvn spring-boot:run
 */


public class MessageController {

    @Autowired private SaveWicOrderService saveWicOrderService;
    @Autowired private EntityService entityService;

    @PostMapping("/wic/order")
    WicOrderRepresentation createOrder(@RequestBody WicOrderRepresentation model) {
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

    @DeleteMapping("/wic/order/{id}")
    void delete(@PathVariable long orderId) {
        entityService.deleteOrderById(orderId);
    }

}
