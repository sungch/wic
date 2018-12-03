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

import java.net.URI;
import java.util.List;

@Validated
@RequestMapping("/")
@RestController
public class MissingProductController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ResponseService responseService;


    @GetMapping("/missingProducts")
    ResponseEntity<List> readMissingProducts() {
        return new ResponseEntity<>(entityService.findAll(MissingProduct.class), HttpStatus.OK);
    }

    @GetMapping("/missingProducts/{id}")
    ResponseEntity<MissingProduct> readMissingProduct(@PathVariable long id) {
        MissingProduct missingProduct = entityService.findById(MissingProduct.class, id);
        if (missingProduct != null) {
            return new ResponseEntity<>(missingProduct, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/missingProducts/" + id));
    }

    @PostMapping("/missingProduct")
    ResponseEntity<MissingProduct> createMissingProduct(@RequestBody MissingProduct missingProduct) {
        if (missingProduct.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(MissingProduct.class, missingProduct), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(missingProduct);
    }

    @PutMapping("/missingProduct")
    ResponseEntity<MissingProduct> updateMissingProduct(@RequestBody MissingProduct missingProduct) {
        if (entityService.isEntityExist(MissingProduct.class, missingProduct.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(MissingProduct.class, missingProduct), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(missingProduct);
    }

    @DeleteMapping("/missingProducts/{id}")
    void deleteMissingProduct(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<MissingProduct> responseEntity = readMissingProduct(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(MissingProduct.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(MissingProduct.class, id));
        }
    }

}
