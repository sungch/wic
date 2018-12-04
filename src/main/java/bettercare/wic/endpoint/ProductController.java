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
public class ProductController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ResponseService responseService;

    @GetMapping("/products")
    ResponseEntity<List> readProducts(@RequestParam(value = "isHandling", required = false, defaultValue = "true") String isHandling) {
        return new ResponseEntity<>(entityService.findProductByIsHandling(Boolean.valueOf(isHandling)), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<Product> readProduct(@PathVariable long id) {
        Product product = entityService.findById(Product.class, id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/products/" + id));
    }

    @GetMapping("/products/category/{id}")
    ResponseEntity<List<Product>> readProductsByCatgoryId(@PathVariable long id) {
        return new ResponseEntity<>(entityService.findProductsByCategoryId(id), HttpStatus.OK);
    }

    @PostMapping("/product")
    ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        if (product.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Product.class, product), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(product);
    }

    @PutMapping("/product")
    ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        if (entityService.isEntityExist(Product.class, product.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Product.class, product), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(product);
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Product> responseEntity = readProduct(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Product.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Product.class, id));
        }
    }
}
