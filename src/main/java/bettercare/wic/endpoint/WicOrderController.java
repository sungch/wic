package bettercare.wic.endpoint;


import bettercare.wic.dal.entity.*;
import bettercare.wic.exceptions.FailedToDeleteException;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Validated
@RequestMapping("/")
@RestController
public class WicOrderController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ResponseService responseService;




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

    @DeleteMapping("/wicOrders/{id}")
    void deleteWicOrder(@PathVariable long id) throws FailedToDeleteException {
        responseService.deleteWicOrder(id);
    }

}
