package bettercare.wic.endpoint;

//import bettercare.wic.model.WicOrderRepresentation;
//import bettercare.wic.service.marshaller.WicMediaType;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/order")
public class OrderEndpointImpl implements OrderEndpoint {

    @Override
    public Response list(Request request) {
        Response
            .ok("List")
            .type("applicatyion/json")
            .cacheControl(null)
            .header(HttpHeaders.VARY, HttpHeaders.ACCEPT)
            .build();
        return null;
    }

    @Override
    public Response order(Request request) {
//        public Response order(Request request, WicOrderRepresentation wicOrderRepresentation) {
        Response
            .ok("Order")
            .type("applicatyion/json")
            .cacheControl(null)
            .header(HttpHeaders.VARY, HttpHeaders.ACCEPT)
            .build();
        return null;
    }

}
