package bettercare.wic.endpoint;


import bettercare.wic.dal.entity.*;
import bettercare.wic.exceptions.FailedToDeleteException;
import bettercare.wic.service.EntityService;
import bettercare.wic.service.ResponseService;
import com.sun.jersey.api.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RequestMapping("/")
@RestController
public class CustomerController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ResponseService responseService;


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

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Customer> responseEntity = readCustomer(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Customer.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Customer.class, id));
        }
    }

}
