package bettercare.wic.endpoint;


import bettercare.wic.dal.entity.*;
import bettercare.wic.exceptions.FailedToDeleteException;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.ResponseService;
import com.sun.jersey.api.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/")
@RestController
public class DeliveryController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ResponseService responseService;

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

    @DeleteMapping("/deliveries/{id}")
    void deleteDelivery(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Delivery> responseEntity = readDelivery(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Delivery.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Delivery.class, id));
        }
    }

}
