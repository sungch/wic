package bettercare.wic.endpoint;

import bettercare.wic.model.WicOrderRepresentation;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Validated
public interface api {

    @GET
    @Path("/")
    @Produces({APPLICATION_JSON})
    @Consumes({APPLICATION_JSON})
    Response list(@Context Request request);

    @POST
    @Path("/")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    Response placeOrder(@Context Request request, WicOrderRepresentation wicOrderRepresentation);

}
