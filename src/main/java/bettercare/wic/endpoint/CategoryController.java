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
public class CategoryController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ResponseService responseService;


    @GetMapping("/categories")
    ResponseEntity<List> readCategories() {
        return new ResponseEntity<>(entityService.findAll(Category.class), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    ResponseEntity<Category> readCategory(@PathVariable long id) {
        Category category = entityService.findById(Category.class, id);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/categories/" + id));
    }

    @PostMapping("/category")
    ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        if (category.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Category.class, category), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(category);
    }

    @PutMapping("/category")
    ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
        if (entityService.isEntityExist(Category.class, category.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Category.class, category), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(category);
    }

    @DeleteMapping("/categories/{id}")
    void deleteCategory(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Category> responseEntity = readCategory(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Category.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Category.class, id));
        }
    }

}
