package bettercare.wic.endpoint;

import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.marshaller.WicMediaType;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Validated
public interface OrderEndpoint {

    @GET
    @Path("/list")
    @Produces({WicMediaType.APPLICATION_WIC_ORDER_JSON})
    @Consumes({WicMediaType.APPLICATION_WIC_ORDER_JSON})
    Response list(@Context Request request);

//    @POST
//    @Produces(WicMediaType.APPLICATION_WIC_ORDER_JSON)
//    @Consumes(WicMediaType.APPLICATION_WIC_ORDER_JSON)
//    Response order(@Context Request request, WicOrderRepresentation wicOrderRepresentation);
}
