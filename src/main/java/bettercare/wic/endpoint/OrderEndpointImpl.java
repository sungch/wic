package bettercare.wic.endpoint;

import bettercare.wic.model.WicOrderRepresentation;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;


@Component
@Path("/order")
public class OrderEndpointImpl implements OrderEndpoint {

    @Override
    public Response list(Request request) {
        WicOrderRepresentation model = new WicOrderRepresentation();
        model.setName("SUNGCH");
        return Response
            .ok(model, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.VARY, HttpHeaders.ACCEPT)
            .build();
    }
}
