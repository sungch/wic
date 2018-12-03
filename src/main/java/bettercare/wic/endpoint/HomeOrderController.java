package bettercare.wic.endpoint;


import bettercare.wic.exceptions.InvalidCustomerDataException;
import bettercare.wic.model.PackagingOrderedProductRepresentation;
import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RequestMapping("/")
@RestController
public class HomeOrderController {

    @Autowired private ResponseService responseService;

    /**
     * This is the only input data from Customer client.
     * All the rest of end-points are for admin pupose.
     *
     * @param model input data
     * @return order record
     * @throws InvalidCustomerDataException invalid payload
     */
    @PostMapping("/customerOrder")
    ResponseEntity<PackagingOrderedProductRepresentation> createCustomerOrder(@Valid @RequestBody WicOrderRepresentation model) throws InvalidCustomerDataException {
        return responseService.handleCustomerOrder(model);
    }

}
