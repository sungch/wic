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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/")
@RestController
public class VoucherController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ResponseService responseService;


    @GetMapping("/vouchers")
    ResponseEntity<List> readVouchers() {
        return new ResponseEntity<>(entityService.findAll(Voucher.class), HttpStatus.OK);
    }

    @GetMapping("/vouchers/{id}")
    ResponseEntity<Voucher> readVoucher(@PathVariable long id) {
        Voucher voucher = entityService.findById(Voucher.class, id);
        if (voucher != null) {
            return new ResponseEntity<>(voucher, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/vouchers/" + id));
    }

    @PostMapping("/voucher")
    ResponseEntity<Voucher> createVoucher(@Valid @RequestBody Voucher voucher) {
        if (voucher.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Voucher.class, voucher), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(voucher);
    }

    @PutMapping("/voucher")
    ResponseEntity<Voucher> updateVoucher(@Valid @RequestBody Voucher voucher) {
        if (entityService.isEntityExist(Voucher.class, voucher.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(Voucher.class, voucher), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(voucher);
    }

    @DeleteMapping("/vouchers/{id}")
    void deleteVoucher(@PathVariable long id) throws FailedToDeleteException {
        ResponseEntity<Voucher> responseEntity = readVoucher(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(Voucher.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(Voucher.class, id));
        }
    }

}
