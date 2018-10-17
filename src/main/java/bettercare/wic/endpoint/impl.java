package bettercare.wic.endpoint;

import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.SaveWicOrderService;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

// TODO JSONMarshallingSupportClasses

@Component
@Path("/")
public class impl implements api {

    @Override
    public Response list(Request request) {
        return null;
    }

    @Override
    public Response placeOrder(Request request, WicOrderRepresentation wicOrderRepresentation) {
        SaveWicOrderService service = new SaveWicOrderService();
        service.saveWicOrder(wicOrderRepresentation);
        return null;
    }
}
