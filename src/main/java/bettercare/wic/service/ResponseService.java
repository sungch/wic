package bettercare.wic.service;

import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.exceptions.FailedToDeleteException;
import bettercare.wic.exceptions.InvalidCustomerDataException;
import bettercare.wic.model.PackagingOrderedProductRepresentation;
import bettercare.wic.model.WicOrderRepresentation;
import com.sun.jersey.api.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseService {

    @Autowired
    private SaveWicOrderService saveWicOrderService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private ProductsParser productsParser;
    @Autowired
    private ResponseService responseService;


    public <T> ResponseEntity<T> getBadResponseEntity(T obj) {
        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }

    public <T> String composeDeleteFailureMessage(Class<T> clz, long id) {
        return String.format("Failed to find %s by id:%d for deletion.", clz.getName(), id);

    }

    public <T> boolean isResponseEntityValid(ResponseEntity<T> responseEntity) {
        return responseEntity != null && responseEntity.hasBody();
    }

    public ResponseEntity<PackagingOrderedProductRepresentation> handleCustomerOrder(@Valid @RequestBody WicOrderRepresentation model) throws InvalidCustomerDataException {
        WicOrder wicOrder = saveWicOrderService.saveWicOrder(model);
        if (wicOrder != null) {
            model.setOrderId(wicOrder.getId());
            model.setStatus(wicOrder.getStatus());
            PackagingOrderedProductRepresentation representation;
            try {
                representation = productsParser.parseProducts(model.getProducts());
                representation.setOrderId(wicOrder.getId());
                return new ResponseEntity<>(representation, HttpStatus.CREATED);
            }
            catch (Exception ex) {
                entityService.deleteById(WicOrder.class, wicOrder.getId());
                throw new InvalidCustomerDataException("There is a problem with the customer data:" + model.toString());
            }
        }
        return responseService.getBadResponseEntity(new PackagingOrderedProductRepresentation());
    }

    public ResponseEntity<WicOrder> readWicOrder(@PathVariable long id) {
        WicOrder wicOrder = entityService.findById(WicOrder.class, id);
        if (wicOrder != null) {
            return new ResponseEntity<>(wicOrder, HttpStatus.OK);
        }
        throw new NotFoundException(URI.create("/wicOrders/" + id));
    }


    public ResponseEntity<WicOrder> createWicOrder(WicOrder wicOrder) {
        if (wicOrder.getId() == 0) {
            return new ResponseEntity<>(entityService.saveOrUpdate(WicOrder.class, wicOrder), HttpStatus.CREATED);
        }
        return responseService.getBadResponseEntity(wicOrder);
    }

    public ResponseEntity<WicOrder> updateWicOrder(WicOrder wicOrder) {
        if (entityService.isEntityExist(WicOrder.class, wicOrder.getId())) {
            return new ResponseEntity<>(entityService.saveOrUpdate(WicOrder.class, wicOrder), HttpStatus.OK);
        }
        return responseService.getBadResponseEntity(wicOrder);
    }

    public void deleteWicOrder(long id) throws FailedToDeleteException {
        ResponseEntity<WicOrder> responseEntity = readWicOrder(id);
        if (responseService.isResponseEntityValid(responseEntity)) {
            entityService.deleteById(WicOrder.class, id);
        } else {
            throw new FailedToDeleteException(responseService.composeDeleteFailureMessage(WicOrder.class, id));
        }

    }

    public List<PackagingOrderedProductRepresentation> marshallProducts(List<WicOrder> wicOrders) throws InvalidCustomerDataException {
        List<PackagingOrderedProductRepresentation> products = new ArrayList<>(wicOrders.size());
        if(!wicOrders.isEmpty()) {
            for(WicOrder wicOrder : wicOrders) {
                if(wicOrder != null) {
                    PackagingOrderedProductRepresentation representation;
                    try {
                        representation = productsParser.parseProducts(wicOrder.getProducts());
                        representation.setOrderId(wicOrder.getId());
                        products.add(representation);
                    }
                    catch(Exception ex) {
                        throw new InvalidCustomerDataException("There is a problem with the customer data:" + wicOrder.toString());
                    }
                }
            }
        }
        return products;
    }
}
