package bettercare.wic.endpoint;

import bettercare.wic.dal.entity.user.User;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RequestMapping("/admin")
@RestController
public class UserController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ResponseService responseService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users")
    ResponseEntity<List> readUsers() {
        return new ResponseEntity<>(entityService.findAll(User.class), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users/{id}")
    ResponseEntity<User> readUsers(@PathVariable long id) {
        User user = entityService.findById(User.class, id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/user/" + id));
    }

     @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/user")
    ResponseEntity<User> createUser(@Valid @RequestBody User model){
        if(model.getId() <= 0) {
            new ResponseEntity<>(entityService.saveOrUpdate(User.class, model), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(model);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/user")
    ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (entityService.isEntityExist(User.class, user.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(User.class, user), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<User> responseEntity = readUsers(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(User.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(User.class, id));
        }
    }

}
