package bettercare.wic.controller;

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


@RestController("/wic")
public class MessageController {

    @Autowired private SaveWicOrderService saveWicOrderService;
    @Autowired private EntityService entityService;

    // GET localhost:8080/message

    @GetMapping("/message")
    WicOrderRepresentation send() {
        WicOrderRepresentation model = new WicOrderRepresentation();
        model.setName("SUNGCH");
        return model;
    }


    // POST localhost:8080/message

    @PostMapping("/message")
    WicOrderRepresentation echo(@RequestBody WicOrderRepresentation model) {
        model.setName("SUNGCH-ECHO");
        return model;
    }

    @PostMapping("/order")
    WicOrderRepresentation createOrder(@RequestBody WicOrderRepresentation model) {
        WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
        model.setOrderId(wicOrder.getId());
        return model;
    }

    @GetMapping("/order/{id}")
    WicOrder readOrder(@PathVariable long orderId) {
        return entityService.findById(WicOrder.class, orderId);
    }

    // TODO make pending as a query param
    @GetMapping("/pending-orders")
    List readPendingOrders() {
        return entityService.findAll(WicOrder.class);
    }

    @GetMapping("/orders")
    List readOrders() {
        return entityService.findAll(WicOrder.class);
    }

    // TODO logic is not clear
    @PutMapping("/order")
    WicOrderRepresentation updateOrder(@RequestBody WicOrderRepresentation model) {
        WicOrder wicOrder =  saveWicOrderService.saveWicOrder(model);
        model.setOrderId(wicOrder.getId());
        return model;
    }

    @DeleteMapping("/order/{id}")
    void delete(@PathVariable long orderId) {
        entityService.deleteOrderById(orderId);
    }

}
